# Use Case: Forecast Wealth

## Overview

**Use Case ID:** UC-005   
**Use Case Name:** Forecast Wealth   
**Primary Actor:** Jens & Annika   
**Goal:** Estimate future wealth based on expected savings rates and annual return rates.   
**Status:** Approved

## Preconditions

- The application is running.

## Main Success Scenario

1. User navigates to the Forecasting section.
2. System displays default input parameters (e.g. monthly savings, expected yearly return).
3. User enters their custom forecast parameters and specifies the target period in years.
4. User clicks Calculate.
5. System computes the compound growth over the period using the current total wealth as the starting point.
6. System displays the forecast result as a line chart and text summary.

## Alternative Flows

### A1: Invalid Parameters

**Trigger:** User enters negative or invalid values (step 3)
**Flow:**

1. System highlights invalid inputs and displays an error message.
2. Use case continues at step 2.

## Postconditions

### Success Postconditions

- Future wealth projections are displayed.

### Failure Postconditions

- Projections are not computed; error is displayed.

## Business Rules

### BR-005: Compound Formula
Forecasting must be calculated using compound interest formula compounded monthly.
