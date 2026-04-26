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
