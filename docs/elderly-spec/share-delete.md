# Share & Delete

The two actions in the app, reachable from both the
[grid's select mode](photos-grid.md#select-mode) (acting on the checked
items) and the [viewer](photo-viewer.md) (acting on the item on screen).
Both are overlay dialogs per the
[dialog rules](design-principles.md#dialogs): dimmed background, explicit
buttons only, tap-outside does nothing, system back cancels.

## Share with picker

Wireframes: [`grid-share-with`](wireframes/grid-share-with.png),
[`viewer-share-with`](wireframes/viewer-share-with.png).

Not the Android share sheet, and not a contact picker either (superseded
2026-07-20, decision #7) — it lists the person's **existing Messages
conversations**, read straight from the system SMS/MMS provider (the same
source Messages itself reads). The two wireframes title it differently
("Share with" / "Select to share with:"); unified as **"Share with"** in
both places.

1. **Title** — "Share with".
2. **Conversation list** — every conversation with at least one message,
   newest first, scrolling when long. Group threads show all recipient
   names joined ("Harvard, Stanford"). One row per conversation: round
   avatar (Commons generic-person icon) + name, ≥64dp tall, thin
   low-contrast divider between rows. Tapping a row is the send action —
   there is no separate confirm step. Empty state: "No conversations found."
3. **"✗ Cancel"** — full-width pill at the bottom. Closes the picker,
   changing nothing.

On tap, the selected photo(s)/video(s) are handed to the **Messages fork**
addressed to that conversation (MMS) — decision #7. The picker closes, a
toast confirms ("Sent to Harvard"), select mode ends (grid) or the viewer
stays on the same item.

Requires the `READ_SMS` permission (`READ_CONTACTS` is used only to resolve
a number to a display name, best-effort) — decision #7. If `READ_SMS` is
denied, the picker closes.

## Delete confirmation

Wireframes: [`grid-delete-confirm`](wireframes/grid-delete-confirm.png),
[`viewer-delete-confirm`](wireframes/viewer-delete-confirm.png).

1. **Question** — count- and type-aware, ~22sp:
   - "Are you sure you want to delete this photo?" / "…this video?"
   - "Are you sure you want to delete these 3 photos?" (mixed types:
     "…these 3 items?")
   - The wireframes' second sentence "This action cannot be undone." is
     **dropped** (decision #9): deleted files actually go to the hidden
     recycle bin, so the sentence would be false — and "photo(s)" shorthand
     violates the [language rules](design-principles.md#language).
2. **"No" | "Yes"** — half-width pills, No on the left. No is the default
   focus; system back and tap-outside also mean No.

On Yes: the items move to Fossify's **recycle bin** (auto-purged after the
existing 30-day window) — decision #9. There is **no bin UI anywhere** in
the app; to the user the photo is simply gone, but a caregiver can recover
it within 30 days (via the file system or a stock Fossify build — see
"Still open" in [decisions.md](decisions.md)). Afterwards: select mode ends
and the grid refreshes (grid path), or the viewer advances to the next item
— previous if it was the last, closing to the grid when nothing remains
(viewer path).
