# Use Case: View Joint Dashboard

## Overview

**Use Case ID:** UC-004   
**Use Case Name:** View Joint Dashboard   
**Primary Actor:** Jens & Annika   
**Goal:** Display aggregated financial data, current net worth, and wealth development over time.   
**Status:** Updated

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Dashboard tab.
2. System retrieves all historical transaction entries.
3. System calculates the total combined net worth for each recorded date and the overall current net worth.
4. System displays:
   - A summary metric card showing current total combined wealth (Gesamtvermögen).
   - A wealth development chart over time with:
     - Time / Dates on the X-axis (`dd.MM.yy`).
     - Total Net Worth on the Y-axis scaled in clear 100.000 € step increments (e.g. 0 €, 100.000 €, 200.000 €, ... 700.000 €).
     - Sufficient top margin/padding to ensure top Y-axis labels and grid lines are completely visible without clipping.
     - Data points representing the sum of entries for each snapshot date.
5. User views the current net worth and the timeline chart of wealth development over time.

## Alternative Flows

### A1: No Data Found

**Trigger:** No transaction entries exist in the system (step 2)
**Flow:**

1. System displays a message suggesting the user to enter their first transaction.
2. Use case ends.

## Postconditions

### Success Postconditions

- User views an accurate summary card of current joint net worth and a timeline chart with 100.000 € Y-axis increments showing wealth development over time.

### Failure Postconditions

- An error message is displayed if transaction data cannot be retrieved.

## Business Rules

### BR-004: Dashboard Wealth Aggregation
Current total wealth is the sum of all recorded transaction balances for the most recent entry date across all institutes and categories.

### BR-005: Timeline Wealth Plotting & Y-Axis Scaling
The timeline chart plots the total net worth for each historical entry date chronologically on the X-axis. The Y-axis is scaled in uniform 100.000 € step increments with proper top padding so all labels remain fully visible.

