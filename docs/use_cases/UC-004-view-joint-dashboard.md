# Use Case: View Joint Dashboard

## Overview

**Use Case ID:** UC-004   
**Use Case Name:** View Joint Dashboard   
**Primary Actor:** Jens & Annika   
**Goal:** Display aggregated financial data, current net worth, wealth development timeline, and category asset distribution for the latest snapshot date.   
**Status:** Updated

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Dashboard tab.
2. System retrieves all historical transaction entries.
3. System calculates the total combined net worth for each recorded date and determines the breakdown of assets by category for the latest snapshot date.
4. System displays:
   - A summary metric card showing current total combined wealth (Gesamtvermögen).
   - A wealth development chart over time with:
     - Time / Dates on the X-axis (`dd.MM.yy`).
     - Total Net Worth on the Y-axis scaled in clear 100.000 € step increments (e.g. 0 €, 100.000 €, 200.000 €, ... 700.000 €).
     - Sufficient top margin/padding to ensure top Y-axis labels and grid lines are completely visible without clipping.
     - Data points representing the sum of entries for each snapshot date.
   - An interactive category asset distribution pie chart positioned below the timeline chart, featuring:
     - A title indicating the date of the latest snapshot (e.g. `Kategorien-Verteilung (Stand: dd.MM.yyyy)`).
     - Color-coded pie slices for each configured financial category using exact category names as defined in application settings and transactions.
     - Interactive hover interaction: hovering over any pie slice dynamically displays the hovered category's exact name, Euro amount, and percentage share in the center of the chart (and via tooltip), reverting back to the total wealth display when the mouse leaves.
     - A legend displaying the exact configured category names, absolute amounts in Euros, and percentage shares.
5. User views the current net worth, the timeline chart of wealth development over time, and interacts with the category distribution pie chart.

## Alternative Flows

### A1: No Data Found

**Trigger:** No transaction entries exist in the system (step 2)
**Flow:**

1. System displays a message suggesting the user to enter their first transaction.
2. Use case ends.

## Postconditions

### Success Postconditions

- User views an accurate summary card of current joint net worth, a timeline chart with 100.000 € Y-axis increments, and an interactive category pie chart with mouse-hover category details for the latest snapshot date using exact defined category names.

### Failure Postconditions

- An error message is displayed if transaction data cannot be retrieved.

## Business Rules

### BR-004: Dashboard Wealth Aggregation
Current total wealth is the sum of all recorded transaction balances for the most recent entry date across all institutes and categories.

### BR-005: Timeline Wealth Plotting & Y-Axis Scaling
The timeline chart plots the total net worth for each historical entry date chronologically on the X-axis. The Y-axis is scaled in uniform 100.000 € step increments with proper top padding so all labels remain fully visible.

### BR-006: Category Distribution Breakdown & Interactive Hover
The category pie chart displays asset sums grouped by the exact category names defined in the application (matching the configured master categories in settings and transaction entries). The date of the snapshot is shown in the header, and exact percentage shares per category are calculated. Hovering over a slice dynamically updates the center display to show the exact category name, amount, and percentage share.

