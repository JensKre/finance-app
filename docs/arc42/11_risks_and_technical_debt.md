# 11. Risks and Technical Debt

## 11.1 Risks
- **Data Loss**: No automated off-site backup. Users must manually backup `data.json`.
- **Concurrent Access**: JSON persistence does not handle simultaneous writes from multiple clients well (unlikely in a private 1-machine setup).

## 11.2 Technical Debt
- **Single-File Frontend**: `App.jsx` is getting large (600+ lines). Future refactoring into components (e.g., `PrognosisChart.jsx`, `RecordTable.jsx`) is recommended.
- **JSON Scaling**: If the record count grows to thousands, loading the entire file for every request will become slower.
