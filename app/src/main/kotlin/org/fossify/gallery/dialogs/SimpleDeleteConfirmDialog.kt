package org.fossify.gallery.dialogs

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import org.fossify.commons.extensions.getAlertDialogBuilder
import org.fossify.commons.extensions.setupDialogStuff
import org.fossify.gallery.R
import org.fossify.gallery.databinding.DialogSimpleDeleteConfirmBinding

/**
 * Plain No/Yes delete confirmation (docs/elderly-spec/share-delete.md). Deliberately does not
 * mention "cannot be undone" - deleted items go to the hidden recycle bin (decision #9 in
 * docs/elderly-spec/decisions.md), so that line would be false.
 */
class SimpleDeleteConfirmDialog(
    activity: Activity,
    photoCount: Int,
    videoCount: Int,
    private val callback: () -> Unit
) {
    private var dialog: AlertDialog? = null

    init {
        val binding = DialogSimpleDeleteConfirmBinding.inflate(activity.layoutInflater)
        binding.simpleDeleteConfirmMessage.text = buildMessage(activity, photoCount, videoCount)
        binding.simpleDeleteConfirmNoButton.setOnClickListener { dialog?.dismiss() }
        binding.simpleDeleteConfirmYesButton.setOnClickListener {
            dialog?.dismiss()
            callback()
        }

        activity.getAlertDialogBuilder().apply {
            activity.setupDialogStuff(binding.root, this) { alertDialog ->
                dialog = alertDialog
            }
        }
    }

    private fun buildMessage(activity: Activity, photoCount: Int, videoCount: Int): String {
        val resources = activity.resources
        return when {
            videoCount == 0 -> resources.getQuantityString(R.plurals.elderly_delete_confirm_photos, photoCount, photoCount)
            photoCount == 0 -> resources.getQuantityString(R.plurals.elderly_delete_confirm_videos, videoCount, videoCount)
            else -> {
                val total = photoCount + videoCount
                resources.getQuantityString(R.plurals.elderly_delete_confirm_items, total, total)
            }
        }
    }
}
