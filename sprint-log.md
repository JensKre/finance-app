# Sprint Log

## Sprint 2: Visualisierung & Erweitertes Management

**Ziel:** Verbesserung der Datenvisualisierung durch Diagramme und Erweiterung der Verwaltungsfunktionen (Institute/Export).

### Epic 6: Erweiterte Visualisierung
- [ ] **US 6.1 - Vermögensverteilung (Pie Chart)**
  - *AC 1:* Das Dashboard zeigt ein Kuchendiagramm der aktuellen Vermögensverteilung nach Kategorien.
  - *AC 2:* Das Diagramm lässt sich zwischen Jens, Annika und Gesamt umschalten.
- [ ] **US 6.2 - Historische Entwicklung (Line Chart)**
  - *AC 1:* Ein Liniendiagramm im Dashboard zeigt die Entwicklung des Gesamtvermögens über die Zeit (basierend auf den Log-Einträgen).

### Epic 7: Erweitertes Datenmanagement
- [ ] **US 7.1 - Datensätze bearbeiten (Full Implementation)**
  - *AC 1:* Ein Klick auf "Bearbeiten" öffnet ein Modal mit den vorausgefüllten Daten des Eintrags.
  - *AC 2:* Änderungen werden im Backend gespeichert und die Ansicht aktualisiert sich sofort.
- [ ] **US 7.2 - Daten Export/Import**
  - *AC 1:* Es gibt einen Button zum Exportieren der `data.json` als Download.
  - *AC 2:* Ein Import-Button erlaubt das Hochladen einer JSON-Datei, um Daten wiederherzustellen.

### Epic 8: UX & Validierung
- [ ] **US 8.1 - Feedback-System (Toasts)**
  - *AC 1:* Erfolgreiche Aktionen (Speichern, Löschen) werden durch eine kurze Erfolgsmeldung bestätigt.
  - *AC 2:* Fehler bei der API-Kommunikation werden dem Nutzer verständlich angezeigt.

---

## Sprint 1: MVP - Datenerfassung & Dashboard (COMPLETED)

**Ziel:** Aufbau der Infrastruktur und Umsetzung der Basis-Funktionen zur Datenerfassung für Jens & Annika.

### Epic 1: Grundlegende Datenverwaltung
- [ ] **US 1.1 - Individuelle Ansicht**
  - *AC 1:* Es gibt zwei separate Tabs für "Jens" und "Annika".
  - *AC 2:* Beim Wechsel der Tabs werden nur die Daten der jeweiligen Person angezeigt.
- [ ] **US 1.2 - Datenerfassung**
  - *AC 1:* Ein Formular erlaubt die Eingabe von Betrag, Institut, Kategorie und Datum.
  - *AC 2:* Pflichtfelder sind Betrag und Institut.
  - *AC 3:* Nach dem Speichern erscheint der Eintrag in der Liste der jeweiligen Person.
- [ ] **US 1.3 - Datum anpassen**
  - *AC 1:* Das Datum kann bei der Erfassung frei gewählt werden (Standard: heute).
  - *AC 2:* Das Datum kann bei der Bearbeitung eines bestehenden Eintrags geändert werden.
- [ ] **US 1.4 - Datensätze bearbeiten**
  - *AC 1:* Jeder Listeneintrag hat einen "Bearbeiten"- und einen "Löschen"-Button.
  - *AC 2:* Löschen erfordert eine kurze Bestätigung.

### Epic 2: Institute & Kategorien
- [ ] **US 2.1 - Flexible Kategorien**
  - *AC 1:* Ein Dropdown bietet Kategorien wie Girokonto, Tagesgeld, ETF, Krypto, Gold.
  - *AC 2:* Einträge werden mit dem Icon/Label der Kategorie dargestellt.
- [ ] **US 2.2 - Neues Institut hinzufügen**
  - *AC 1:* Innerhalb des Formulars kann ein neues Institut manuell eingetippt werden.
  - *AC 2:* Bereits verwendete Institute werden als Vorschlag (Autofill/Dropdown) angezeigt.

### Epic 3: Historie & Verlauf
- [ ] **US 3.1 - Historischer Verlauf**
  - *AC 1:* Unter dem Formular wird eine Tabelle aller Einträge der Person angezeigt.
  - *AC 2:* Die Liste ist chronologisch absteigend sortiert (neueste zuerst).

### Epic 4: Gemeinsames Dashboard & Ausblick
- [ ] **US 4.1 - Gesamtsumme**
  - *AC 1:* Das Dashboard zeigt die aktuelle Summe von Jens, Annika und die Gesamtsumme.
  - *AC 2:* Die Summen basieren auf den jeweils aktuellsten Einträgen pro Institut/Kategorie.
- [ ] **US 4.2 - Prognose**
  - *AC 1:* Ein Diagramm (Line Chart) zeigt die Vermögensentwicklung für die nächsten 5-10 Jahre.
  - *AC 2:* Die Basis ist die aktuelle Gesamtsumme.
- [ ] **US 4.3 - Manuelle Prognose-Parameter**
  - *AC 1:* Eingabefelder für "Monatliche Sparrate (€)" und "Erwartetes Wachstum (% p.a.)".
  - *AC 2:* Das Prognose-Diagramm aktualisiert sich sofort bei Änderung der Parameter.

### Epic 5: Datenhaltung & Infrastruktur
- [ ] **US 5.1 - Dateibasierte Speicherung**
  - *AC 1:* Alle Daten werden in einer `backend/data.json` persistiert.
  - *AC 2:* Bei App-Start werden die Daten automatisch geladen.
