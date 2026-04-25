---
description: Structured multi-agent workflow for feature development, testing, and security.
---

## 1. Initiation Phase

* **Trigger:** Vision and features are described by the user (Human).
* **Action @philipp_product-owner:** Write User Stories including acceptance criteria for the mentioned features.

## 2. Implementation Phase

* **Action @bastian_backend & @fabian_frontend:** Create the code draft and start implementation for the sprint.

## 3. Quality Assurance & Validation

*Note: If any step results in "no," the workflow returns to the developers for implementation adjustments.*

### 3.1 Code Review

* **Action @gunnar_code-reviewer:** Perform the code review and document the results in the file code-review-report.md in the `agent/` folder with a tag and date.
* **Decision:** If rejected, @bastian_backend and @fabian_frontend must implement the review findings.

### 3.2 Unit Testing

* **Action @tarzan_unit-test:** Write and execute unit tests locally; document the results in the file unit-test-report.md in the `agent/` folder with a tag and date.
* **Decision:** If tests fail, return to the implementation phase.

### 3.3 Integration Testing

* **Action @ingo_integration-test:** Write and execute integration tests; document the results in the file integration-test-report.md in the `agent/` folder with a tag and date.
* **Decision:** If tests fail, return to the implementation phase.

### 3.4 End-to-End (E2E) Testing

* **Action @elena_e2e-test:** Execute E2E tests with the browser subagent; document the results in the file e2e-test-report.md in the `agent/` folder with a tag and date.
* **Decision:** If tests fail, return to the implementation phase.

## 4. Security & Documentation

### 4.1 Security Review

* **Action @shield_security-engineering:** Verify code security and document the results in the file security-audit-report.md in the `agent/` folder with a tag and date.
* **Decision:** If security issues are found, @bastian_backend and @fabian_frontend must resolve the identified themes.

### 4.2 Documentation

* **Action @arthur_documentation:** Update the project documentation within the `docs/arc42` folder.

## 5. Finalization

* **Status:** Feature is completed.
* **Check:** All packages and dependencies are updated.
* **Handoff:** Final confirmation sent to the user (Human).
