#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

echo "🚀 Starte FinanceFlow..."

# Start Backend in a new terminal window
osascript -e "tell application \"Terminal\" to do script \"cd '$DIR/backend' && pip install -r requirements.txt && uvicorn main:app --reload\""

# Start Frontend in a new terminal window
osascript -e "tell application \"Terminal\" to do script \"cd '$DIR/frontend' && npm install && npm run dev\""

# Wait for the servers and installations (especially npm install can take a moment)
sleep 15
open -a Safari "http://localhost:5173"

echo "✅ Terminal-Fenster wurden geöffnet und Safari gestartet."
