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
| 7 | Share mechanism | **Simplified contact picker → Messages fork.** No Android share sheet. Tapping a contact sends the media as MMS via the Messages app (`ACTION_SEND`/`ACTION_SEND_MULTIPLE` targeted at its package). Adds `READ_CONTACTS` — the redesign's one new permission. |
| 8 | Picker contents | **All device contacts with a phone number**, sorted by name, scrollable — not just starred/favorites. |
| 9 | Delete behavior | **Recycle bin, hidden.** Yes moves files to Fossify's recycle bin (30-day auto-purge) as a caregiver safety net; no bin UI exists anywhere. The wireframes' "This action cannot be undone." sentence is dropped from the dialog — it would be false, and the "photo(s)" shorthand violates the language rules. |
| 10 | Month label | Right end of the grid header shows the **month of the topmost visible row**, updating on scroll — "May", with the year added when not the current year ("May 2024"). Grid only; the viewer header is just "Photos". |
| 11 | Viewer chrome | **Always visible** — no immersive mode, no tap-to-hide. Pinch and double-tap zoom kept; "Swipe to view more" hint is permanent (a hint that disappears is a hidden state). No wrap-around at either end of the list. |
| 12 | Grid order | **Newest first by date taken.** No grouping headers, no fastscroller. |
| 13 | V1 scope | **Everything else unreachable**: editing, settings, search, slideshow, wallpaper, hidden/included/excluded folders, favorites, widgets, backup/export. Code stays; nothing links to it. |
| 14 | Grid columns | **2 columns**, fixed, as drawn. |
| 15 | Build process | Iterative: build → emulator screenshot → compare to wireframes → adjust, per screen. See [implementation-map.md](implementation-map.md). |

## Resolved wireframe ambiguities

- [`grid-main`](wireframes/grid-main.png) draws circles on tiles outside
  select mode → circles appear **only in select mode** (#6).
- "Share with" vs "Select to share with:" titles → unified as **"Share
  with"** in both entry points.
- Hint text appears in only one of three viewer sketches → **always shown**
  (#11).

## Still open (later)

- **Messages-fork intent contract** — its manifest accepts
  `SEND`/`SEND_MULTIPLE` (`*/*`) and `SENDTO`; the exact extras for
  pre-addressing the MMS to the picked contact need confirming against its
  compose activity when the share flow is built (#7).
- **Caregiver recovery path** for the hidden recycle bin (#9) — document how
  a helper actually restores a deleted photo (stock Fossify build? file
  manager?) once the delete flow lands.
- **Share feedback wording** — "Sent to X" vs "Sending…" depends on what the
  hand-off can actually know; settle when building.
- Dark theme and 200% system font size must be verified per screen during
  the build loop (#15) — the Clock effort showed these slip when deferred.
- Settings & widgets redesign (post-v1, #13).
