# Use Case: Enter Transaction

## Overview

**Use Case ID:** UC-001
**Use Case Name:** Enter Transaction
**Primary Actor:** Jens & Annika
**Goal:** Allows a user (Jens or Annika) to enter and track a new set of financial entries (amounts and categories) for all active accounts (combinations of institution and category) for a specific date, showing previously recorded values for guidance.
**Status:** Updated

## Preconditions

- The application is running and accessible.
- Active financial institutions and asset categories exist in the system.

## Main Success Scenario

1. User selects their individual tab (Jens or Annika).
2. System displays the historical entry dates list and the transaction entry form for the selected date.
3. System lists all active accounts (combinations of institution and category) in the form, displaying the most recently recorded amount for each account for user reference.
4. User sets or modifies the entry date for the new dataset.
5. For each account, the user enters the new amount and selects or confirms the category.
6. User submits the entries for the selected date.
7. System validates the entry details (amount formats, valid categories).
8. System records the new transaction entries for each account for the specified date.
9. System updates the historical entry dates list, updates the dashboard totals, and displays a success notification.

## Alternative Flows

### A1: Invalid Entry Data

**Trigger:** System detects empty fields or invalid amount formats in any of the entries (step 7)
**Flow:**

1. System displays an error message indicating which fields or entries are invalid.
2. Use case continues at step 5.

### A2: Modify/Delete Existing Date Entries

**Trigger:** User selects a historical entry date from the list (step 2)
**Flow:**

1. System displays the recorded transaction entries for all accounts on that specific date.
2. User modifies the amounts/categories or clicks the delete action to remove all entries for that date.
3. If modifying, user submits the entries (use case continues at step 6).
4. If deleting, system displays a confirmation dialog asking the user to confirm the deletion.
5. User confirms the deletion.
6. System deletes the transaction entries for that date.
7. System updates the entry dates list, updates the dashboard totals, and displays a success notification.
8. Use case ends.

### A3: Cancel Deletion

**Trigger:** User cancels the deletion in the confirmation dialog (step 5 of A2)
**Flow:**

1. System closes the confirmation dialog without deleting any entries.
2. Use case continues at step 2.

## Postconditions

### Success Postconditions

- The transaction entries for all accounts for the specified date are persistently saved (if added/modified) or permanently removed (if deleted).
- The user's historical entries list and dashboard totals are updated.

### Failure Postconditions

- No transaction entries are saved, modified, or deleted.
- An error message is displayed.

## Business Rules

### BR-001: Amount Format

The transaction amount must be a positive number with up to 2 decimal places.

### BR-007: Currency and Number Formatting

All displayed monetary values must include thousands separators and a currency symbol, formatted according to the German locale (e.g., dot as thousands separator, comma as decimal separator: "123.456,78 €").
