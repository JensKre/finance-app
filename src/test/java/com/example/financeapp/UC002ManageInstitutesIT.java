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
class UC002ManageInstitutesIT extends AbstractBasePlaywrightIT {

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
        for (DataService.InstituteDto inst : dataService.getInstitutes()) {
            if (!inst.name().equals("Trade Republic") && 
                !inst.name().equals("Sparkasse") && 
                !inst.name().equals("Binance")) {
                dataService.deleteInstitute(inst.id());
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
        @UseCase(id = "UC-002")
        @DisplayName("Add a new institute successfully")
        void main_success_scenario_manage_institutes() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator instLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Institute verwalten\"))");
            TextFieldElement newInstField = new TextFieldElement(instLayout.locator("vaadin-text-field"));
            ButtonElement addInstBtn = ButtonElement.getByText(instLayout, "Hinzufügen");
            GridElement instGrid = GridElement.get(instLayout);

            int initialCount = instGrid.getTotalRowCount();

            newInstField.setValue("Commerzbank");
            addInstBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Institut hinzugefügt.");

            assertThat(instGrid.getTotalRowCount()).isEqualTo(initialCount + 1);

            int rowIndex = -1;
            for (int i = 0; i < instGrid.getTotalRowCount(); i++) {
                Optional<GridElement.CellElement> nameCell = instGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("Commerzbank")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> cell = instGrid.findCell(rowIndex, "Name");
            assertThat(cell.isPresent()).isTrue();
            com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(cell.get().getCellContentLocator()).hasText("Commerzbank");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-002", scenario = "A1: Duplicate Institute Name", businessRules = {"BR-002"})
        @DisplayName("Adding a duplicate institute name displays validation error")
        void registration_fails_when_duplicate_institute() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator instLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Institute verwalten\"))");
            TextFieldElement newInstField = new TextFieldElement(instLayout.locator("vaadin-text-field"));
            ButtonElement addInstBtn = ButtonElement.getByText(instLayout, "Hinzufügen");
            GridElement instGrid = GridElement.get(instLayout);

            int initialCount = instGrid.getTotalRowCount();

            newInstField.setValue("Sparkasse");
            addInstBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Institut existiert bereits.");

            assertThat(instGrid.getTotalRowCount()).isEqualTo(initialCount);
        }

        @Test
        @UseCase(id = "UC-002", scenario = "A2: Delete Institute")
        @DisplayName("Delete institute not in use succeeds")
        void delete_institute_not_in_use() {
            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator instLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Institute verwalten\"))");
            TextFieldElement newInstField = new TextFieldElement(instLayout.locator("vaadin-text-field"));
            ButtonElement addInstBtn = ButtonElement.getByText(instLayout, "Hinzufügen");
            GridElement instGrid = GridElement.get(instLayout);

            newInstField.setValue("TempInst");
            addInstBtn.click();
            waitForVaadin();

            int initialCount = instGrid.getTotalRowCount();

            int rowIndex = -1;
            for (int i = 0; i < initialCount; i++) {
                Optional<GridElement.CellElement> nameCell = instGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("TempInst")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> actionCell = instGrid.findCell(rowIndex, 1);
            assertThat(actionCell.isPresent()).isTrue();

            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Institut entfernt.");

            assertThat(instGrid.getTotalRowCount()).isEqualTo(initialCount - 1);
        }

        @Test
        @UseCase(id = "UC-002", scenario = "A2: Delete Institute")
        @DisplayName("Delete institute in use fails")
        void delete_institute_in_use_fails() {
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("10.00"), LocalDate.now());

            page.navigate(getUrl());
            waitForVaadin();
            TabElement settingsTab = TabElement.getTabByText(page.locator("body"), "Einstellungen");
            settingsTab.click();
            waitForVaadin();

            com.microsoft.playwright.Locator instLayout = page.locator("vaadin-vertical-layout:has(> h3:has-text(\"Institute verwalten\"))");
            GridElement instGrid = GridElement.get(instLayout);

            int initialCount = instGrid.getTotalRowCount();

            // Find row index of "Sparkasse" in grid
            int rowIndex = -1;
            for (int i = 0; i < initialCount; i++) {
                Optional<GridElement.CellElement> nameCell = instGrid.findCell(i, "Name");
                if (nameCell.isPresent() && nameCell.get().getCellContentLocator().innerText().trim().equals("Sparkasse")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            Optional<GridElement.CellElement> actionCell = instGrid.findCell(rowIndex, 1);
            assertThat(actionCell.isPresent()).isTrue();

            ButtonElement deleteBtn = ButtonElement.getByText(actionCell.get().getCellContentLocator(), "Löschen");
            deleteBtn.click();
            waitForVaadin();

            NotificationElement notif = new NotificationElement(page.locator("vaadin-notification-card").last());
            notif.assertOpen();
            assertThat(notif.getLocator().innerText()).contains("Institut wird verwendet.");

            assertThat(instGrid.getTotalRowCount()).isEqualTo(initialCount);
        }
    }
}
