#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

echo "🚀 Starte Couples Finance Tracker (Java/Vaadin)..."
echo "------------------------------------------"

# Setup JDK from the local .jdk directory
export JAVA_HOME=$(find "$DIR/.jdk" -name "Home" -type d | head -n 1)
export PATH="$JAVA_HOME/bin:$PATH"

# Setup Maven from the local .maven directory
MVN_CMD="$DIR/.maven/bin/mvn"


echo "📦 Starte Spring Boot & Vaadin Entwicklungs-Server..."
"$MVN_CMD" spring-boot:run &
APP_PID=$!

# Start a file watcher that auto-compiles Java files on change.
# This triggers Spring Boot DevTools automatic restart.
echo "👁️  Starte Datei-Überwachung für automatischen Neustart..."

(
    # Compute initial checksum of all source files
    get_checksum() {
        find "$DIR/src/main" -type f \( -name "*.java" -o -name "*.properties" -o -name "*.html" \) -exec stat -f "%m %N" {} \; 2>/dev/null | sort | md5
    }
    LAST_CHECKSUM=$(get_checksum)

    while true; do
        sleep 2
        CURRENT_CHECKSUM=$(get_checksum)
        if [ "$CURRENT_CHECKSUM" != "$LAST_CHECKSUM" ]; then
            echo ""
            echo "🔄 Änderung erkannt – kompiliere neu..."
            "$MVN_CMD" compile -q 2>&1 | tail -5
            echo "✅ Kompilierung abgeschlossen. DevTools startet die App automatisch neu."
            LAST_CHECKSUM=$(get_checksum)
        fi
    done
) &
WATCH_PID=$!

# Cleanup function to kill background processes on exit
cleanup() {
    echo ""
    echo "🛑 Beende Server..."
    kill $APP_PID 2>/dev/null
    [ -n "$WATCH_PID" ] && kill $WATCH_PID 2>/dev/null
    exit
}

# Set trap for Ctrl+C and exit
trap cleanup SIGINT SIGTERM

# Wait for a few seconds then open browser
(sleep 8 && open "http://localhost:8080") &

echo "------------------------------------------"
echo "✅ App-Server startet. Zugriff unter: http://localhost:8080"
echo "👁️  Datei-Überwachung aktiv: Änderungen werden automatisch kompiliert."
echo "⌨️  Drücke Ctrl+C in diesem Terminal zum Beenden."
echo "------------------------------------------"

# Keep the script running to see the logs
wait

