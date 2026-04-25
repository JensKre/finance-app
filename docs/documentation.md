# Project Documentation: FinanceFlow

## 1. Introduction and Goals
FinanceFlow is a local web application for couples (Jens & Annika) to track their joint finances and forecast future wealth development.

## 2. Architecture Constraints
- **Local First:** All data is stored locally in a JSON file.
- **Technology:** React (Vite) for the frontend, FastAPI (Python) for the backend.

## 3. System Scope and Context
The system consists of a web frontend and a REST backend. It does not currently connect to external banking APIs (manual entry).

## 4. Technical Context
- **Frontend:** `localhost:5173` (Vite default)
- **Backend:** `localhost:8000` (Uvicorn default)
- **Storage:** `backend/data.json`

## 5. Building Block View
- **Frontend Components:**
  - `App`: Main layout, tab management, Modals, and Toasts.
  - `Dashboard`: Aggregated view, Pie Chart (distribution), Line Chart (history), and Forecasting.
  - `PersonView`: Individual data entry and history list.
- **Backend:**
  - `main.py`: API routes for full CRUD operations and JSON persistence.
  - `models.py`: Data schemas for records and parameters.

## 6. Features (Added in Sprint 2)
- **Data Visualization:** Integrated `recharts` for visual asset distribution and historical trends.
- **Advanced Management:** Full Edit Modal for existing records.
- **UX:** Toast notification system for action feedback.
- **Portability:** JSON Data Export functionality.
