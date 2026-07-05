#!/bin/bash

# Couples Finance Tracker - Production Build & Deploy Script
# Creates a standalone, production-ready version in the 'finance-app-prod' folder

set -e

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROD_DIR="$PROJECT_DIR/finance-app-prod"

if [ ! -d "$PROD_DIR" ]; then
    echo "❌ Fehler: Der Ordner 'finance-app-prod' existiert nicht!"
    echo "Der Prozess wird abgebrochen. Bitte erstelle den Ordner zuerst."
    exit 1
fi

echo "🏗️  Starte Produktions-Build..."
echo "------------------------------------------"

# 1. Setup paths to local JDK and Maven if global ones are missing
if command -v java &> /dev/null && ! java -version 2>&1 | grep -q "Unable to locate a Java Runtime"; then
    echo "☕ Nutze globales JDK..."
else
    if [ -d "$PROJECT_DIR/.jdk" ]; then
        echo "☕ Nutze lokales JDK aus der Entwicklungsumgebung..."
        export JAVA_HOME=$(find "$PROJECT_DIR/.jdk" -name "Home" -type d | head -n 1)
        export PATH="$JAVA_HOME/bin:$PATH"
    else
        echo "❌ Fehler: Kein JDK gefunden! Bitte starte zuerst start_dev-finance_app.command einmalig im Dev-Modus."
        exit 1
    fi
fi

if command -v mvn &> /dev/null; then
    MVN_CMD="mvn"
else
    if [ -d "$PROJECT_DIR/.maven" ]; then
        MVN_CMD="$PROJECT_DIR/.maven/bin/mvn"
    else
        echo "❌ Fehler: Kein Maven gefunden! Bitte starte zuerst start_dev-finance_app.command einmalig im Dev-Modus."
        exit 1
    fi
fi

# 2. Run Maven Production Build
echo "📦 Baue und optimiere das Vaadin Frontend & Spring Boot Backend..."
"$MVN_CMD" clean package -Pproduction

# 3. Prepare Production Directory
echo "📂 Bereite Produktions-Verzeichnis vor..."
mkdir -p "$PROD_DIR"

# Clean up old app files in production directory, but KEEP the production database file if it exists!
rm -f "$PROD_DIR/finance-app.jar"
rm -f "$PROD_DIR/start_prod.command"

# 4. Copy executable JAR
echo "🚚 Kopiere ausführbares JAR..."
cp "$PROJECT_DIR/target/finance-app-1.0.0-SNAPSHOT.jar" "$PROD_DIR/finance-app.jar"

# 5. Create production startup command
echo "📜 Erstelle Start-Skript für Produktion..."
cat <<'EOF' > "$PROD_DIR/start_prod.command"
#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

echo "🚀 Starte Couples Finance Tracker (Produktions-Modus)..."
echo "📁 Datenbank: $DIR/financedb_prod.mv.db"
echo "------------------------------------------"

# Check if Java is working, otherwise configure a local JDK auto-downloader
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
        curl -sL "https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.2%2B13/OpenJDK21U-jdk_aarch64_mac_hotspot_21.0.2_13.tar.gz" -o "$LOCAL_JDK_DIR/jdk.tar.gz"
        echo "📦 Entpacke JDK..."
        tar -xzf "$LOCAL_JDK_DIR/jdk.tar.gz" -C "$LOCAL_JDK_DIR"
        rm "$LOCAL_JDK_DIR/jdk.tar.gz"
        echo "✅ Lokales JDK eingerichtet."
    fi
    export JAVA_HOME=$(find "$LOCAL_JDK_DIR" -name "Home" -type d | head -n 1)
    export PATH="$JAVA_HOME/bin:$PATH"
fi

# Run executable JAR with production profile (enables persistent file DB)
(sleep 6 && open "http://localhost:8080") &
java -jar -Dspring.profiles.active=prod finance-app.jar

EOF

chmod +x "$PROD_DIR/start_prod.command"

echo "------------------------------------------"
echo "✅ Fertig! Die produktionsbereite App befindet sich im Ordner: finance-app-prod"
echo "🚀 Start der App mit: finance-app-prod/start_prod.command"
echo "🔗 Erreichbar unter: http://localhost:8080"
echo "------------------------------------------"
