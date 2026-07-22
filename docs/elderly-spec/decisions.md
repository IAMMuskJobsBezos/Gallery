# Decisions

#1–#4 and #6, #10–#15 follow from the wireframes and the conventions already
settled in the Clock redesign (Clock repo, `docs/elderly-spec/decisions.md`).
#5, #7, #8, #9 were resolved 2026-07-19 in interactive Q&A while drafting
this spec.

| # | Question | Decision |
| --- | --- | --- |
| 1 | Distribution | **Replace the UI in this fork.** No mode toggle, no separate flavor. Divergence from upstream Fossify is accepted. |
| 2 | Design system | Stay within the **Fossify Commons / Fossify Gallery look** — Commons theming and components, scaled up per the spec. Not a new visual language. |
| 3 | Wireframe images | Kept **untracked** (`.gitignore`); spec text is what's committed. Only the nine `Screenshot 2026-07-19 …` files from `~/Downloads/photos-wireframes` are source material — the `.jpeg` files there are not. |
| 4 | Folders/albums | **None.** One flat grid of everything on the device (existing `SHOW_ALL` pipeline). Folder browsing, albums, search, favorites, and the directories screen are unreachable. |
| 5 | Videos | **Included.** Video tiles get a play badge in the grid; the viewer plays them with one big centered play/pause and no other chrome. (Wireframes only draw photos; excluding videos would make them invisible on the device.) |
| 6 | Selection | **Only via the "Select Photos" button.** Long-press, drag-select, and every other hidden gesture are disabled. Tap toggles a visible circle/check on each tile. |
| 7 | Share mechanism | **Superseded 2026-07-20.** "Share with" lists the person's **existing Messages conversations** (read straight from the system SMS/MMS provider, same source Messages itself reads — not device contacts, decision #8 below is superseded too). Tapping one hands the media off explicitly to the Messages fork (`ACTION_SEND`/`ACTION_SEND_MULTIPLE`, pre-addressed via extras so Messages skips its own recipient picker). Adds `READ_SMS` (`READ_CONTACTS` is now only used to resolve a number to a display name, best-effort). |
| 8 | Picker contents | **Superseded — see #7.** Every existing conversation (including group threads), newest first, not contacts. |
| 9 | Delete behavior | **Revised 2026-07-20: permanent, no recycle bin.** `useRecycleBin` now defaults to `false` and `setupElderlyMode()` forces it off, so Yes deletes the file immediately and irreversibly — the caregiver-safety-net recycle bin from the original decision is removed. The confirmation dialog picks up Commons' standard `deletion_confirmation` string automatically (the "move to recycle bin" wording only shows when `useRecycleBin` is on), so the wireframes' "This action cannot be undone." sentence is now true and should be reinstated if/when the dialog copy is revisited. |
| 10 | Month label | Right end of the grid header shows the **month of the topmost visible row**, updating on scroll — "May", with the year added when not the current year ("May 2024"). Grid only; the viewer header is just "Photos". |
| 11 | Viewer chrome | **Always visible** — no immersive mode, no tap-to-hide. Pinch and double-tap zoom kept. No wrap-around at either end of the list. **Revised 2026-07-20:** the "Swipe to view more" hint is removed (redundant once you've swiped once); the header and the Share/Delete bar are solid opaque bars (not a gradient scrim over the photo), so the photo reads as cut off between them — for both photos and videos. |
| 12 | Grid order | **Newest first by date taken.** No grouping headers, no fastscroller. |
| 13 | V1 scope | **Everything else unreachable**: editing, settings, search, slideshow, wallpaper, hidden/included/excluded folders, favorites, widgets, backup/export. Code stays; nothing links to it. |
| 14 | Grid columns | **2 columns**, fixed, as drawn. |
| 15 | Build process | Iterative: build → emulator screenshot → compare to wireframes → adjust, per screen. See [implementation-map.md](implementation-map.md). |
| 16 | Share/Delete order & color | **Revised 2026-07-20.** Delete is now on the **left** (secondary/outlined); Share is on the **right** (primary/filled), with the share icon changed to a box-with-up-arrow ("ios_share" style) matching the wireframe more literally. Applies to both the grid's selection bar and the viewer's bottom bar. The No/Yes delete-confirm dialog is unchanged (No left/secondary, Yes right/primary). |
| 17 | Bottom bar sizing | The grid's bottom bar padding was tightened (from `activity_margin` to `normal_margin`) to show more of the grid; the system nav bar's own inset padding (edge-to-edge) is unaffected and still reserved on top of it. |

## Resolved wireframe ambiguities

- [`grid-main`](wireframes/grid-main.png) draws circles on tiles outside
  select mode → circles appear **only in select mode** (#6).
- "Share with" vs "Select to share with:" titles → unified as **"Share
  with"** in both entry points.
- Hint text appears in only one of three viewer sketches → resolved, then
  **removed entirely** on 2026-07-20 per decision #11's revision.

## Still open (later)

- ~~Caregiver recovery path for the hidden recycle bin (#9)~~ — moot as of
  the 2026-07-20 revision to #9; delete is now permanent and there is no bin
  to recover from.
- Dark theme and 200% system font size must be verified per screen during
  the build loop (#15) — the Clock effort showed these slip when deferred.
- Settings & widgets redesign (post-v1, #13).
- The Messages fork's applicationId gets a `.debug` suffix on debug builds;
  `shareWithMessagesConversation` resolves whichever variant is installed at
  runtime rather than assuming the release package name.
