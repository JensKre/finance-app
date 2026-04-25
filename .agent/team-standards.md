# 🛠 Team Standards & Engineering Culture - Arbitrage Project

## 1. Universal Coding Standards

- **Core Tech Stack:**
  - **Frontend:** React (Vite) with Vanilla CSS (for premium design) or Tailwind if requested. State via Zustand.
  - **Backend:** Python (FastAPI) for high-performance data processing and exchange integration.
  - **Exchanges:** Mandatory use of `ccxt` for Binance and Coinbase integration.
- **Naming & Readability:**
  - Python: `snake_case` for functions/variables, `PascalCase` for classes.
  - JavaScript/React: `camelCase` for variables, `PascalCase` for components, `kebab-case` for file names.
- **Documentation:** Every feature must be documented in `sprint-log.md`. Backend code should include docstrings (Google/Sphinx style).
- **Security:** API Keys MUST never be hardcoded. Use `.env` files and ensure they are git-ignored.
- **Clean Code:** Prioritize readability over cleverness. Adhere to DRY (Don't Repeat Yourself) and KISS (Keep It Simple, Stupid).

## 2. AI-Native Scrum Lifecycle

- **Vision & Planning:** Philipp (Product Owner) maintains the `product-vision.md` and `sprint-log.md`.
- **Backend Architecture:** Bastian (Lead Backend) is responsible for Python logic, `ccxt` integration, and API design.
- **Frontend Excellence:** Fabian (Senior Frontend) is responsible for the React UI, modern aesthetics, and connecting to Bastian's API.
- **Quality Assurance:** Gunnar (Code Reviewer) reviews all major changes before they are considered "Done".

## 3. Definition of Done (DoD)

- **Functionality:** The feature works as described in the User Story (Acceptance Criteria).
- **Design:** UI matches the "Modern & Premium" goal (e.g., proper spacing, high-quality typography, smooth interactions).
- **Code Quality:** No linting errors. Python code follows PEP8. React code is modular.
- **Testing:** Manual verification of API connectivity and data accuracy is required for every sprint.
- **Documentation:** The `walkthrough.md` is updated with screenshots/recordings of the new functionality.

## 4. Communication & Ownership

- **Local First:** All development and execution happen locally on the Mac.
- **Atomic Edits:** Changes should be focused and well-documented.
- **Collaboration:** Frontend and Backend must agree on API schemas before implementation starts.
