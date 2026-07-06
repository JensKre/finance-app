# Use Case: Enter Transaction

## Overview

**Use Case ID:** UC-001   
**Use Case Name:** Enter Transaction   
**Primary Actor:** Jens & Annika   
**Goal:** Allows a user (Jens or Annika) to enter a new financial entry for a specific date, category, and institute, so it can be tracked.   
**Status:** Approved

## Preconditions

- The application is running and accessible.
- Valid institutes and categories exist in the system.

## Main Success Scenario

1. User selects their individual tab (Jens or Annika).
2. System displays the input form and the list of existing transactions.
3. User enters the amount, selects the institute, selects the category, and sets the transaction date.
4. User submits the transaction.
5. System validates the entry details.
6. System records the new transaction entry.
7. System updates the transactions list and displays a success notification.

## Alternative Flows

### A1: Invalid Entry Data

**Trigger:** System detects empty fields or invalid amount (step 5)
**Flow:**

1. System displays an error message indicating which fields are invalid.
2. Use case continues at step 3.

### A2: Delete Transaction

**Trigger:** User clicks the delete action on an existing transaction entry (step 2)
**Flow:**

1. System displays a confirmation dialog asking the user to confirm the deletion.
2. User confirms the deletion.
3. System deletes the transaction entry.
4. System updates the transactions list, updates the dashboard totals, and displays a success notification.
5. Use case ends.

### A3: Cancel Deletion

**Trigger:** User cancels the deletion in the confirmation dialog (step 1 of A2)
**Flow:**

1. System closes the confirmation dialog without deleting the entry.
2. Use case continues at step 2.

## Postconditions

### Success Postconditions

- The transaction is persistently saved (if added) or permanently removed (if deleted).
- The user's transaction table is updated.

### Failure Postconditions

- No transaction is saved or deleted.
- An error message is displayed.

## Business Rules

### BR-001: Amount Format
The transaction amount must be a positive number with up to 2 decimal places.

### BR-002: Currency and Number Formatting
All displayed monetary values must include thousands separators and a currency symbol, formatted according to the German locale (e.g., dot as thousands separator, comma as decimal separator: "123.456,78 €").

