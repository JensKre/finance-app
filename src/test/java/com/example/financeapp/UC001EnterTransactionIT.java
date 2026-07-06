package com.example.financeapp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.vaadin.addons.dramafinder.AbstractBasePlaywrightIT;
import org.vaadin.addons.dramafinder.element.BigDecimalFieldElement;
import org.vaadin.addons.dramafinder.element.ButtonElement;
import org.vaadin.addons.dramafinder.element.ComboBoxElement;
import org.vaadin.addons.dramafinder.element.DatePickerElement;
import org.vaadin.addons.dramafinder.element.DialogElement;
import org.vaadin.addons.dramafinder.element.GridElement;
import org.vaadin.addons.dramafinder.element.NotificationElement;
import org.vaadin.addons.dramafinder.element.TextFieldElement;
import org.vaadin.addons.dramafinder.element.TabElement;

import com.example.financeapp.usecase.UseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UC001EnterTransactionIT extends AbstractBasePlaywrightIT {

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
        for (DataService.TransactionDto tx : dataService.getTransactions("Jens")) {
            dataService.deleteTransaction(tx.id());
        }
        for (DataService.TransactionDto tx : dataService.getTransactions("Annika")) {
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
        @UseCase(id = "UC-001", scenario = "Main Success Scenario")
        @DisplayName("Enter valid transaction updates grid and database")
        void main_success_scenario_enter_transaction() {
            waitForVaadin();
            TabElement jensTab = TabElement.getTabByText(page.locator("body"), "Jens");
            jensTab.click();
            waitForVaadin();

            BigDecimalFieldElement amountField = BigDecimalFieldElement.getByLabel(page, "Betrag (€)");
            amountField.setValue("123,45");

            ComboBoxElement instituteCombo = ComboBoxElement.getByLabel(page, "Institut");
            instituteCombo.selectItem("Sparkasse");

            ComboBoxElement categoryCombo = ComboBoxElement.getByLabel(page, "Kategorie");
            categoryCombo.selectItem("Girokonto");

            DatePickerElement datePicker = DatePickerElement.getByLabel(page, "Datum");
            datePicker.setValue(LocalDate.now());

            ButtonElement submitBtn = ButtonElement.getByText(page, "Hinzufügen");
            submitBtn.click();

            NotificationElement notif = new NotificationElement(page);
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Eintrag erfolgreich hinzugefügt!");

            GridElement grid = GridElement.get(page);
            Optional<GridElement.CellElement> amountCell = grid.findCell(0, "Betrag");
            assertThat(amountCell.isPresent()).isTrue();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(amountCell.get().getCellContentLocator()).hasText("123,45 €");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-001", scenario = "A1: Invalid Entry Data")
        @DisplayName("Validation fails when required fields are empty")
        void registration_fails_when_fields_are_empty() {
            waitForVaadin();
            TabElement jensTab = TabElement.getTabByText(page.locator("body"), "Jens");
            jensTab.click();
            waitForVaadin();

            ButtonElement submitBtn = ButtonElement.getByText(page, "Hinzufügen");
            submitBtn.click();

            NotificationElement notif = new NotificationElement(page);
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Bitte fülle alle Felder aus!");
        }

        @Test
        @UseCase(id = "UC-001", scenario = "A2: Delete Transaction")
        @DisplayName("Delete transaction opens dialog and deletes entry")
        void delete_transaction_confirms_and_deletes() {
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("500.00"), LocalDate.now());
            page.reload();
            waitForVaadin();

            TabElement jensTab = TabElement.getTabByText(page.locator("body"), "Jens");
            jensTab.click();
            waitForVaadin();

            GridElement grid = GridElement.get(page);
            int initialCount = grid.getTotalRowCount();
            assertThat(initialCount).isGreaterThanOrEqualTo(1);

            Optional<GridElement.CellElement> actionCell = grid.findCell(0, "Aktion");
            assertThat(actionCell.isPresent()).isTrue();
            
            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();

            DialogElement dialog = DialogElement.getByHeaderText(page, "Eintrag löschen?");
            dialog.assertOpen();

            ButtonElement confirmBtn = ButtonElement.getByText(dialog.getLocator(), "Ja, löschen");
            confirmBtn.click();
            waitForVaadin();

            dialog.assertClosed();

            assertThat(grid.getTotalRowCount()).isEqualTo(initialCount - 1);
        }

        @Test
        @UseCase(id = "UC-001", scenario = "A3: Cancel Deletion")
        @DisplayName("Cancel deletion closes dialog and preserves entry")
        void cancel_deletion_does_not_delete() {
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("500.00"), LocalDate.now());
            page.reload();
            waitForVaadin();

            TabElement jensTab = TabElement.getTabByText(page.locator("body"), "Jens");
            jensTab.click();
            waitForVaadin();

            GridElement grid = GridElement.get(page);
            int initialCount = grid.getTotalRowCount();
            assertThat(initialCount).isGreaterThanOrEqualTo(1);

            Optional<GridElement.CellElement> actionCell = grid.findCell(0, "Aktion");
            assertThat(actionCell.isPresent()).isTrue();
            
            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();

            DialogElement dialog = DialogElement.getByHeaderText(page, "Eintrag löschen?");
            dialog.assertOpen();

            ButtonElement cancelBtn = ButtonElement.getByText(dialog.getLocator(), "Abbrechen");
            cancelBtn.click();

            dialog.assertClosed();

            assertThat(grid.getTotalRowCount()).isEqualTo(initialCount);
        }
    }

    @Nested
    @DisplayName("Business Rules")
    class BusinessRules {

        @Test
        @UseCase(id = "UC-001", scenario = "Main Success Scenario", businessRules = {"BR-002"})
        @DisplayName("Monetary format follows German locale")
        void currency_formatting_follows_german_locale() {
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1234567.89"), LocalDate.now());
            page.reload();
            waitForVaadin();

            TabElement jensTab = TabElement.getTabByText(page.locator("body"), "Jens");
            jensTab.click();
            waitForVaadin();

            GridElement grid = GridElement.get(page);
            Optional<GridElement.CellElement> amountCell = grid.findCell(0, "Betrag");
            assertThat(amountCell.isPresent()).isTrue();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(amountCell.get().getCellContentLocator()).hasText("1.234.567,89 €");
        }
    }
}
