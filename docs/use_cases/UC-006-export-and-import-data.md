# Use Case: Export & Import Data

## Overview

**Use Case ID:** UC-006
**Use Case Name:** Export & Import Data
**Primary Actor:** Jens & Annika
**Goal:** Backup or restore data to/from local storage files.
**Status:** Approved

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Settings section.
2. User clicks the Export Data button.
3. System compiles all users, categories, institutes, and transactions into a single file and offers it for download.
4. User selects a downloaded file to import and clicks Upload.
5. System validates the file format and integrity.
6. System overwrites the current records with the imported ones.
7. System displays a success message and reloads the application state.

## Alternative Flows

### A1: Invalid Import Format

**Trigger:** Imported file is corrupt or violates schema (step 5)
**Flow:**

1. System displays an error message stating that the file is invalid.
2. Use case continues at step 4.

## Postconditions

### Success Postconditions

- Data is exported to file, or imported data is stored in the database.

### Failure Postconditions

- No changes are made to the database on failed imports.

## Business Rules

### BR-006: Data Format Consistency

The imported data must contain valid JSON representing all schema tables, matching primary and foreign key constraints.
