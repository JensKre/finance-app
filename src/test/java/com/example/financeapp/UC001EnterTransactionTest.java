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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    }

    @AfterEach
    void tearDown() {
        // Clean up entries created during tests
        List<LocalDate> dates = List.of(LocalDate.now(), LocalDate.of(2026, 7, 6));
        for (LocalDate date : dates) {
            dataService.deleteEntriesForDate("Jens", date);
            dataService.deleteEntriesForDate("Annika", date);
        }
    }

    private <T extends Component> T getVisible(com.vaadin.browserless.ComponentQuery<T> query) {
        return query.all().stream()
                .filter(Component::isVisible)
                .findFirst()
                .orElseThrow(() -> new java.util.NoSuchElementException("No visible component found"));
    }

    @Test
    @UseCase(id = "UC-001")
    void main_success_scenario_enter_transaction() {
        MainView mainView = navigate(MainView.class);

        // 1. Select Jens tab
        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        // Get visible DatePicker
        DatePicker dp = getVisible($(DatePicker.class).withCaption("Datum"));
        test(dp).setValue(LocalDate.of(2026, 7, 6));

        // Locate the field for Trade Republic
        BigDecimalField amountField = getVisible($(BigDecimalField.class).withAttribute("data-inst", "Trade Republic"));
        Component row = amountField.getParent().orElseThrow();
        ComboBox<String> categoryCombo = $(ComboBox.class, row).single();

        test(amountField).setValue(new BigDecimal("1250.50"));
        test(categoryCombo).selectItem("ETF");

        // Locate the visible save button
        Button saveBtn = getVisible($(Button.class).withText("Speichern"));
        test(saveBtn).click();

        // Verify success notification
        assertThat($(Notification.class).exists()).isTrue();
        Notification notification = $(Notification.class).single();
        assertThat(test(notification).getText()).isEqualTo("Einträge erfolgreich gespeichert!");

        // Verify the visible date summary grid
        Grid<DataService.DateSummaryDto> grid = getVisible($(Grid.class).withCondition(g -> g.getDataProvider() != null));
        assertThat(test(grid).size()).isGreaterThanOrEqualTo(1);
        DataService.DateSummaryDto summary = test(grid).getRow(0);
        assertThat(summary.date()).isEqualTo(LocalDate.of(2026, 7, 6));
        assertThat(summary.totalAmount()).isEqualByComparingTo("1250.50");
    }

    @Test
    @UseCase(id = "UC-001", scenario = "A1: Invalid Entry Data")
    void registration_fails_when_fields_are_empty() {
        MainView mainView = navigate(MainView.class);

        // Select Annika tab
        Tabs tabs = $(Tabs.class).single();
        Tab annikaTab = $(Tab.class).withText("Annika").single();
        tabs.setSelectedTab(annikaTab);

        // Enter amount without category for Sparkasse
        BigDecimalField amountField = getVisible($(BigDecimalField.class).withAttribute("data-inst", "Sparkasse"));
        Component row = amountField.getParent().orElseThrow();
        ComboBox<String> categoryCombo = $(ComboBox.class, row).single();

        test(amountField).setValue(new BigDecimal("500.00"));
        categoryCombo.setValue(null); // Clear category using Java API directly

        Button saveBtn = getVisible($(Button.class).withText("Speichern"));
        test(saveBtn).click();

        // Verify error notification
        assertThat($(Notification.class).exists()).isTrue();
        Notification notification = $(Notification.class).single();
        assertThat(test(notification).getText()).isEqualTo("Bitte Kategorie für Sparkasse auswählen!");
    }

    @Test
    @UseCase(id = "UC-001", scenario = "A2: Modify/Delete Existing Date Entries")
    void delete_transaction_confirms_and_deletes() {
        // Pre-populate an entry
        java.util.Map<String, DataService.DateEntryDto> entries = new java.util.HashMap<>();
        entries.put("Sparkasse", new DataService.DateEntryDto("Sparkasse", "Girokonto", new BigDecimal("500.00")));
        dataService.saveEntriesForDate("Jens", LocalDate.now(), entries.values());

        MainView mainView = navigate(MainView.class);

        // Select Jens tab
        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Grid<DataService.DateSummaryDto> grid = getVisible($(Grid.class).withCondition(g -> g.getDataProvider() != null));
        int initialSize = test(grid).size();
        assertThat(initialSize).isGreaterThanOrEqualTo(1);

        // Click delete button in the first row, last column
        Button deleteBtn = (Button) test(grid).getCellComponent(0, 2);
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
        // Pre-populate an entry
        java.util.Map<String, DataService.DateEntryDto> entries = new java.util.HashMap<>();
        entries.put("Sparkasse", new DataService.DateEntryDto("Sparkasse", "Girokonto", new BigDecimal("500.00")));
        dataService.saveEntriesForDate("Jens", LocalDate.now(), entries.values());

        MainView mainView = navigate(MainView.class);

        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Grid<DataService.DateSummaryDto> grid = getVisible($(Grid.class).withCondition(g -> g.getDataProvider() != null));
        int initialSize = test(grid).size();
        assertThat(initialSize).isGreaterThanOrEqualTo(1);

        Button deleteBtn = (Button) test(grid).getCellComponent(0, 2);
        test(deleteBtn).click();

        Dialog dialog = $(Dialog.class).single();

        // Cancel deletion
        test($(Button.class, dialog).withText("Abbrechen").single()).click();

        // Verify dialog is closed and grid still has the row
        assertThat($(Dialog.class).exists()).isFalse();
        assertThat(test(grid).size()).isEqualTo(initialSize);
    }

    @Test
    @UseCase(id = "UC-001", scenario = "Main Success Scenario", businessRules = {"BR-007"})
    void currency_formatting_follows_german_locale() {
        // Pre-populate a large entry
        java.util.Map<String, DataService.DateEntryDto> entries = new java.util.HashMap<>();
        entries.put("Binance", new DataService.DateEntryDto("Binance", "Krypto", new BigDecimal("1234567.89")));
        dataService.saveEntriesForDate("Jens", LocalDate.now(), entries.values());

        MainView mainView = navigate(MainView.class);

        Tabs tabs = $(Tabs.class).single();
        Tab jensTab = $(Tab.class).withText("Jens").single();
        tabs.setSelectedTab(jensTab);

        Grid<DataService.DateSummaryDto> grid = getVisible($(Grid.class).withCondition(g -> g.getDataProvider() != null));

        // Col index 1 is "Gesamt"
        String amountText = test(grid).getCellText(0, 1);
        assertThat(amountText).isEqualTo("1.234.567,89 €");
    }
}
