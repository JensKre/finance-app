# 9. Design Decisions

## 9.1 ADR 1: Use of JSON for Persistence
- **Decision**: Store data in a single `data.json` file.
- **Rationale**: Simple backup (copy file), human-readable, no database setup required. Sufficient for the expected volume of few hundred records.

## 9.2 ADR 2: Unified Live Server
- **Decision**: Backend serves static files in production.
- **Rationale**: Reduces deployment complexity from two processes to one. Avoids CORS issues in production.

## 9.3 ADR 3: Environment Data Isolation
- **Decision**: Script-relative paths for the database file.
- **Rationale**: Allows the same code to run in different folders while accessing different data files automatically based on the execution context.

## 9.4 ADR 4: Multi-Level Table Sorting
- **Decision**: Store sort criteria in a stack/array.
- **Rationale**: Enables complex sorting (e.g., sort by date within institutions) which is common for financial analysis.
