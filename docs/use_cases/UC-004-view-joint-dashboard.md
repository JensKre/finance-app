# Use Case: View Joint Dashboard

## Overview

**Use Case ID:** UC-004   
**Use Case Name:** View Joint Dashboard   
**Primary Actor:** Jens & Annika   
**Goal:** Display aggregated financial data for Jens, Annika, and their combined net worth.   
**Status:** Approved

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Dashboard tab.
2. System retrieves all transaction entries.
3. System aggregates the data (total assets by category, total assets by owner).
4. System displays:
   - A summary card with combined total wealth.
   - An interactive chart showing assets distribution by category.
   - A trend line showing wealth development over time.
   - A list of recent transactions.
5. User can scroll through the recent transactions list and dashboard content.
6. User can toggle between individual views (Jens only, Annika only) or combined view.

## Alternative Flows

### A1: No Data Found

**Trigger:** No transaction entries exist in the system (step 2)
**Flow:**

1. System displays a message suggesting the user to enter their first transaction.
2. Use case ends.

## Postconditions

### Success Postconditions

- User views an accurate dashboard of assets.

### Failure Postconditions

- An error message is shown if data cannot be retrieved.

## Business Rules

### BR-004: Dashboard Aggregation
Combined wealth must be the sum of all transactions for both Jens and Annika.
