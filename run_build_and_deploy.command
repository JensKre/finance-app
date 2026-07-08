#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
echo "🛠️  Auslösen des Build & Deploy Prozesses..."
./build_and_deploy_prod.sh
echo ""
echo "✅ Vorgang abgeschlossen. Drücken Sie eine beliebige Taste zum Beenden..."
read -n 1

