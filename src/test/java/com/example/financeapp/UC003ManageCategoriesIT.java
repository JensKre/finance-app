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
import org.vaadin.addons.dramafinder.element.ButtonElement;
import org.vaadin.addons.dramafinder.element.GridElement;
import org.vaadin.addons.dramafinder.element.NotificationElement;
import org.vaadin.addons.dramafinder.element.TextFieldElement;
import org.vaadin.addons.dramafinder.element.TabElement;

import com.example.financeapp.usecase.UseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UC003ManageCategoriesIT extends AbstractBasePlaywrightIT {

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
        for (DataService.CategoryDto cat : dataService.getCategories()) {
            if (!cat.name().equals("Girokonto") && 
                !cat.name().equals("Tagesgeldkonto") && 
                !cat.name().equals("ETF") && 
                !cat.name().equals("Gold") && 
                !cat.name().equals("Krypto")) {
                dataService.deleteCategory(cat.id());
            }
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
        @UseCase(id = "UC-003")
        @DisplayName("Add a new category successfully")
        void main_success_scenario_manage_categories() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator catLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Kategorien verwalten\"))");
            TextFieldElement newCatField = new TextFieldElement(catLayout.locator("vaadin-text-field"));
            ButtonElement addCatBtn = ButtonElement.getByText(catLayout, "Hinzufügen");
            GridElement catGrid = GridElement.get(catLayout);

            int initialCount = catGrid.getTotalRowCount();

            newCatField.setValue("Aktien");
            addCatBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Kategorie hinzugefügt.");

            assertThat(catGrid.getTotalRowCount()).isEqualTo(initialCount + 1);

            int rowIndex = -1;
            for (int i = 0; i < catGrid.getTotalRowCount(); i++) {
                Optional<GridElement.CellElement> nameCell = catGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("Aktien")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> cell = catGrid.findCell(rowIndex, "Name");
            assertThat(cell.isPresent()).isTrue();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(cell.get().getCellContentLocator()).hasText("Aktien");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-003", scenario = "A1: Duplicate Category Name", businessRules = {"BR-003"})
        @DisplayName("Adding a duplicate category name displays validation error")
        void registration_fails_when_duplicate_category() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator catLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Kategorien verwalten\"))");
            TextFieldElement newCatField = new TextFieldElement(catLayout.locator("vaadin-text-field"));
            ButtonElement addCatBtn = ButtonElement.getByText(catLayout, "Hinzufügen");
            GridElement catGrid = GridElement.get(catLayout);

            int initialCount = catGrid.getTotalRowCount();

            newCatField.setValue("Girokonto");
            addCatBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Kategorie existiert bereits.");

            assertThat(catGrid.getTotalRowCount()).isEqualTo(initialCount);
        }

        @Test
        @UseCase(id = "UC-003", scenario = "A2: Delete Category")
        @DisplayName("Delete category not in use succeeds")
        void delete_category_not_in_use() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator catLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Kategorien verwalten\"))");
            TextFieldElement newCatField = new TextFieldElement(catLayout.locator("vaadin-text-field"));
            ButtonElement addCatBtn = ButtonElement.getByText(catLayout, "Hinzufügen");
            GridElement catGrid = GridElement.get(catLayout);

            newCatField.setValue("TempCat");
            addCatBtn.click();
            waitForVaadin();

            int initialCount = catGrid.getTotalRowCount();

            int rowIndex = -1;
            for (int i = 0; i < initialCount; i++) {
                Optional<GridElement.CellElement> nameCell = catGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("TempCat")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> actionCell = catGrid.findCell(rowIndex, 1);
            assertThat(actionCell.isPresent()).isTrue();

            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Kategorie entfernt.");

            assertThat(catGrid.getTotalRowCount()).isEqualTo(initialCount - 1);
        }

        @Test
        @UseCase(id = "UC-003", scenario = "A2: Delete Category")
        @DisplayName("Delete category in use fails")
        void delete_category_in_use_fails() {
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("10.00"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator catLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Kategorien verwalten\"))");
            GridElement catGrid = GridElement.get(catLayout);

            int initialCount = catGrid.getTotalRowCount();

            // Find row index of "Girokonto" in grid
            int rowIndex = -1;
            for (int i = 0; i < initialCount; i++) {
                Optional<GridElement.CellElement> nameCell = catGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("Girokonto")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> actionCell = catGrid.findCell(rowIndex, 1);
            assertThat(actionCell.isPresent()).isTrue();

            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Kategorie wird verwendet.");

            assertThat(catGrid.getTotalRowCount()).isEqualTo(initialCount);
        }
    }
}
