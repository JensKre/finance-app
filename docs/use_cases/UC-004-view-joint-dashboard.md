# Use Case: View Joint Dashboard

## Overview

**Use Case ID:** UC-004   
**Use Case Name:** View Joint Dashboard   
**Primary Actor:** Jens & Annika   
**Goal:** Display aggregated financial data, current net worth, wealth development timeline, category asset distribution for the latest snapshot date, and historical percentage asset distribution timeline.   
**Status:** Updated

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Dashboard tab.
2. System retrieves all historical transaction entries.
3. System calculates the total combined net worth for each recorded date, the category breakdown for the latest snapshot date, and the historical percentage share of each category relative to total wealth for each snapshot date.
4. System displays:
   - A summary metric card showing current total combined wealth (Gesamtvermögen).
   - A wealth development chart over time with:
     - Time / Dates on the X-axis (`dd.MM.yy`) scaled proportionally according to actual calendar time elapsed between dates (proportional date spacing).
     - Total Net Worth on the Y-axis scaled in clear 100.000 € step increments (e.g. 0 €, 100.000 €, 200.000 €, ... 700.000 €).
     - Sufficient top margin/padding to ensure top Y-axis labels and grid lines are completely visible without clipping.
     - Data points representing the sum of entries for each snapshot date with interactive mouse-hover displaying the exact Euro wealth value and date.
   - An interactive category asset distribution pie chart positioned below the timeline chart, featuring:
     - A title indicating the date of the latest snapshot (e.g. `Kategorien-Verteilung (Stand: dd.MM.yyyy)`).
     - Color-coded pie slices for each configured financial category using exact category names as defined in application settings and transactions.
     - Interactive hover interaction: hovering over any pie slice dynamically displays the hovered category's exact name, Euro amount, and percentage share in the center of the chart (and via tooltip), reverting back to the total wealth display when the mouse leaves.
     - A legend displaying the exact configured category names, absolute amounts in Euros, and percentage shares.
   - A percentage distribution timeline chart positioned below the category distribution pie chart, displaying:
     - A title indicating the chart purpose (`Prozentualer Vermögensverlauf nach Kategorien über die Zeit`).
     - Time / Dates on the X-axis (`dd.MM.yy`) scaled proportionally according to actual calendar time elapsed between dates (proportional date spacing).
     - Percentage share (0% to 100%) on the Y-axis representing each category's proportion of total combined wealth for every snapshot date.
     - Visual series (such as stacked area or multi-line visualization) showing the historical trend of how category asset shares evolved over time.
     - Interactive hover interaction: hovering over any category line or data point highlights the line, increases its stroke width, and immediately displays a popover/tooltip or title header showing the exact category name, date, Euro amount, and percentage share.
   - A wealth growth breakdown timeline chart ("Vermögenszuwachs-Aufschlüsselung: Einnahmen/Ersparnisse vs. Wertsteigerung/Investitionen") located below all previous charts. For each interval between consecutive wealth snapshot dates, the chart analyzes and breaks down total net worth change into:
     1) **Income / Savings Contribution (Einnahmen/Ersparnisse)**: Net saved budget income (total income minus total expenses) imported from budget CSV data during that exact date interval.
     2) **Investment & Valuation Appreciation (Wertsteigerung & Investitionen)**: Remaining wealth difference (Total Wealth Delta minus Net Saved Budget Income) attributable to market gains, portfolio growth, or capital additions.
     - Interactive click interaction: clicking on any bar in the growth breakdown chart opens a detail dialog/modal displaying all underlying transaction entries (budget transactions for savings or asset account entries for wealth changes) for that date interval without text truncation (`...`).
5. User inspects current total wealth, pie chart breakdown, percentage category timeline, and clicks chart elements or growth bars to inspect exact values and underlying constituent transactions.

## Alternative Flows

### A1: No Data Recorded

**Trigger:** No transaction entries exist in the database (step 2)
**Flow:**

1. System displays 0,00 € for current net worth.
2. System displays empty state placeholders for all dashboard charts.
3. Use case ends.

## Postconditions

### Success Postconditions

- User views an accurate summary card of current joint net worth, a timeline chart with 100.000 € Y-axis increments, proportional calendar time X-axis scaling, and exact value hover tooltips, an interactive category pie chart with mouse-hover category details for the latest snapshot date, an interactive historical percentage asset distribution timeline chart, and a wealth growth attribution chart decomposing snapshot deltas into budget savings vs. investment/valuation appreciation with click drill-down to full un-truncated transaction listings.

### Failure Postconditions

- An error message is displayed if transaction data cannot be retrieved.

## Business Rules

### BR-004: Dashboard Wealth Aggregation
Current total wealth is the sum of all recorded transaction balances for the most recent entry date across all institutes and categories.

### BR-005: Timeline Wealth Plotting & Y-Axis Scaling & Hover Details
The timeline chart plots total net worth for each historical entry date chronologically on the X-axis. The X-position of each date is calculated proportionally based on actual elapsed calendar days between the earliest and latest recorded snapshot dates. The Y-axis is scaled in uniform 100.000 € step increments with proper top padding so all labels remain fully visible. Hovering over any data point on the timeline displays a popover/tooltip or header text showing the exact date and exact total net worth in Euros (e.g. `23.07.2026: 452.890,50 €`). The subtitle text container must maintain a fixed height, line height, and uniform font size during hover state changes so that the chart layout does not shift or jump vertically.

### BR-006: Category Distribution Breakdown & Interactive Hover
The category pie chart displays asset sums grouped by the exact category names defined in the application (matching the configured master categories in settings and transaction entries). The date of the snapshot is shown in the header, and exact percentage shares per category are calculated. Hovering over a slice dynamically updates the center display to show the exact category name, amount, and percentage share.

### BR-013: Percentage Wealth Distribution Timeline
Below the category pie chart, a historical percentage distribution timeline chart visualizes the proportion (0% to 100%) of total combined wealth held in each asset category for every recorded snapshot date, with X-axis positions spaced proportionally to actual calendar days. Hovering over any line highlights the selected category line (and dims other lines) while displaying the category name, date, amount, and percentage share, allowing users to track structural asset allocation shifts over time.

### BR-018: Wealth Growth Decomposition & Click Drill-Down Inspection
Located below all other dashboard charts, a dedicated timeline chart decomposes the net change in total wealth between each consecutive snapshot date ($T_{i}$ to $T_{i+1}$) into two distinct components:
1. **Net Savings / Budget Income (Einnahmen/Ersparnisse):** Sum of net income minus expenses imported from budget CSV records with transaction dates falling strictly within the date interval $(T_{i}, T_{i+1}]$.
2. **Investment Appreciation & Valuation Gain (Wertsteigerung & Investitionen):** Calculated as $\text{Total Delta} - \text{Net Savings}$. This represents portfolio gains, market value growth, interest, or unbudgeted capital increases during that period.
The chart plots these components chronologically on a calendar-proportional X-axis with interactive hover details showing the exact Euro breakdown. Clicking on any bar opens a popup dialog or detail view displaying all underlying constituent transaction records for that period with full un-truncated text visibility.
