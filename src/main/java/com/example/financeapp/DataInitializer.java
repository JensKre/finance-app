package com.example.financeapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DataService service;

    @Override
    public void run(String... args) throws Exception {
        // Only run migration if there are no transaction entries in the database yet
        if (!service.getTransactions(null).isEmpty()) {
            return;
        }

        File file = new File("backup-old-stack/backend/data.json");
        if (!file.exists()) {
            System.out.println("ℹ️ Keine alte data.json unter backup-old-stack/backend/data.json gefunden.");
            return;
        }

        System.out.println("🔄 Starte automatische Datenmigration aus der alten data.json...");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);

        // Migrate institutes
        if (root.has("institutes")) {
            for (JsonNode inst : root.get("institutes")) {
                String name = inst.asText();
                if (service.getInstitutes().stream().noneMatch(i -> i.name().equalsIgnoreCase(name))) {
                    service.addInstitute(name);
                }
            }
        }

        // Migrate categories
        if (root.has("categories")) {
            for (JsonNode cat : root.get("categories")) {
                String name = cat.asText();
                if (service.getCategories().stream().noneMatch(c -> c.name().equalsIgnoreCase(name))) {
                    service.addCategory(name);
                }
            }
        }

        // Migrate transaction records
        if (root.has("records")) {
            int count = 0;
            for (JsonNode record : root.get("records")) {
                String person = record.get("person").asText();
                String institution = record.get("institution").asText();
                String category = record.get("category").asText();
                BigDecimal amount = new BigDecimal(record.get("amount").asText());
                LocalDate date = LocalDate.parse(record.get("entry_date").asText());

                // Ensure institute and category exist in the database (dynamic fallbacks)
                if (service.getInstitutes().stream().noneMatch(i -> i.name().equalsIgnoreCase(institution))) {
                    service.addInstitute(institution);
                }
                if (service.getCategories().stream().noneMatch(c -> c.name().equalsIgnoreCase(category))) {
                    service.addCategory(category);
                }

                service.addTransaction(person, institution, category, amount, date);
                count++;
            }
            System.out.println("✅ " + count + " Transaktionseinträge erfolgreich migriert!");
        }
    }
}
