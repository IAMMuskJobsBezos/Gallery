# Elderly-Friendly Photos — Spec Overview

Redesign of Fossify Gallery into a minimal "Photos" app for elderly users,
based on hand-drawn wireframes (local copies in `wireframes/`, untracked;
originals in `~/Downloads/photos-wireframes` — only the nine
`Screenshot 2026-07-19 …` files are source material; the `.jpeg` files there
are not).

Sibling spec of the same style and rules: Clock's
`docs/elderly-spec/` in the Clock repo.

## Goal

Strip the gallery down to the two things the wireframes show — **look at
photos** and **share or delete them** — usable by someone with reduced
vision, reduced fine-motor control, and low tolerance for hidden or ambiguous
UI. No folders, no editing, no menus, no gestures that aren't swiping through
photos. Big targets, every button labeled in words — while staying on the
Fossify Commons design system so it still looks like a Fossify app.

**This fork's UI is replaced outright** (no "simple mode" toggle, no separate
flavor) — decision #1 in [decisions.md](decisions.md).

## The whole app

Two screens and two overlay dialogs:

1. **[Photos grid](photos-grid.md)** — flat grid of every photo on the
   device, newest first, with a month label in the header and one
   "Select Photos" button.
2. **[Photo viewer](photo-viewer.md)** — one photo full-screen, swipe to move
   between photos, Share and Delete buttons.
3. **[Share & Delete](share-delete.md)** — the "Share with" contact picker
   and the delete confirmation dialog, reachable from both screens.

## Spec files

| File | Covers |
| --- | --- |
| [design-principles.md](design-principles.md) | Type scale, touch targets, dialogs, navigation, language rules for every screen |
| [photos-grid.md](photos-grid.md) | Main grid, month header label, select mode |
| [photo-viewer.md](photo-viewer.md) | Full-screen viewer, swiping, zoom |
| [share-delete.md](share-delete.md) | "Share with" contact picker, delete confirmation, both entry points |
| [implementation-map.md](implementation-map.md) | How each spec maps onto the existing Fossify Gallery code, plus the build/verify loop |
| [decisions.md](decisions.md) | All resolved product decisions and the few items deferred to build time |

## Scope

**V1:** the grid, the viewer, select mode, the share-with picker, the delete
confirmation. Everything else in today's Gallery — folder browsing, search,
favorites, hidden/included/excluded folders, editing, slideshow, wallpaper,
recycle-bin UI, settings, widgets — becomes unreachable (decision #4, #13).

All headline product questions are resolved (videos in, share via the
Messages fork, delete to a hidden recycle bin — decisions #5, #7, #9). What
remains open are build-time details listed in
[decisions.md](decisions.md#still-open-later), chiefly the exact intent
extras for pre-addressing the Messages hand-off.

## Status

**Spec drafted 2026-07-19 from the wireframes, decisions resolved the same
day. Implementation not started.**
