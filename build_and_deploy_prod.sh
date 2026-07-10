#!/bin/bash

# Couples Finance Tracker - Production Build & Deploy Script
# Creates a standalone, production-ready version in the 'finance-app-prod' folder

set -e

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROD_DIR="$PROJECT_DIR/finance-app-prod"

if [ ! -d "$PROD_DIR" ]; then
    echo "📁 Erstelle den Ordner 'finance-app-prod'..."
    mkdir -p "$PROD_DIR"
fi

echo "🏗️  Starte Produktions-Build..."
echo "------------------------------------------"

# 1. Setup paths to local JDK and Maven
export JAVA_HOME=$(find "$PROJECT_DIR/.jdk" -name "Home" -type d | head -n 1)
export PATH="$JAVA_HOME/bin:$PATH"
MVN_CMD="$PROJECT_DIR/.maven/bin/mvn"

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

# Setup JDK from the project's local .jdk directory
export JAVA_HOME=$(find "$DIR/../.jdk" -name "Home" -type d | head -n 1)
export PATH="$JAVA_HOME/bin:$PATH"

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
