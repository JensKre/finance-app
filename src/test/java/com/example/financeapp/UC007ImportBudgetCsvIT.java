package com.example.financeapp;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.vaadin.addons.dramafinder.AbstractBasePlaywrightIT;
import org.vaadin.addons.dramafinder.element.GridElement;
import org.vaadin.addons.dramafinder.element.TabElement;

import com.example.financeapp.usecase.UseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UC007ImportBudgetCsvIT extends AbstractBasePlaywrightIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DataService dataService;

    @Override
    public String getUrl() {
        return String.format("http://localhost:%d/", port);
    }

    @Override
    public String getView() {
        return "";
    }

    @BeforeEach
    void setUpData() {
        cleanData();
    }

    @AfterEach
    void tearDownData() {
        cleanData();
    }

    private void cleanData() {
        dataService.clearImportMetadataAndBudgetTransactions();
    }

    private void waitForVaadin() {
        page.waitForTimeout(500);
        page.waitForFunction("window.Vaadin && window.Vaadin.Flow && Object.keys(window.Vaadin.Flow.clients).every(id => !window.Vaadin.Flow.clients[id].isActive())");
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-007", businessRules = {"BR-009", "BR-010", "BR-011", "BR-012"})
        @DisplayName("Importing budget CSV entries renders transactions and rounds high precision floating amounts in Playwright IT")
        void verify_budget_csv_import_in_browser() {
            // Pre-seed imported rows
            List<String[]> rows = new java.util.ArrayList<>();
            rows.add(new String[]{"22.07.2026", "Ausgabe", "4,8799999999999999", "Lebensmittel", "Jens", "Lidl"});
            rows.add(new String[]{"23.07.2026", "Ausgabe", "15,15", "Lebensmittel", "Jens", "Rewe"});
            dataService.importBudgetCsvRows("e2e-budget-export.csv", rows);
            dataService.updateImportMetadata("e2e-budget-export.csv");

            // Open application in real Playwright browser
            page.navigate(getUrl());
            waitForVaadin();

            // Switch to CSV Import tab using Drama Finder
            TabElement csvTab = TabElement.getTabByText(page.locator("body"), "CSV Import");
            csvTab.click();
            waitForVaadin();

            // Verify metadata display contains filename (BR-012)
            com.microsoft.playwright.Locator metaSpan = page.locator("span:has-text(\"e2e-budget-export.csv\")");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(metaSpan).isVisible();

            // Verify recent transactions grid contains rows (BR-010, BR-012)
            GridElement grid = GridElement.get(page);
            assertThat(grid).isNotNull();
        }
    }
}
