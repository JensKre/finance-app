package com.example.financeapp;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.financeapp.usecase.UseCase;
import com.vaadin.browserless.SpringBrowserlessTest;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC005ForecastWealthTest extends SpringBrowserlessTest {

    @Autowired
    private DataService dataService;

    @BeforeEach
    void setUp() {
        cleanTransactions();
    }

    @AfterEach
    void tearDown() {
        cleanTransactions();
    }

    private void cleanTransactions() {
        for (DataService.TransactionDto tx : dataService.getTransactions(null)) {
            dataService.deleteTransaction(tx.id());
        }
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-005")
        @DisplayName("Forecasting wealth with valid inputs calculates compound growth correctly")
        void forecast_wealth_calculates_correctly() {
            // Seed a starting wealth of 1000.00
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1000.00"), LocalDate.now());

            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            // Select Prognose tab
            Tabs tabs = $(Tabs.class).single();
            Tab forecastTab = $(Tab.class).withText("Prognose").single();
            tabs.setSelectedTab(forecastTab);

            // Locate components
            BigDecimalField savingsField = $(BigDecimalField.class).single();
            NumberField returnField = $(NumberField.class).single();
            IntegerField periodField = $(IntegerField.class).single();
            Button calcBtn = $(Button.class).withText("Berechnen").single();

            VerticalLayout calcForm = (VerticalLayout) savingsField.getParent().orElseThrow();
            Div resultContainer = $(Div.class, calcForm).single();

            // Assert default values
            assertThat(savingsField.getValue()).isEqualTo(new BigDecimal("500"));
            assertThat(returnField.getValue()).isEqualTo(5.0);
            assertThat(periodField.getValue()).isEqualTo(10);

            // Enter custom forecast parameters
            test(savingsField).setValue(new BigDecimal("200.00"));
            test(returnField).setValue(6.0); // 6% annual return
            test(periodField).setValue(5);  // 5 years period (60 months)

            test(calcBtn).click();

            // Math check:
            // starting = 1000.00
            // monthly interest rate = 0.06 / 12 = 0.005
            // 60 monthly steps: futureValue = (futureValue + 200) * 1.005
            // Let's check final amount text
            String resultText = resultContainer.getText();
            assertThat(resultText).startsWith("Geschätztes Endvermögen: ");
            assertThat(resultText).contains("15372.63 €"); // Calculated compound result
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-005", scenario = "A1: Invalid Parameters")
        @DisplayName("Forecasting wealth with invalid parameters fails calculation and shows validation errors")
        void forecast_fails_when_parameters_invalid() {
            navigate(MainView.class);

            // Select Prognose tab
            Tabs tabs = $(Tabs.class).single();
            Tab forecastTab = $(Tab.class).withText("Prognose").single();
            tabs.setSelectedTab(forecastTab);

            BigDecimalField savingsField = $(BigDecimalField.class).single();
            NumberField returnField = $(NumberField.class).single();
            IntegerField periodField = $(IntegerField.class).single();
            Button calcBtn = $(Button.class).withText("Berechnen").single();

            VerticalLayout calcForm = (VerticalLayout) savingsField.getParent().orElseThrow();
            Div resultContainer = $(Div.class, calcForm).single();

            // Set invalid input values (negative savings, negative return, zero period)
            test(savingsField).setValue(new BigDecimal("-100.00"));
            test(returnField).setValue(-2.5);
            test(periodField).setValue(0);

            test(calcBtn).click();

            // Assert inputs are marked as invalid
            assertThat(savingsField.isInvalid()).isTrue();
            assertThat(savingsField.getErrorMessage()).isEqualTo("Sparrate darf nicht negativ oder leer sein");

            assertThat(returnField.isInvalid()).isTrue();
            assertThat(returnField.getErrorMessage()).isEqualTo("Rendite darf nicht negativ oder leer sein");

            assertThat(periodField.isInvalid()).isTrue();
            assertThat(periodField.getErrorMessage()).isEqualTo("Zeitraum muss größer als 0 sein");

            // Verify error notification is shown
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Bitte korrigieren Sie die ungültigen Parameter.");

            // Result text should be empty
            assertThat(resultContainer.getText()).isEmpty();
        }
    }
}
