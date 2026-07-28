# Project Rules & Strict Constraints

## Deployment & Production Bundle Restrictions

> [!CAUTION]
> **NEVER execute deployment scripts or build production packages (`./build_and_deploy_prod.sh`, `./run_build_and_deploy.command`, or modifying/creating `finance-app-prod`).**

- **Strict User Control**: Deployment to production is strictly managed by the user manually via `run_build_and_deploy.command`.
- **Directory Isolation**: The user manually moves `finance-app-prod` outside the project root during normal development.
- **Allowed Commands**: You may run `./mvnw test`, `./mvnw test-compile`, and `./mvnw verify` for verification, but **NEVER** run `./build_and_deploy_prod.sh` or create/touch `finance-app-prod`.
