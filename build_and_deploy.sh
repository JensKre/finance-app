#!/bin/bash

# FinanceFlow Build & Deploy Script
# Creates a standalone version in the 'finance-app-live' folder
# IMPORTANT: Dev and Live use separate data.json files!

set -e

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
LIVE_DIR="$PROJECT_DIR/finance-app-live"

echo "🏗️  Starte Build-Prozess..."

# 1. Frontend Build
echo "📦 Baue Frontend..."
cd "$PROJECT_DIR/frontend"
npm install
npm run build

# 2. Live-Ordner vorbereiten (data.json NIEMALS löschen!)
echo "📂 Bereite Live-Ordner vor..."

# Nur die App-Dateien löschen, NICHT die data.json
if [ -d "$LIVE_DIR" ]; then
    rm -f "$LIVE_DIR/main.py"
    rm -f "$LIVE_DIR/models.py"
    rm -f "$LIVE_DIR/requirements.txt"
    rm -f "$LIVE_DIR/start_app.command"
    rm -rf "$LIVE_DIR/static"
    echo "🗂️  Alte App-Dateien entfernt. Live-Datenbank bleibt erhalten."
else
    echo "📁 Erstelle neuen Live-Ordner."
fi

mkdir -p "$LIVE_DIR/static"

# 3. Backend kopieren (NICHT die data.json!)
echo "🐍 Kopiere Backend-Dateien..."
cp "$PROJECT_DIR/backend/main.py" "$LIVE_DIR/"
cp "$PROJECT_DIR/backend/models.py" "$LIVE_DIR/"
cp "$PROJECT_DIR/backend/requirements.txt" "$LIVE_DIR/"

# 4. Frontend-Build kopieren
echo "🎨 Kopiere Frontend-Assets..."
cp -r "$PROJECT_DIR/frontend/dist/"* "$LIVE_DIR/static/"

# 5. Start-Skript für Live-App erstellen
echo "📜 Erstelle Start-Skript..."
cat <<'EOF' > "$LIVE_DIR/start_app.command"
#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
echo "🚀 Starte FinanceFlow (Live-Modus)..."
echo "📁 Datenbank: $DIR/data.json"
# Install dependencies if needed
pip install -r requirements.txt
# Start unified server and open browser
sleep 3 && open -a Safari "http://localhost:8080" &
uvicorn main:app --host 127.0.0.1 --port 8080
EOF

chmod +x "$LIVE_DIR/start_app.command"

# 6. Fertigstellung
echo ""
echo "✅ Fertig! Die Live-App befindet sich im Ordner: finance-app-live"
echo "📊 Dev-Datenbank:  $PROJECT_DIR/backend/data.json"
echo "📊 Live-Datenbank: $LIVE_DIR/data.json"
echo "🚀 Start mit: finance-app-live/start_app.command"
echo "🔗 Erreichbar unter: http://localhost:8080"
