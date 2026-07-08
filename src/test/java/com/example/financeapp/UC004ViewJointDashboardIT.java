package com.example.financeapp;

import java.math.BigDecimal;
import java.time.LocalDate;

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
class UC004ViewJointDashboardIT extends AbstractBasePlaywrightIT {

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

    @org.junit.jupiter.api.BeforeEach
    void cleanDatabase() {
        for (DataService.TransactionDto tx : dataService.getTransactions(null)) {
            dataService.deleteTransaction(tx.id());
        }
    }

    private void waitForVaadin() {
        page.waitForTimeout(500);
        page.waitForFunction("window.Vaadin && window.Vaadin.Flow && Object.keys(window.Vaadin.Flow.clients).every(id => !window.Vaadin.Flow.clients[id].isActive())");
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-004")
        @DisplayName("Joint dashboard displays correct wealth cards and recent activities")
        void joint_dashboard_displays_correct_wealth_aggregations() {
            // Seed transactions
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1500.50"), LocalDate.now());
            dataService.addTransaction("Annika", "Trade Republic", "Tagesgeldkonto", new BigDecimal("2500.75"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement dashboardTab = TabElement.getTabByText(page.locator("body"), "Dashboard");
            dashboardTab.click();
            waitForVaadin();

            // Verify wealth cards
            com.microsoft.playwright.Locator gesamtCard = page.locator("div:has(> h3:has-text(\"Gesamtvermögen\"))");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(gesamtCard).containsText("4.001,25 €");

            com.microsoft.playwright.Locator jensCard = page.locator("div:has(> h3:has-text(\"Jens\"))");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(jensCard).containsText("1.500,50 €");

            com.microsoft.playwright.Locator annikaCard = page.locator("div:has(> h3:has-text(\"Annika\"))");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(annikaCard).containsText("2.500,75 €");

            // Verify combined transactions grid
            GridElement combinedGrid = GridElement.get(page.locator("body"));
            assertThat(combinedGrid.getTotalRowCount()).isEqualTo(2);

            // No warning is present
            assertThat(page.locator("#no-data-message").count()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-004", scenario = "A1: No Data Found")
        @DisplayName("Displays suggestion message when no transactions exist")
        void warning_displayed_when_no_data() {
            // Database is clean (no transactions)

            page.navigate(getUrl());
            waitForVaadin();
            TabElement dashboardTab = TabElement.getTabByText(page.locator("body"), "Dashboard");
            dashboardTab.click();
            waitForVaadin();

            // Verify message is shown
            com.microsoft.playwright.Locator noDataMessage = page.locator("#no-data-message");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(noDataMessage)
                    .hasText("Bitte tragen Sie Ihre erste Transaktion ein, um Daten auf dem Dashboard anzuzeigen.");

            // Wealth cards and grids must NOT be present
            assertThat(page.locator("div:has(> h3:has-text(\"Gesamtvermögen\"))").count()).isEqualTo(0);
            assertThat(page.locator("div:has(> h3:has-text(\"Jens\"))").count()).isEqualTo(0);
            assertThat(page.locator("div:has(> h3:has-text(\"Annika\"))").count()).isEqualTo(0);
            assertThat(page.locator("vaadin-grid").count()).isEqualTo(0);
        }
    }
}
