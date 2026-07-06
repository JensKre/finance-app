package com.example.financeapp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.financeapp.usecase.UseCase;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.browserless.SpringBrowserlessTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC001EnterTransactionTest extends SpringBrowserlessTest {

    @Autowired
    private DataService dataService;

    @BeforeEach
    void setUp() {
        // Ensure a clean state or setup if necessary.
        // H2 in-memory DB is used, preloaded with Flyway seed data (Jens, Annika, etc.).
    }

    @AfterEach
    void tearDown() {
        // Remove any transactions created during tests
        List<DataService.TransactionDto> jensTxs = dataService.getTransactions("Jens");
        for (DataService.TransactionDto tx : jensTxs) {
            dataService.deleteTransaction(tx.id());
        }
        List<DataService.TransactionDto> annikaTxs = dataService.getTransactions("Annika");
        for (DataService.TransactionDto tx : annikaTxs) {
            dataService.deleteTransaction(tx.id());
        }
    }

    @Test
    @UseCase(id = "UC-001")
    void main_success_scenario_enter_transaction() {
        MainView mainView = navigate(MainView.class);

        // 1. Select Jens tab
        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        // Get Jens's layout container to scope queries
        Component jensLayout = $(H2.class).withText("Jens's Finanzverwaltung").single().getParent().orElseThrow();

        // 2. Locate components
        BigDecimalField amountField = $(BigDecimalField.class, jensLayout).single();
        ComboBox<String> instCombo = $(ComboBox.class, jensLayout).withCaption("Institut").single();
        ComboBox<String> catCombo = $(ComboBox.class, jensLayout).withCaption("Kategorie").single();
        DatePicker datePicker = $(DatePicker.class, jensLayout).withCaption("Datum").single();
        Button submitBtn = $(Button.class, jensLayout).withText("Hinzufügen").single();
        Grid<DataService.TransactionDto> grid = $(Grid.class, jensLayout).single();

        // 3. Fill in details
        test(amountField).setValue(new BigDecimal("1250.50"));
        test(instCombo).selectItem("Trade Republic");
        test(catCombo).selectItem("ETF");
        test(datePicker).setValue(LocalDate.of(2026, 7, 6));

        // 4. Submit
        test(submitBtn).click();

        // 5. Verify success notification
        assertThat($(Notification.class).exists()).isTrue();
        Notification notification = $(Notification.class).single();
        assertThat(test(notification).getText()).isEqualTo("Eintrag erfolgreich hinzugefügt!");

        // 6. Verify entry was added to the grid
        assertThat(test(grid).size()).isEqualTo(1);
        DataService.TransactionDto transaction = test(grid).getRow(0);
        assertThat(transaction.amount()).isEqualByComparingTo("1250.50");
        assertThat(transaction.institute()).isEqualTo("Trade Republic");
        assertThat(transaction.category()).isEqualTo("ETF");
        assertThat(transaction.date()).isEqualTo(LocalDate.of(2026, 7, 6));
    }

    @Test
    @UseCase(id = "UC-001", scenario = "A1: Invalid Entry Data")
    void registration_fails_when_fields_are_empty() {
        MainView mainView = navigate(MainView.class);

        // Select Annika tab
        Tabs tabs = $(Tabs.class).single();
        Tab annikaTab = $(Tab.class).withText("Annika").single();
        tabs.setSelectedTab(annikaTab);

        Component annikaLayout = $(H2.class).withText("Annika's Finanzverwaltung").single().getParent().orElseThrow();
        Button submitBtn = $(Button.class, annikaLayout).withText("Hinzufügen").single();

        // Submit immediately with empty fields
        test(submitBtn).click();

        // Verify error notification
        assertThat($(Notification.class).exists()).isTrue();
        Notification notification = $(Notification.class).single();
        assertThat(test(notification).getText()).isEqualTo("Bitte fülle alle Felder aus!");
    }

    @Test
    @UseCase(id = "UC-001", scenario = "A2: Delete Transaction")
    void delete_transaction_confirms_and_deletes() {
        // Pre-populate a transaction
        dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("500.00"), LocalDate.now());

        MainView mainView = navigate(MainView.class);

        // Select Jens tab
        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Component jensLayout = $(H2.class).withText("Jens's Finanzverwaltung").single().getParent().orElseThrow();
        Grid<DataService.TransactionDto> grid = $(Grid.class, jensLayout).single();

        // Check size and materialize the first row
        int initialSize = test(grid).size();
        assertThat(initialSize).isGreaterThanOrEqualTo(1);

        // Click delete button in row 0, col 4
        Button deleteBtn = (Button) test(grid).getCellComponent(0, 4);
        test(deleteBtn).click();

        // Verify confirm dialog is open
        assertThat($(Dialog.class).exists()).isTrue();
        Dialog dialog = $(Dialog.class).single();

        // Confirm deletion
        test($(Button.class, dialog).withText("Ja, löschen").single()).click();

        // Verify dialog is closed and grid size has decreased by 1
        assertThat($(Dialog.class).exists()).isFalse();
        assertThat(test(grid).size()).isEqualTo(initialSize - 1);
    }

    @Test
    @UseCase(id = "UC-001", scenario = "A3: Cancel Deletion")
    void cancel_deletion_does_not_delete() {
        // Pre-populate a transaction
        dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("500.00"), LocalDate.now());

        MainView mainView = navigate(MainView.class);

        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Component jensLayout = $(H2.class).withText("Jens's Finanzverwaltung").single().getParent().orElseThrow();
        Grid<DataService.TransactionDto> grid = $(Grid.class, jensLayout).single();

        int initialSize = test(grid).size();
        assertThat(initialSize).isGreaterThanOrEqualTo(1);

        // Click delete button in row 0, col 4
        Button deleteBtn = (Button) test(grid).getCellComponent(0, 4);
        test(deleteBtn).click();

        Dialog dialog = $(Dialog.class).single();

        // Cancel deletion
        test($(Button.class, dialog).withText("Abbrechen").single()).click();

        // Verify dialog is closed and grid still has the row
        assertThat($(Dialog.class).exists()).isFalse();
        assertThat(test(grid).size()).isEqualTo(initialSize);
    }

    @Test
    @UseCase(id = "UC-001", scenario = "Main Success Scenario", businessRules = {"BR-002"})
    void currency_formatting_follows_german_locale() {
        dataService.addTransaction("Jens", "Binance", "Krypto", new BigDecimal("1234567.89"), LocalDate.now());

        MainView mainView = navigate(MainView.class);

        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Component jensLayout = $(H2.class).withText("Jens's Finanzverwaltung").single().getParent().orElseThrow();
        Grid<DataService.TransactionDto> grid = $(Grid.class, jensLayout).single();

        // Col index 3 is Betrag/Amount
        String amountText = test(grid).getCellText(0, 3);
        assertThat(amountText).isEqualTo("1.234.567,89 €");
    }
}
