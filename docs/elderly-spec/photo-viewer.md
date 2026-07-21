# Photo Viewer

Wireframes: [`viewer-main`](wireframes/viewer-main.png),
[`viewer-main-b`](wireframes/viewer-main-b.png),
[`viewer-swipe-hint`](wireframes/viewer-swipe-hint.png).

Opened by tapping a tile in the [grid](photos-grid.md). Shows one item at a
time from the same flat newest-first list as the grid.

## Layout (top to bottom)

1. **Header** — solid, opaque bar with the static label "Photos", same as
   the grid (decision #11 revision) — not a gradient scrim over the photo.
   No back arrow (system back returns to the grid), no month label, no menu.
2. **The photo** — fills the space between header and buttons; reads as cut
   off by the solid bars above and below it, for both photos and videos. For
   **videos** (decision #5): the frame shows the video with one big centered
   ▶ play button; tapping it plays, tapping the video again pauses.
3. **"🗑 Delete" | "↗ Share"** — half-width pills side by side in a solid
   opaque bottom bar (decision #16), Delete on the left (secondary/outlined),
   Share on the right (primary/filled, box-with-up-arrow icon). Delete opens
   the [delete confirmation](share-delete.md#delete-confirmation); Share
   opens the [Share with picker](share-delete.md#share-with-picker) for this
   one item.

## Behavior

- **Horizontal swipe** moves to the next/previous item. Swiping past either
  end does nothing (no wrap-around).
- **Pinch zoom and double-tap zoom** are kept (decision #11) — natural,
  already-known gestures. While zoomed in, panning moves around the photo;
  swiping to the next photo requires zooming back out (double-tap again).
- Header and buttons are **always visible** — no immersive mode, no
  tap-to-hide chrome, no fullscreen toggle (decision #11).
- After a delete is confirmed, the viewer advances to the next item (or the
  previous one if the last item was deleted; closes to the grid if nothing
  is left) — see [share-delete.md](share-delete.md).
- Removed entirely from the viewer: edit, favorite, rotate, set-as-wallpaper,
  slideshow, properties/details, hide, copy/move, print, resize — every
  current menu and bottom action other than Share and Delete (decision #13).
