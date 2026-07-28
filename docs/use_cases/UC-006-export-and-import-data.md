# Use Case: Export & Import Backup Data

## Overview

**Use Case ID:** UC-006
**Use Case Name:** Export & Import Backup Data
**Primary Actor:** Jens & Annika
**Goal:** Backup or restore complete application data (users, categories, institutes, transactions) to/from local JSON storage files.
**Status:** Approved

## Preconditions

- The application is running and accessible.

## Main Success Scenario

1. User navigates to the Settings / Backup section.
2. User selects to perform a full system backup export or JSON data restore.
3. If exporting, user clicks the Export Backup Data button.
4. System compiles all users, categories, institutes, and transaction records into a formatted JSON backup file.
5. System triggers file download in the user's browser.
6. If restoring, user selects a previously exported JSON backup file and clicks Upload.
7. System parses and validates the JSON schema integrity and foreign key constraints.
8. System restores system entities from the JSON backup file.
9. System displays a success notification and reloads the application state.

## Alternative Flows

### A1: Invalid JSON Import Format

**Trigger:** Imported JSON file is corrupt, incomplete, or violates schema constraints (step 7)
**Flow:**

1. System displays an error notification stating that the backup file is invalid.
2. Use case continues at step 6.

## Postconditions

### Success Postconditions

- Data is safely exported to a local JSON file, or database is restored from a valid JSON backup.

### Failure Postconditions

- No changes are made to the database on failed JSON imports.
- An error notification is displayed detailing the validation issue.

## Business Rules

### BR-008: JSON Data Format Consistency
The exported and imported backup data must contain valid JSON representing all schema entities (users, institutes, categories, transactions) matching system relational constraints.


