# Design Principles (all screens)

These rules come from what is consistent across every wireframe, and match
the principles already established in the Clock redesign (Clock repo,
`docs/elderly-spec/design-principles.md`). They override current app styling
wherever the two conflict.

## Typography

- Header label ("Photos") and the month label are large: ~24–28sp.
- All other text ≥ 18sp — button labels, dialog body text, hint text.
  Nothing smaller than 18sp anywhere.
- Dialog body text ("Are you sure you want to delete…") ~22sp.
- Respect the Android system font-size setting; layouts must not clip at the
  largest accessibility font sizes (test at 200%).

## Touch targets

- Minimum touch target 56dp; action buttons are pill buttons ~64dp tall —
  full-width when alone ("Select Photos", "✗ Cancel"), half-width when
  paired ("Share"/"Delete", "No"/"Yes").
- Paired buttons sit side by side with a clear gap; the safe/leaving action
  on the **left**, the confirming/destructive-confirm action on the
  **right** — matching every wireframe ("No | Yes", "Share | Delete").
- Grid thumbnails are large: **2 columns**, as drawn (decision #14).
- Contact rows in the share picker are full-width, ≥ 64dp tall.

## Buttons say what they do

- Every button has an icon **and** a text label: "↗ Share", "🗑 Delete",
  "✗ Cancel", "☝ Select Photos". No icon-only actions anywhere.
- Dialog answers are plain words ("No" / "Yes"), not "OK".

## Navigation & gestures

- **No back arrow in any header, anywhere** — every screen's app bar is a
  plain static label reading "Photos" (the grid adds a month label at the
  right end — see [photos-grid.md](photos-grid.md)). The system back
  gesture/button leaves the viewer or cancels a dialog. Same rule as Clock
  decision #16.
- **No overflow menu, no toolbar icons** — Settings/Sort/Search/About are
  unreachable from the UI (decision #4, #13).
- The **only** gestures are: tap, vertical scroll in the grid, horizontal
  swipe between photos in the viewer (labeled on screen: "Swipe to view
  more"), and pinch/double-tap zoom in the viewer. **No long-press, no
  drag-select, no swipe-to-dismiss, no pull-to-refresh** (decision #6).
- Selection is entered only through the labeled "Select Photos" button —
  never by long-pressing a thumbnail.

## Dialogs

Unlike Clock (whose editors became full screens), this app keeps two
overlays, because that's exactly what the wireframes draw — a dialog is
acceptable when it holds a single question or one short list:

- **Delete confirmation** — one question, two big answer buttons.
- **Share with** — a short list of contacts plus Cancel.

Both dim the screen behind them; tapping outside does nothing (explicit
Cancel / No only, plus system back). No other dialogs exist.

## Color & contrast

- High contrast text (≥ 7:1 for body text, WCAG AAA where feasible).
- The selection checkmark on a thumbnail is a filled circle with a
  check — large (~32dp), high contrast against any photo (white glyph on
  solid primary-color disc with a thin white rim).
- Keep Fossify's light/dark theming; layouts must meet the contrast bar in
  both themes.
- **Stay on Fossify Commons** (decision #2): reuse Commons theming, colors,
  and components wherever possible — scaled up, not replaced. The app must
  still read as a Fossify app.

## Language

- Plain words, no jargon, sentence case: "Share", "Delete", "Cancel",
  "Select Photos", "Share with", "Are you sure you want to delete this
  photo?"
- Counts are spelled into the delete dialog when more than one item is
  selected ("…delete these 3 photos?") rather than the wireframe's
  "photo(s)" shorthand — parenthetical plurals are jargon.
