# Use Case: Manage Institutes

## Overview

**Use Case ID:** UC-002   
**Use Case Name:** Manage Institutes   
**Primary Actor:** Jens & Annika   
**Goal:** Manage the list of financial institutes to be used in transaction entries.   
**Status:** Approved

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Settings section.
2. System displays the list of existing institutes.
3. User enters a new institute name and clicks Add.
4. System validates that the institute name is not empty and does not exist.
5. System records the new institute.
6. System refreshes the institute list and displays a success message.

## Alternative Flows

### A1: Duplicate Institute Name

**Trigger:** The institute name already exists in the system (step 4)
**Flow:**

1. System displays an error message stating that the institute already exists.
2. Use case continues at step 3.

### A2: Delete Institute

**Trigger:** User clicks Delete next to an existing institute (step 2)
**Flow:**

1. System checks if any transactions are associated with this institute.
2. If associated, system displays an error message stating that the institute is in use.
3. If not associated, system deletes the institute.
4. System updates the institute list and displays a success notification.
5. Use case ends.

## Postconditions

### Success Postconditions

- The institute is added or deleted.
- The updated institute list is persistently stored.

### Failure Postconditions

- No changes are made to the institute list.
- An error message is displayed.

## Business Rules

### BR-002: Institute Name Uniqueness
Every institute must have a unique name.
