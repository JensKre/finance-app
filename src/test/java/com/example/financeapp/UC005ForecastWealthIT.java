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
import org.vaadin.addons.dramafinder.element.BigDecimalFieldElement;
import org.vaadin.addons.dramafinder.element.NumberFieldElement;
import org.vaadin.addons.dramafinder.element.IntegerFieldElement;
import org.vaadin.addons.dramafinder.element.ButtonElement;
import org.vaadin.addons.dramafinder.element.NotificationElement;
import org.vaadin.addons.dramafinder.element.TabElement;

import com.example.financeapp.usecase.UseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UC005ForecastWealthIT extends AbstractBasePlaywrightIT {

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
        @UseCase(id = "UC-005")
        @DisplayName("Forecast compound wealth correctly using custom inputs")
        void forecast_wealth_calculates_correctly() {
            // Seed starting wealth of 1000.00
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1000.00"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement forecastTab = TabElement.getTabByText(page.locator("body"), "Prognose");
            forecastTab.click();
            waitForVaadin();

            BigDecimalFieldElement savingsField = BigDecimalFieldElement.getByLabel(page, "Monatliche Sparrate (€)");
            NumberFieldElement returnField = NumberFieldElement.getByLabel(page, "Erwartete jährliche Rendite (%)");
            IntegerFieldElement periodField = IntegerFieldElement.getByLabel(page, "Zeitraum (Jahre)");
            ButtonElement calcBtn = ButtonElement.getByText(page.locator("body"), "Berechnen");

            // Fill parameters: savingsField is text-based (uses comma), returnField is number-based (uses dot)
            savingsField.setValue("200,00");
            returnField.setValue("6.0");
            periodField.setValue("5");

            calcBtn.click();
            waitForVaadin();

            // Verify result
            com.microsoft.playwright.Locator resultContainer = page.getByText("Geschätztes Endvermögen:");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(resultContainer).containsText("15372.63 €");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-005", scenario = "A1: Invalid Parameters")
        @DisplayName("Validation fails and displays errors when inputs are invalid")
        void forecast_fails_when_parameters_invalid() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement forecastTab = TabElement.getTabByText(page.locator("body"), "Prognose");
            forecastTab.click();
            waitForVaadin();

            BigDecimalFieldElement savingsField = BigDecimalFieldElement.getByLabel(page, "Monatliche Sparrate (€)");
            NumberFieldElement returnField = NumberFieldElement.getByLabel(page, "Erwartete jährliche Rendite (%)");
            IntegerFieldElement periodField = IntegerFieldElement.getByLabel(page, "Zeitraum (Jahre)");
            ButtonElement calcBtn = ButtonElement.getByText(page.locator("body"), "Berechnen");

            // Enter negative and zero values (returnField uses dot, savingsField uses comma)
            savingsField.setValue("-100,00");
            returnField.setValue("-2.5");
            periodField.setValue("0");

            calcBtn.click();
            waitForVaadin();

            // Verify error notification
            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Bitte korrigieren Sie die ungültigen Parameter.");

            // Verify fields are marked invalid
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(savingsField.getLocator()).hasAttribute("invalid", "");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(returnField.getLocator()).hasAttribute("invalid", "");
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(periodField.getLocator()).hasAttribute("invalid", "");
        }
    }
}
