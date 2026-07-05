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

## Postconditions

### Success Postconditions

- The transaction is persistently saved.
- The user's transaction table is updated.

### Failure Postconditions

- No transaction is saved.
- An error message is displayed.

## Business Rules

### BR-001: Amount Format
The transaction amount must be a positive number with up to 2 decimal places.
