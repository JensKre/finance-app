# Use Case: View Budget Analysis

## Overview

**Use Case ID:** UC-008
**Use Case Name:** View Budget Analysis
**Primary Actor:** Jens & Annika
**Goal:** Visualize monthly sum totals for each budget category from imported CSV data in individual category charts.
**Status:** Proposed

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Budget Auswertung (Budget Analysis) section.
2. System retrieves all imported budget transactions from the database.
3. System groups transactions by budget category and calculates the total monthly sum for each recorded month (formatted as `MM.yyyy` / `MMM yyyy`).
4. System displays a vertical card layout on a full-page scrollable view featuring:
   - An interactive category selector / overview header allowing users to filter or scroll through category charts.
   - For each distinct budget category (e.g. `Lebensmittel`, `Kleidung`, `Transport`):
     - A dedicated chart card with the category name as title.
     - A monthly bar or line chart showing the monthly sum totals on the Y-axis (in Euros) and chronological months on the X-axis (`MM.yy`).
     - Interactive hover details on bars/data points showing month name, year, and exact total amount spent/transacted in Euros.
     - Interactive click interaction: clicking on any monthly bar opens a detail dialog/modal listing all individual underlying transactions (date, person, type, amount, description) that contribute to that specific month's category total. The dialog must provide sufficient width and full text visibility so that descriptions, dates, and names are fully legible without truncation or ellipsis (`...`).
5. User views the monthly spending and sum totals per category, and clicks on individual bars to inspect detailed transaction listings for specific months.

## Alternative Flows

### A1: No Budget CSV Data Uploaded

**Trigger:** No budget transactions exist in the system (step 2)
**Flow:**

1. System displays an informational message stating that no budget data is available and prompts the user to upload a CSV file via the Budget CSV Import tab first.
2. Use case ends.

## Postconditions

### Success Postconditions

- Dedicated monthly aggregation charts are rendered for every imported budget category.
- Monthly sum totals per category are correctly calculated and chronologically ordered on the X-axis.
- Clicking any monthly bar opens a detailed transaction view displaying all constituent records for that category and month without text truncation.
- Page content is fully scrollable vertically with bottom padding.

### Failure Postconditions

- An error notification is displayed if budget transaction data cannot be loaded.

## Business Rules

### BR-014: Monthly Category Sum Aggregation
Budget transaction sums must be grouped by calendar month (`YEAR` and `MONTH`) and category name. All transactions occurring within the same calendar month for a given category are summed together into a single monthly total.

### BR-015: Individual Category Chart Rendering
Every distinct budget category present in the imported CSV data must be rendered in its own separate chart card with clear monthly X-axis timeline ticks and Euro Y-axis scaling.

### BR-016: Budget Analysis Domain Isolation
The budget analysis calculations must operate exclusively on the `BUDGET_TRANSACTION` and `BUDGET_CATEGORY` data domain. They must remain completely separate from the wealth/asset management transaction entries.

### BR-017: Interactive Bar Drill-Down Inspection & Full Text Visibility
Clicking on any monthly bar in a category chart must open a popup dialog or inline detail view displaying all underlying transaction records (including date, person/user, type, amount, description) that make up that specific month's total sum for that category. The dialog must feature sufficient width (e.g. 900px) and column sizing so that all transaction texts (especially descriptions, names, and dates) are fully displayed without truncation (`...`).
