package org.fossify.gallery.models

/**
 * One row in the elderly-friendly "Share with" picker (docs/elderly-spec/share-delete.md,
 * decision #7 revision) - a conversation that already exists in the Messages fork, read straight
 * from the system SMS/MMS provider (the same source Messages itself reads), not device contacts.
 */
data class ElderlyConversation(
    val threadId: Long,
    val displayName: String,
    // semicolon-joined for group threads, matching NewConversationActivity's expected format
    val phoneNumbers: String,
    // the first recipient's contact photo, if any - SimpleContactsHelper.loadContactImage() falls
    // back to a colored letter avatar (the same one the contact already has elsewhere) when empty
    val photoUri: String
)
