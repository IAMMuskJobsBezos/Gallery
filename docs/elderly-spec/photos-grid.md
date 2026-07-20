# Photos Grid (main screen)

Wireframes: [`grid-main`](wireframes/grid-main.png),
[`grid-select-mode`](wireframes/grid-select-mode.png).

The app opens straight into this screen. There is no folder/album screen
anywhere (decision #4) — this grid is every photo and video on the device in
one flat list.

## Layout (top to bottom)

1. **Header** — static label "Photos" at the left (never changes, no back
   arrow, no menu — see [design-principles.md](design-principles.md)).
   At the right end, a **month label** ("May") naming the month of the
   topmost visible row. It updates as the user scrolls; when the row's month
   is not in the current year it includes the year ("May 2024") —
   decision #10.
2. **Grid** — 2 columns (decision #14), square thumbnails, small gap.
   - Sorted newest first by date taken (decision #12).
   - No section headers, no folder tiles, no fastscroller.
   - **Video tiles** show a centered play badge over the thumbnail
     (decision #5); otherwise identical to photo tiles.
   - Tapping a tile opens it in the [viewer](photo-viewer.md).
3. **"☝ Select Photos" button** — full-width pill, fixed at the bottom.
   Tapping it enters select mode.

Empty state (no photos on device): the grid area shows "No photos yet" in
large text; the Select Photos button is hidden.

## Select mode

Entered **only** via the Select Photos button — long-press and drag-select
are disabled (decision #6).

- Every tile gains an empty **selection circle**; tapping a tile toggles it
  to a filled circle with a checkmark (and back). Tapping a tile in select
  mode never opens the viewer.
- The bottom of the screen shows, in place of Select Photos:
  - **"↗ Share" | "🗑 Delete"** — half-width pills side by side. Both act on
    the checked items; both do nothing (stay disabled/dimmed) while nothing
    is checked.
  - **"✗ Cancel"** — full-width pill below the pair. Clears the selection
    and leaves select mode. System back does the same.
- Share opens the [Share with picker](share-delete.md#share-with-picker);
  Delete opens the
  [delete confirmation](share-delete.md#delete-confirmation). After either
  completes, select mode ends and the plain grid returns.

### Wireframe note

[`grid-main`](wireframes/grid-main.png) sketches small circles on tiles even
though its bottom button is still "Select Photos". Resolved: circles appear
**only in select mode** (decision #6) — outside it the grid is plain
thumbnails, so normal browsing has no tappable-looking chrome.
