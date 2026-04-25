# Security Audit Report - 2026-04-25

**Auditor:** Shield (Security Engineer)
**Status:** SAFE (for local development)

## Findings
1. **CORS Configuration:** The backend currently allows all origins (`allow_origins=["*"]`). This is acceptable for local development but must be restricted to the frontend URL in production.
2. **Data Storage:** Data is stored in a local `data.json` file without encryption. Since the app is intended for local use, this is a low risk, but users should be aware that anyone with access to the file can read their financial data.
3. **Input Sanitization:** FastAPI automatically validates types via Pydantic, which prevents basic injection attacks.
4. **Environment Variables:** No secrets (API keys, etc.) are currently used or hardcoded.

## Recommendations
- Restrict CORS origins before deploying.
- Consider file-level encryption if sensitive data is stored on shared machines.
