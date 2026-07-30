# Use Case: Import Budget CSV

## Overview

**Use Case ID:** UC-007
**Use Case Name:** Import Budget CSV
**Primary Actor:** Jens & Annika
**Goal:** Incrementally import transaction records from expanding budget CSV export files into the database without duplicating existing data.
**Status:** Proposed

## Preconditions

- The application is running and accessible.

## Main Success Scenario

1. User navigates to the Budget CSV Import section.
2. System displays the page header including metadata of the most recently uploaded CSV file (filename and upload timestamp), if a previous upload exists.
3. System displays a table below the upload control showing a preview of the 100 most recent transaction entries imported into the system. The entire CSV Import view layout is scrollable vertically with sufficient bottom padding so that the full preview table and page content can be comfortably scrolled into view without cutoff.
4. User selects a budget CSV export file (formatted with semicolon delimiters: `Datum;Typ;Betrag;Kategorie;Person;Beschreibung`) and initiates the upload.
5. System parses the CSV file structure and validates column headers, delimiter format, and row data types.
6. System checks each CSV row against existing transaction records in the database to identify new entries vs. already imported records.
7. System imports only the non-existing transaction records into the database while skipping already existing records to prevent duplication.
8. System registers any newly encountered categories from the CSV file as distinct budget expense categories, keeping them completely separate and independent from the master asset/wealth categories.
9. System updates the metadata at the top of the page with the newly uploaded filename and timestamp.
10. System updates the preview table below to display the 100 most recent imported budget transaction entries.
11. System displays a summary notification indicating the number of newly imported records and skipped duplicate records.

## Alternative Flows

### A1: Invalid CSV Header or File Format

**Trigger:** Uploaded file is corrupt, empty, or fails header validation (step 5)
**Flow:**

1. System displays an error notification specifying that the CSV file format or header structure is invalid.
2. Use case continues at step 4.

### A2: All Transactions Already Exist

**Trigger:** System determines all rows in the CSV file already exist in the database (step 7)
**Flow:**

1. System skips insertion of all records.
2. System displays an informational notification stating that 0 new records were imported and all records were already up to date.
3. Use case ends.

## Postconditions

### Success Postconditions

- New transaction entries from the CSV file are stored in the database.
- The name of the last uploaded CSV file is displayed at the top of the page.
- A table showing the 100 most recent imported budget transactions is displayed below the upload option within a full-page scrollable container.
- Imported budget categories are maintained independently without polluting or mixing with asset/wealth master categories.
- Existing database transactions remain untouched without duplication.

### Failure Postconditions

- No invalid or partial transactions are inserted into the database.
- An error notification detailing the import issue is displayed.

## Business Rules

### BR-009: Incremental CSV Delta Import
When importing expanding budget CSV files, record uniqueness must be determined by evaluating unique record attributes (such as combination of date, user/person, category, amount, and description). Only rows not yet present in the system database are inserted.

### BR-010: Budget CSV Format Specification & Amount Precision
The CSV file must use semicolon (`;`) delimiters with German decimal numbers (comma as decimal mark) and contain the exact headers: `Datum;Typ;Betrag;Kategorie;Person;Beschreibung`. Raw decimal amounts with arbitrary floating point precision (e.g. `4,8799999999999999`) are automatically rounded to 2 decimal places using standard half-up commercial rounding (e.g. `4,88 €`) before duplicate detection and database persistence.

### BR-011: Category Domain Separation
Categories imported from budget CSV files (e.g. `Lebensmittel`, `Kleidung`, `Transport`) must be treated as independent budget expense categories. They must not be merged, overwritten, or mixed with the master asset categories used for wealth tracking (e.g. `Tagesgeld+Sparkonto+Girokonto`, `Aktien`).

### BR-012: Upload Metadata & Recent Entries Display & Page Scrollability
The CSV import page must display the filename of the most recently uploaded CSV file at the top of the view. Below the upload control, a data grid must display up to 100 of the most recent budget transactions (ordered by transaction date descending) to give users immediate visual confirmation of imported data. The outer container of the view must allow vertical page scrolling (overflow-y auto) and include generous bottom margin/padding (e.g. 64px) to ensure the bottom rows of the grid and floating UI buttons do not obscure content.


