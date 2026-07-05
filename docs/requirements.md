# Requirements Catalog: Couples Finance Tracker

Based on [vision.md](file:///Users/jenskreutzer/Antigravity_Projects/finance-app/docs/vision.md).

## Functional Requirements (FR)

| ID     | Title                 | User Story                                                                                                               | Priority | Status |
|--------|-----------------------|--------------------------------------------------------------------------------------------------------------------------|----------|--------|
| FR-001 | Individual Data Entry | As Jens or Annika, I want to enter my current financial data (amount, institute, category, date) in my own tab.           | High     | Open   |
| FR-002 | Custom Institutes     | As a user, I want to dynamically add custom financial institutes (e.g. Sparkasse, Binance) so they are available in dropdowns. | High     | Open   |
| FR-003 | Custom Categories     | As a user, I want to dynamically add custom asset categories (e.g. ETF, Crypto, Gold) so they are available in dropdowns. | High     | Open   |
| FR-004 | Historical Tracking   | As a user, I want to view my past entry history and modify entry dates retroactively to keep records accurate.            | Medium   | Open   |
| FR-005 | Shared Dashboard      | As Jens & Annika, we want to view a joint dashboard aggregating both of our financial data into a total net worth.      | High     | Open   |
| FR-006 | Future Forecasting    | As Jens & Annika, we want to forecast our future net worth based on monthly savings rate and expected annual return rate. | Medium   | Open   |
| FR-007 | Edit/Delete Entries   | As a user, I want to edit or delete existing entries to correct mistakes.                                                | High     | Open   |
| FR-008 | Data Export & Import  | As a user, I want to export and import my financial data JSON file for backup and portability.                            | Medium   | Open   |

## Non-Functional Requirements (NFR)

| ID      | Title            | Requirement                                                                                | Category     | Priority | Status |
|---------|------------------|--------------------------------------------------------------------------------------------|--------------|----------|--------|
| NFR-001 | Low Latency      | Dashboard aggregation and chart rendering must complete in under 500ms.                   | Performance  | High     | Open   |
| NFR-002 | Offline First    | The application must load and function fully offline using local storage resources.         | Usability    | High     | Open   |
| NFR-003 | Mobile Friendly  | The UI must adjust layout responsively for smartphones, tablets, and desktop screen sizes. | Usability    | High     | Open   |

## Constraints (C)

| ID    | Title             | Constraint                                                                   | Category  | Priority | Status |
|-------|-------------------|------------------------------------------------------------------------------|-----------|----------|--------|
| C-001 | Storage Platform  | All financial records must be persisted locally in a single `data.json` file. | Technical | High     | Open   |
| C-002 | Runtime Platform  | The backend must run on Python 3 using FastAPI.                              | Technical | High     | Open   |
| C-003 | Frontend Platform | The frontend must be a single-page application built with Vite and JS.       | Technical | High     | Open   |
| C-004 | Browser Support   | The app must be fully functional on modern versions of Chrome, Safari, and Firefox. | Technical | High     | Open   |
