# Photo Viewer

Wireframes: [`viewer-main`](wireframes/viewer-main.png),
[`viewer-main-b`](wireframes/viewer-main-b.png),
[`viewer-swipe-hint`](wireframes/viewer-swipe-hint.png).

Opened by tapping a tile in the [grid](photos-grid.md). Shows one item at a
time from the same flat newest-first list as the grid.

## Layout (top to bottom)

1. **Header** — static label "Photos", same as the grid. No back arrow
   (system back returns to the grid), no month label, no menu.
2. **The photo** — fills the space between header and buttons, letterboxed
   to fit. For **videos** (decision #5): the frame shows the video with one
   big centered ▶ play button; tapping it plays, tapping the video again
   pauses. No scrub bar, no other playback chrome.
3. **"↗ Share" | "🗑 Delete"** — half-width pills side by side, fixed at the
   bottom. Share opens the
   [Share with picker](share-delete.md#share-with-picker) for this one item;
   Delete opens the
   [delete confirmation](share-delete.md#delete-confirmation).
4. **Hint line** — "Swipe to view more" in ≥18sp text under the buttons,
   always visible (decision #11 — two of the three viewer wireframes omit
   it, but a hint that disappears is a hidden state; it stays).

## Behavior

- **Horizontal swipe** moves to the next/previous item — the one gesture
  the hint teaches. Swiping past either end does nothing (no wrap-around).
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
