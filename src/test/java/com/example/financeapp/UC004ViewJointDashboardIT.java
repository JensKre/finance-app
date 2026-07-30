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
        @UseCase(id = "UC-004", businessRules = {"BR-004", "BR-005", "BR-006"})
        @DisplayName("Joint dashboard displays correct wealth card, timeline chart, and category pie chart")
        void joint_dashboard_displays_correct_wealth_aggregations() {
            // Seed transactions
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1500.50"), LocalDate.now());
            dataService.addTransaction("Annika", "Trade Republic", "Tagesgeldkonto", new BigDecimal("2500.75"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement dashboardTab = TabElement.getTabByText(page.locator("body"), "Dashboard");
            dashboardTab.click();
            waitForVaadin();

            // Verify wealth card (BR-004)
            com.microsoft.playwright.Locator gesamtCard = page.locator("div:has(> h3:has-text(\"Gesamtvermögen\"))");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(gesamtCard).containsText("4.001,25 €");

            // Verify wealth timeline trend chart (BR-005)
            com.microsoft.playwright.Locator trendChart = page.locator("#wealth-trend-chart");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(trendChart).isVisible();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(trendChart).containsText("Vermögensverlauf über die Zeit");

            // Verify hovering over trend chart point displays exact value (BR-005)
            com.microsoft.playwright.Locator point = page.locator("#wealth-trend-chart circle").first();
            point.dispatchEvent("mouseover");
            page.waitForTimeout(200);
            com.microsoft.playwright.Locator trendHoverInfo = page.locator("#wealth-trend-hover-info");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(trendHoverInfo).containsText("4.001,25 €");

            // Verify category distribution pie chart (BR-006)
            com.microsoft.playwright.Locator pieChart = page.locator("#category-pie-chart-card");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(pieChart).isVisible();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(pieChart).containsText("Kategorien-Verteilung");

            // Verify wealth growth decomposition chart (BR-018)
            com.microsoft.playwright.Locator growthTitle = page.locator("h3:has-text(\"Vermögenszuwachs-Aufschlüsselung\")");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(growthTitle).isVisible();

            // No warning is present
            assertThat(page.locator("#no-data-message").count()).isEqualTo(0);
        }

        @Test
        @UseCase(id = "UC-004", businessRules = {"BR-006"})
        @DisplayName("Category pie chart slice mouse hover dynamically updates center display in browser")
        void pie_chart_slice_hover_updates_center_display() {
            dataService.addTransaction("Jens", "Platzhalter", "Tagesgeld+Sparkonto+Girokonto", new BigDecimal("10000.00"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement dashboardTab = TabElement.getTabByText(page.locator("body"), "Dashboard");
            dashboardTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator pieCard = page.locator("#category-pie-chart-card");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(pieCard).isVisible();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(pieCard).containsText("Tagesgeld+Sparkonto+Girokonto");

            com.microsoft.playwright.Locator pieSlice = page.locator("#category-pie-chart-card path").first();
            pieSlice.dispatchEvent("mouseover");
            page.waitForTimeout(200);

            com.microsoft.playwright.Locator centerTitle = page.locator("#donut-center-title");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(centerTitle).hasText("Tagesgeld+Sparkonto+Girokonto");
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

            // Wealth cards, trend chart, and pie chart must NOT be present
            assertThat(page.locator("div:has(> h3:has-text(\"Gesamtvermögen\"))").count()).isEqualTo(0);
            assertThat(page.locator("#wealth-trend-chart").count()).isEqualTo(0);
            assertThat(page.locator("#category-pie-chart-card").count()).isEqualTo(0);
        }
    }
}
