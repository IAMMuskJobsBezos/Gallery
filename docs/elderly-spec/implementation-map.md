# Implementation Map

How the spec lands in the existing Fossify Gallery codebase
(`app/src/main/kotlin/org/fossify/gallery/`). This is a pointer map, not a
task plan. All product decisions are in [decisions.md](decisions.md).

## Build & verify loop (decision #15)

Same as the Clock redesign: every screen is verified visually, not just by
compiling — build → install on emulator → screenshot each specced state →
compare side-by-side with the matching image in `wireframes/` → adjust →
repeat. Re-screenshot at 200% system font size and in dark theme before
calling a screen done. Progress screenshots go in `_progress/` (untracked).

## Shell / launch

| Spec | Existing code | Change |
| --- | --- | --- |
| App opens straight into the flat grid | `SplashActivity.kt`, `MainActivity.kt` (directories screen), `MediaActivity.kt` (`mShowAll`, `helpers/Config.kt: showAll`) | Route launch to `MediaActivity` in its existing `SHOW_ALL` mode (`config.showAll = true`); `MainActivity`/directory browsing becomes unreachable |
| Global type/target scale | theme + `res/values/dimens` | New dimension set (header, month label, pill buttons, grid gap) used by all redesigned layouts |

## Grid

| Spec | Existing code | Change |
| --- | --- | --- |
| 2-column flat grid, newest first | `MediaActivity.kt`, `activity_media.xml`, `MediaAdapter.kt`, `photo_item_grid.xml`, `video_item_grid.xml` | Fix columns at 2; force sort to date-taken descending, grouping off (`GROUP_BY_NONE`) so `thumbnail_section.xml` headers never appear; remove fastscroller, pull-to-refresh, toolbar menu |
| Month label in header | `activity_media.xml` toolbar | New right-aligned label driven by a grid scroll listener reading the first visible `Medium`'s date-taken |
| Select mode via button, no gestures | `MediaAdapter.kt` (`setupDragListener(true)`, Commons long-press ActionMode) | Disable drag-select and long-press; add circle/check overlay bound to a plain selection set; replace the ActionMode CAB with the fixed bottom pills (Select Photos ↔ Share/Delete/Cancel) in `activity_media.xml` |

## Viewer

| Spec | Existing code | Change |
| --- | --- | --- |
| Full-screen pager, Share/Delete pills, hint | `ViewPagerActivity.kt`, `activity_medium.xml`, `bottom_actions.xml`, `MyPagerAdapter.kt` | Replace `bottom_actions.xml` icon row with two labeled pills + "Swipe to view more" hint; delete the menu and all other actions; disable immersive/tap-to-hide chrome so header and buttons stay visible |
| Photo zoom, video playback | `fragments/PhotoFragment.kt`, `fragments/VideoFragment.kt` | Keep pinch/double-tap zoom as-is; strip video chrome down to one big centered play/pause |

## Share

| Spec | Existing code | Change |
| --- | --- | --- |
| "Share with" conversation picker → Messages | `extensions/Activity.kt: sharePath/sharePaths` (system share sheet), `AndroidManifest.xml` | `dialogs/ShareWithDialog.kt` + `extensions/Telephony.kt: getElderlyConversations()` read `Telephony.Threads`/canonical-addresses directly (needs `READ_SMS`; `READ_CONTACTS` best-effort for display names) instead of device contacts; on tap, `extensions/Activity.kt: shareWithMessagesConversation()` fires `ACTION_SEND`/`ACTION_SEND_MULTIPLE` targeted explicitly at the Messages fork's package (resolved at runtime — debug builds carry a `.debug` applicationId suffix) with the media URIs and the conversation's number(s); `NewConversationActivity.kt` in the Messages repo reads those extras and skips its own recipient picker |

## Delete

| Spec | Existing code | Change |
| --- | --- | --- |
| No/Yes confirm → hidden recycle bin | `DeleteWithRememberDialog.kt`, delete paths in `ViewPagerActivity.kt` / `MediaActivity.kt`, `config.useRecycleBin` | Replace the dialog with the plain count-aware No/Yes confirm; force `useRecycleBin = true` (not user-visible); remove the Recycle Bin entry from all navigation so the bin has no UI |

## Untouched / unreachable in v1 (decision #13)

`EditActivity`, `SettingsActivity`, `SearchActivity`,
`Hidden/Included/ExcludedFoldersActivity`, `SetWallpaperActivity`,
`VideoPlayerActivity` (standalone), `WidgetConfigureActivity`, slideshow,
favorites, filtering dialogs, backup/export. They stay in the codebase but
nothing links to them; each gets its own pass later if ever.

## Android permissions

Existing storage/media permission flow stays as-is
(`AllFilesPermissionDialog`, `GrantAllFilesDialog`, …). New additions for the
share picker (decision #7): `READ_SMS` (gates opening the picker) and
`READ_CONTACTS` (best-effort display-name resolution only).
