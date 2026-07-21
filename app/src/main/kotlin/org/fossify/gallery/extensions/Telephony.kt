package org.fossify.gallery.extensions

import android.content.Context
import android.net.Uri
import android.provider.Telephony.Mms
import android.provider.Telephony.MmsSms
import android.provider.Telephony.Threads
import androidx.core.net.toUri
import org.fossify.commons.extensions.getLongValue
import org.fossify.commons.extensions.getStringValue
import org.fossify.commons.helpers.SimpleContactsHelper
import org.fossify.gallery.models.ElderlyConversation

/**
 * Elderly-friendly "Share with" picker (docs/elderly-spec/share-delete.md, decision #7 revision):
 * lists the conversations that already exist in the Messages fork by reading the same system
 * SMS/MMS provider Messages itself reads (`Telephony.Threads`), not device contacts. Requires
 * `READ_SMS`.
 */
fun Context.getElderlyConversations(): ArrayList<ElderlyConversation> {
    val conversations = ArrayList<ElderlyConversation>()
    val uri = "${Threads.CONTENT_URI}?simple=true".toUri()
    val projection = arrayOf(Threads._ID, Threads.RECIPIENT_IDS)
    val selection = "${Threads.MESSAGE_COUNT} > 0"
    val sortOrder = "${Threads.DATE} DESC"

    try {
        contentResolver.query(uri, projection, selection, null, sortOrder)?.use { cursor ->
            while (cursor.moveToNext()) {
                val threadId = cursor.getLongValue(Threads._ID)
                val rawIds = cursor.getStringValue(Threads.RECIPIENT_IDS) ?: continue
                val recipientIds = rawIds.split(" ").mapNotNull { it.toIntOrNull() }
                if (recipientIds.isEmpty()) {
                    continue
                }

                val phoneNumbers = recipientIds.mapNotNull { getPhoneNumberFromCanonicalAddress(it) }
                if (phoneNumbers.isEmpty()) {
                    continue
                }

                val contactsHelper = SimpleContactsHelper(this)
                val names = phoneNumbers.map { number ->
                    try {
                        contactsHelper.getNameFromPhoneNumber(number)
                    } catch (e: Exception) {
                        number
                    }
                }
                val photoUri = try {
                    contactsHelper.getPhotoUriFromPhoneNumber(phoneNumbers.first())
                } catch (e: Exception) {
                    ""
                }

                conversations.add(
                    ElderlyConversation(
                        threadId = threadId,
                        displayName = names.joinToString(", "),
                        phoneNumbers = phoneNumbers.joinToString(";"),
                        photoUri = photoUri
                    )
                )
            }
        }
    } catch (e: Exception) {
    }

    return conversations
}

private fun Context.getPhoneNumberFromCanonicalAddress(canonicalAddressId: Int): String? {
    val uri: Uri = Uri.withAppendedPath(MmsSms.CONTENT_URI, "canonical-addresses")
    val projection = arrayOf(Mms.Addr.ADDRESS)
    val selection = "${Mms._ID} = ?"
    val selectionArgs = arrayOf(canonicalAddressId.toString())

    return try {
        contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getStringValue(Mms.Addr.ADDRESS)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        null
    }
}
