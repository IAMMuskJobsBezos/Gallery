package org.fossify.gallery.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fossify.commons.activities.BaseSimpleActivity
import org.fossify.commons.extensions.beVisibleIf
import org.fossify.commons.extensions.getAlertDialogBuilder
import org.fossify.commons.extensions.getProperTextColor
import org.fossify.commons.extensions.setupDialogStuff
import org.fossify.commons.helpers.PERMISSION_READ_SMS
import org.fossify.commons.helpers.SimpleContactsHelper
import org.fossify.commons.helpers.ensureBackgroundThread
import org.fossify.gallery.databinding.DialogShareWithBinding
import org.fossify.gallery.databinding.ItemShareContactBinding
import org.fossify.gallery.extensions.getElderlyConversations
import org.fossify.gallery.models.ElderlyConversation

/**
 * "Share with" picker (docs/elderly-spec/share-delete.md, decision #7 revision). Lists the
 * conversations that already exist in the Messages fork - not device contacts - so tapping one
 * sends straight to a conversation the person already has. Reads the system SMS/MMS provider
 * directly (same source Messages itself reads); the actual send is built by the caller via
 * [org.fossify.gallery.extensions.shareWithMessagesConversation].
 */
class ShareWithDialog(
    private val activity: BaseSimpleActivity,
    private val onConversationPicked: (ElderlyConversation) -> Unit
) {
    private var dialog: AlertDialog? = null
    private val binding = DialogShareWithBinding.inflate(activity.layoutInflater)
    private val textColor = activity.getProperTextColor()

    init {
        binding.shareWithTitle.setTextColor(textColor)
        binding.shareWithEmptyPlaceholder.setTextColor(textColor)
        binding.shareWithContactsList.layoutManager = LinearLayoutManager(activity)
        binding.shareWithCancelButton.setOnClickListener { dialog?.dismiss() }

        activity.getAlertDialogBuilder().apply {
            activity.setupDialogStuff(binding.root, this) { alertDialog ->
                dialog = alertDialog
            }
        }

        loadConversations()
    }

    private fun loadConversations() {
        activity.handlePermission(PERMISSION_READ_SMS) { granted ->
            if (!granted) {
                dialog?.dismiss()
                return@handlePermission
            }

            ensureBackgroundThread {
                val conversations = activity.getElderlyConversations()
                activity.runOnUiThread {
                    if (dialog == null) {
                        return@runOnUiThread
                    }

                    binding.shareWithEmptyPlaceholder.beVisibleIf(conversations.isEmpty())
                    binding.shareWithContactsList.beVisibleIf(conversations.isNotEmpty())
                    binding.shareWithContactsList.adapter = ConversationsAdapter(activity, conversations, textColor) { conversation ->
                        dialog?.dismiss()
                        onConversationPicked(conversation)
                    }
                }
            }
        }
    }

    private class ConversationsAdapter(
        private val activity: BaseSimpleActivity,
        private val conversations: ArrayList<ElderlyConversation>,
        private val textColor: Int,
        private val onClick: (ElderlyConversation) -> Unit
    ) : RecyclerView.Adapter<ConversationsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemShareContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val conversation = conversations[position]
            holder.binding.shareContactName.text = conversation.displayName
            holder.binding.shareContactName.setTextColor(textColor)
            SimpleContactsHelper(activity).loadContactImage(
                conversation.photoUri, holder.binding.shareContactAvatar, conversation.displayName
            )
            holder.binding.root.setOnClickListener { onClick(conversation) }
        }

        override fun getItemCount() = conversations.size

        class ViewHolder(val binding: ItemShareContactBinding) : RecyclerView.ViewHolder(binding.root)
    }
}
