# 6. Runtime View

## 6.1 Data Synchronization
1. User enters data in `App.jsx`.
2. `App.jsx` calls `store.js`.
3. `store.js` triggers a POST request via `api.js`.
4. `main.py` receives the data, updates the JSON file, and returns the updated state.
5. Frontend state is refreshed automatically.

## 6.2 Application Start (Dev)
1. `start_dev-finance_app.command` is executed.
2. Backend (Uvicorn) starts on port 8000.
3. Frontend (Vite) starts on port 5173.
4. Browser opens Safari to the frontend.

## 6.3 Application Start (Live)
1. `start_app.command` (inside `finance-app-live`) is executed.
2. Unified server starts on port 8080.
3. Static assets are served from `static/`.
4. API requests are handled by the same process.
