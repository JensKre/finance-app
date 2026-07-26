package com.example.financeapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DataService service;

    @Override
    public void run(String... args) throws Exception {
        if (!service.getTransactions(null).isEmpty()) {
            return;
        }
        importCsvFile();
    }

    private void importCsvFile() {
        File csvFile = new File("docs/Finanzen_Annika_Jens.csv");
        if (!csvFile.exists()) {
            System.out.println("ℹ️ CSV-Datei unter docs/Finanzen_Annika_Jens.csv nicht gefunden.");
            return;
        }

        System.out.println("🔄 Starte Datenimport aus docs/Finanzen_Annika_Jens.csv...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();
            if (lines.isEmpty()) {
                return;
            }

            // Line 0: Header with dates (skip col 0)
            String[] header = lines.get(0).split(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            List<LocalDate> dates = new ArrayList<>();
            for (int i = 1; i < header.length; i++) {
                String dStr = header[i].trim();
                if (!dStr.isEmpty()) {
                    dates.add(LocalDate.parse(dStr, formatter));
                }
            }

            // Delete any transactions not belonging to the CSV dates
            service.deleteAllTransactionsExceptDates(dates);

            int importedCount = 0;

            for (int r = 1; r < lines.size(); r++) {
                String line = lines.get(r).trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";", -1);
                String rawItemName = parts[0].trim();

                if (rawItemName.isEmpty() || rawItemName.equalsIgnoreCase("Summe")) {
                    continue;
                }

                // Map row item name to institute & category
                String instituteName;
                String categoryName;

                switch (rawItemName) {
                    case "Bargeld":
                        instituteName = "Bargeld";
                        categoryName = "Bargeld";
                        break;
                    case "Tagesgeld+Sparkonto+Girokonto":
                        instituteName = "Tagesgeld+Sparkonto+Girokonto";
                        categoryName = "Girokonto";
                        break;
                    case "Bausparvertrag":
                        instituteName = "Bausparvertrag";
                        categoryName = "Bausparvertrag";
                        break;
                    case "Aktien":
                        instituteName = "Aktien";
                        categoryName = "ETF";
                        break;
                    case "Krypto":
                        instituteName = "Krypto";
                        categoryName = "Krypto";
                        break;
                    case "Gold":
                        instituteName = "Gold";
                        categoryName = "Gold";
                        break;
                    case "Silber":
                        instituteName = "Silber";
                        categoryName = "Silber";
                        break;
                    case "Genussrechte Stihl":
                        instituteName = "Stihl";
                        categoryName = "Genussrechte";
                        break;
                    case "Steuervorauszahlung":
                        instituteName = "Steuervorauszahlung";
                        categoryName = "Steuern";
                        break;
                    case "Steuernachzahlung voraussichtlich":
                        instituteName = "Steuernachzahlung voraussichtlich";
                        categoryName = "Steuern";
                        break;
                    default:
                        instituteName = rawItemName;
                        categoryName = rawItemName;
                        break;
                }

                // Ensure institute & category exist in DB
                if (!service.instituteExists(instituteName)) {
                    service.addInstitute(instituteName);
                }
                if (!service.categoryExists(categoryName)) {
                    service.addCategory(categoryName);
                }

                // User: "Jens"
                String username = "Jens";

                // Parse columns for each date
                for (int c = 1; c < parts.length && (c - 1) < dates.size(); c++) {
                    String valStr = parts[c].trim();
                    if (valStr.isEmpty()) {
                        continue;
                    }

                    // Clean amount string
                    String clean = valStr.replace("€", "")
                            .replace("\u00A0", "")
                            .replace(" ", "")
                            .replace(".", "")
                            .replace(",", ".");

                    if (clean.isEmpty()) {
                        continue;
                    }

                    BigDecimal amount = new BigDecimal(clean);
                    LocalDate date = dates.get(c - 1);

                    // Check if entry already exists for this user, date, institute, and category
                    boolean exists = service.getTransactions(username).stream()
                            .anyMatch(t -> t.date().equals(date)
                                    && t.institute().equalsIgnoreCase(instituteName)
                                    && t.category().equalsIgnoreCase(categoryName));

                    if (!exists) {
                        service.addTransaction(username, instituteName, categoryName, amount, date);
                        importedCount++;
                    }
                }
            }

            System.out.println("✅ Datenimport aus docs/Finanzen_Annika_Jens.csv abgeschlossen. Nur noch CSV-Daten in der Datenbank!");
        } catch (Exception e) {
            System.err.println("❌ Fehler beim Importieren von docs/Finanzen_Annika_Jens.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


