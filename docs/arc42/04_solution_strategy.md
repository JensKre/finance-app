# 4. Solution Strategy

## 4.1 Frontend Architecture
- **State Management**: Zustand (via `store.js`) for centralized data handling.
- **Component Design**: Unified `App.jsx` for the core dashboard and logic to keep complexity low for a small-scale app.
- **Dynamic Charting**: Using `recharts` for interactive data visualization.

## 4.2 Backend Architecture
- **Service Layer**: FastAPI for high-performance API endpoints.
- **Persistence Layer**: Simple JSON file storage with script-relative path resolution to support environment isolation.

## 4.3 Deployment Strategy
- **Unified Server**: In production (Live mode), the FastAPI server serves both the API and the static React build, simplifying the runtime to a single process.
- **Environment Isolation**: Separate directories (`backend/` vs `finance-app-live/`) ensure that dev and live data never mix.
