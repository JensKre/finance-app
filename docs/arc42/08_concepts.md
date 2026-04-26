# 8. Concepts

## 8.1 Persistence
- **JSON-Only**: No external database (SQL/NoSQL) is required to minimize setup effort.
- **Atomic Operations**: Backend reads/writes the whole file on every change to ensure consistency for low-volume data.

## 8.2 Calculation Logic
- **Latest-Per-Account**: Total wealth is calculated by summing the most recent entry for every unique `person-institution-category` combination.
- **Compound Interest**: The 25-year prognosis uses a recursive compound interest formula considering monthly savings and yearly growth.

## 8.3 User Interface
- **Responsiveness**: Flexbox and CSS Grid are used for a dashboard that works across different screen sizes.
- **Visual Feedback**: Real-time updates and toast notifications (via `setToast`) provide immediate user feedback.
- **Aesthetics**: Dark mode theme with blue/indigo primary colors.
