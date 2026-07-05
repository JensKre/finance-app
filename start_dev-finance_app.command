#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

echo "🚀 Starte Couples Finance Tracker (Java/Vaadin)..."
echo "------------------------------------------"

# Check if Java is actually working (not just the macOS stub)
JAVA_WORKING=true
if ! command -v java &> /dev/null; then
    JAVA_WORKING=false
elif java -version 2>&1 | grep -q "Unable to locate a Java Runtime"; then
    JAVA_WORKING=false
fi

if [ "$JAVA_WORKING" = false ]; then
    echo "ℹ️  Keine funktionierende Java-Laufzeitumgebung gefunden. Richte lokales JDK 21 ein..."
    LOCAL_JDK_DIR="$DIR/.jdk"
    if [ ! -d "$LOCAL_JDK_DIR" ]; then
        echo "📥 Lade OpenJDK 21 (macOS arm64) herunter..."
        mkdir -p "$LOCAL_JDK_DIR"
        # Download Temurin JDK 21 tar.gz
        curl -sL "https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.2%2B13/OpenJDK21U-jdk_aarch64_mac_hotspot_21.0.2_13.tar.gz" -o "$LOCAL_JDK_DIR/jdk.tar.gz"
        echo "📦 Entpacke JDK..."
        tar -xzf "$LOCAL_JDK_DIR/jdk.tar.gz" -C "$LOCAL_JDK_DIR"
        rm "$LOCAL_JDK_DIR/jdk.tar.gz"
        echo "✅ Lokales JDK eingerichtet."
    fi
    # Set JAVA_HOME dynamically
    export JAVA_HOME=$(find "$LOCAL_JDK_DIR" -name "Home" -type d | head -n 1)
    export PATH="$JAVA_HOME/bin:$PATH"
fi


# Check if Maven is installed globally, otherwise set up a local one
if command -v mvn &> /dev/null
then
    MVN_CMD="mvn"
else
    echo "ℹ️  Globales Maven (mvn) nicht gefunden. Richte lokales Maven ein..."
    LOCAL_MAVEN_DIR="$DIR/.maven"
    if [ ! -d "$LOCAL_MAVEN_DIR" ]; then
        echo "📥 Lade Apache Maven herunter..."
        mkdir -p "$LOCAL_MAVEN_DIR"
        # Download Maven 3.9.6 tarball
        curl -sL https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz -o "$LOCAL_MAVEN_DIR/maven.tar.gz"
        echo "📦 Entpacke Maven..."
        tar -xzf "$LOCAL_MAVEN_DIR/maven.tar.gz" -C "$LOCAL_MAVEN_DIR" --strip-components=1
        rm "$LOCAL_MAVEN_DIR/maven.tar.gz"
        echo "✅ Lokales Maven erfolgreich eingerichtet."
    fi
    MVN_CMD="$LOCAL_MAVEN_DIR/bin/mvn"
fi


echo "📦 Starte Spring Boot & Vaadin Entwicklungs-Server..."
"$MVN_CMD" spring-boot:run &
APP_PID=$!

# Cleanup function to kill background processes on exit
cleanup() {
    echo ""
    echo "🛑 Beende Server..."
    kill $APP_PID 2>/dev/null
    exit
}

# Set trap for Ctrl+C and exit
trap cleanup SIGINT SIGTERM

# Wait for a few seconds then open browser
(sleep 8 && open "http://localhost:8080") &

echo "------------------------------------------"
echo "✅ App-Server startet. Zugriff unter: http://localhost:8080"
echo "⌨️  Drücke Ctrl+C in diesem Terminal zum Beenden."
echo "------------------------------------------"

# Keep the script running to see the logs
wait

