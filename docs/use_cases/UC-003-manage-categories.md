# Use Case: Manage Categories

## Overview

**Use Case ID:** UC-003   
**Use Case Name:** Manage Categories   
**Primary Actor:** Jens & Annika   
**Goal:** Manage the list of asset categories to be used in transaction entries.   
**Status:** Approved

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Settings section.
2. System displays the list of existing categories.
3. User enters a new category name and clicks Add.
4. System validates that the category name is not empty and does not exist.
5. System records the new category.
6. System refreshes the category list and displays a success message.

## Alternative Flows

### A1: Duplicate Category Name

**Trigger:** The category name already exists in the system (step 4)
**Flow:**

1. System displays an error message stating that the category already exists.
2. Use case continues at step 3.

### A2: Delete Category

**Trigger:** User clicks Delete next to an existing category (step 2)
**Flow:**

1. System checks if any transactions are associated with this category.
2. If associated, system displays an error message stating that the category is in use.
3. If not associated, system deletes the category.
4. System updates the category list and displays a success notification.
5. Use case ends.

## Postconditions

### Success Postconditions

- The category is added or deleted.
- The updated category list is persistently stored.

### Failure Postconditions

- No changes are made to the category list.
- An error message is displayed.

## Business Rules

### BR-003: Category Name Uniqueness
Every category must have a unique name.
