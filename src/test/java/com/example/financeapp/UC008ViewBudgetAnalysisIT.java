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
import org.vaadin.addons.dramafinder.element.TabElement;

import com.example.financeapp.usecase.UseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UC008ViewBudgetAnalysisIT extends AbstractBasePlaywrightIT {

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
        @UseCase(id = "UC-008", businessRules = {"BR-014", "BR-015", "BR-016", "BR-017"})
        @DisplayName("Budget Auswertung tab displays category monthly sum cards and opens drill-down dialog on bar click in real browser")
        void verify_budget_analysis_and_drilldown_in_browser() {
            // Seed budget data
            List<String[]> rows = new java.util.ArrayList<>();
            rows.add(new String[]{"15.03.2026", "Ausgabe", "24,90", "Abos", "Jens", "Disney Plus Abo"});
            rows.add(new String[]{"01.03.2026", "Ausgabe", "7,20", "Abos", "Annika", "Spotify"});
            rows.add(new String[]{"10.04.2026", "Ausgabe", "100,00", "Lebensmittel", "Jens", "Supermarkt Einkauf"});
            dataService.importBudgetCsvRows("e2e-budget.csv", rows);

            // Open application in real Playwright browser
            page.navigate(getUrl());
            waitForVaadin();

            // Switch to Budget Auswertung tab
            TabElement budgetTab = TabElement.getTabByText(page.locator("body"), "Budget Auswertung");
            budgetTab.click();
            waitForVaadin();

            // Verify header & category card headings are visible (BR-015)
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(page.locator("h2:has-text(\"Budget Auswertung\")")).isVisible();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(page.locator("h3:has-text(\"Abos\")")).isVisible();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(page.locator("h3:has-text(\"Lebensmittel\")")).isVisible();

            // Click on SVG bar element to trigger drill-down dialog (BR-017)
            com.microsoft.playwright.Locator rectBar = page.locator("rect[data-cat=\"Abos\"]").first();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(rectBar).isVisible();
            rectBar.click();
            waitForVaadin();

            // Verify Vaadin Dialog opens with un-truncated details
            com.microsoft.playwright.Locator dialogHeader = page.locator("h3:has-text(\"Einzelbuchungen: Abos\")");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(dialogHeader).isVisible();

            com.microsoft.playwright.Locator descCell = page.locator("vaadin-grid-cell-content:has-text(\"Disney Plus Abo\")");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(descCell).isVisible();
        }
    }
}
