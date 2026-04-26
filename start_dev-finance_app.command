#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

echo "🚀 Starte FinanceFlow (Entwicklungsmodus)..."
echo "------------------------------------------"

# Cleanup function to kill background processes on exit
cleanup() {
    echo ""
    echo "🛑 Beende Server..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    exit
}

# Set trap for Ctrl+C and exit
trap cleanup SIGINT SIGTERM

# 1. Start Backend
echo "🐍 Starte Backend..."
cd "$DIR/backend"
pip install -r requirements.txt > /dev/null 2>&1
uvicorn main:app --reload --port 8000 &
BACKEND_PID=$!

# 2. Start Frontend
echo "📦 Starte Frontend..."
cd "$DIR/frontend"
npm install > /dev/null 2>&1
npm run dev &
FRONTEND_PID=$!

# Wait for a few seconds then open browser
(sleep 5 && open -a Safari "http://localhost:5173") &

echo "------------------------------------------"
echo "✅ Beide Server laufen in diesem Fenster."
echo "⌨️  Drücke Ctrl+C zum Beenden."
echo "------------------------------------------"

# Keep the script running to see the logs
wait
