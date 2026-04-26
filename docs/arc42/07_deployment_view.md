# 7. Deployment View

## 7.1 Infrastructure
The application is deployed on a local macOS machine.

```mermaid
node "Local Machine" {
  [Browser (Safari)]
  
  subgraph "Live Environment Folder"
    [start_app.command]
    [main.py (Unified Server)]
    [data.json (Live Data)]
    folder "static/" {
      [Frontend Assets (JS/CSS/HTML)]
    }
  end
}
```

## 7.2 Environment Separation
| Environment | Folder | Port | Database |
|---|---|---|---|
| Development | `backend/` & `frontend/` | 8000 / 5173 | `backend/data.json` |
| Live | `finance-app-live/` | 8080 | `finance-app-live/data.json` |

## 7.3 Build Process
The `build_and_deploy.sh` script automates:
1. Compiling the React frontend.
2. Cleaning up old application files in the Live folder (preserving data).
3. Copying new backend and frontend assets.
4. Generating the start command.
