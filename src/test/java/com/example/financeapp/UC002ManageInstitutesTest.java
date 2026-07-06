package com.example.financeapp;

import java.time.LocalDate;
import java.util.List;

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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC002ManageInstitutesTest extends SpringBrowserlessTest {

    @Autowired
    private DataService dataService;

    @BeforeEach
    void setUp() {
        // Clean up any test-added institutes
        cleanTestAddedInstitutes();
    }

    @AfterEach
    void tearDown() {
        // Clean up any test-added institutes
        cleanTestAddedInstitutes();
    }

    private void cleanTestAddedInstitutes() {
        for (DataService.TransactionDto tx : dataService.getTransactions("Jens")) {
            dataService.deleteTransaction(tx.id());
        }
        for (DataService.TransactionDto tx : dataService.getTransactions("Annika")) {
            dataService.deleteTransaction(tx.id());
        }
        List<DataService.InstituteDto> insts = dataService.getInstitutes();
        for (DataService.InstituteDto inst : insts) {
            if (!inst.name().equals("Trade Republic") && 
                !inst.name().equals("Sparkasse") && 
                !inst.name().equals("Binance")) {
                dataService.deleteInstitute(inst.id());
            }
        }
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-002")
        @DisplayName("Add a new institute successfully")
        void main_success_scenario_manage_institutes() {
            navigate(MainView.class);

            // Select Einstellungen tab
            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            // Locate components in the institutes layout
            Component instLayout = $(H3.class).withText("Institute verwalten").single().getParent().orElseThrow();
            TextField newInstField = $(TextField.class, instLayout).single();
            Button addInstBtn = $(Button.class, instLayout).withText("Hinzufügen").single();
            Grid<DataService.InstituteDto> instGrid = $(Grid.class, instLayout).single();

            int initialSize = test(instGrid).size();

            // Fill and submit
            test(newInstField).setValue("Commerzbank");
            test(addInstBtn).click();

            // Verify success notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).single();
            assertThat(test(notification).getText()).isEqualTo("Institut hinzugefügt.");

            // Verify grid updates
            assertThat(test(instGrid).size()).isEqualTo(initialSize + 1);
            
            // Verify new institute exists in grid rows
            boolean found = false;
            for (int i = 0; i < test(instGrid).size(); i++) {
                if (test(instGrid).getRow(i).name().equals("Commerzbank")) {
                    found = true;
                    break;
                }
            }
            assertThat(found).isTrue();
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-002", scenario = "A1: Duplicate Institute Name", businessRules = {"BR-002"})
        @DisplayName("Adding a duplicate institute fails")
        void registration_fails_when_duplicate_institute() {
            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component instLayout = $(H3.class).withText("Institute verwalten").single().getParent().orElseThrow();
            TextField newInstField = $(TextField.class, instLayout).single();
            Button addInstBtn = $(Button.class, instLayout).withText("Hinzufügen").single();
            Grid<DataService.InstituteDto> instGrid = $(Grid.class, instLayout).single();

            int initialSize = test(instGrid).size();

            // Fill with duplicate name "Sparkasse" (which exists in seeds)
            test(newInstField).setValue("Sparkasse");
            test(addInstBtn).click();

            // Verify error notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).single();
            assertThat(test(notification).getText()).isEqualTo("Institut existiert bereits.");

            // Verify size did not change
            assertThat(test(instGrid).size()).isEqualTo(initialSize);
        }

        @Test
        @UseCase(id = "UC-002", scenario = "A2: Delete Institute")
        @DisplayName("Delete institute not in use succeeds")
        void delete_institute_not_in_use() {
            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component instLayout = $(H3.class).withText("Institute verwalten").single().getParent().orElseThrow();
            TextField newInstField = $(TextField.class, instLayout).single();
            Button addInstBtn = $(Button.class, instLayout).withText("Hinzufügen").single();
            Grid<DataService.InstituteDto> instGrid = $(Grid.class, instLayout).single();

            // 1. Add temporary institute
            test(newInstField).setValue("TempInst");
            test(addInstBtn).click();
            assertThat($(Notification.class).exists()).isTrue();

            int sizeAfterAdd = test(instGrid).size();

            // 2. Find row index of "TempInst"
            int rowIndex = -1;
            for (int i = 0; i < test(instGrid).size(); i++) {
                if (test(instGrid).getRow(i).name().equals("TempInst")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            // 3. Delete it
            Button delBtn = (Button) test(instGrid).getCellComponent(rowIndex, 1);
            test(delBtn).click();

            // 4. Verify success notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Institut entfernt.");

            // 5. Verify size decreased
            assertThat(test(instGrid).size()).isEqualTo(sizeAfterAdd - 1);
        }

        @Test
        @UseCase(id = "UC-002", scenario = "A2: Delete Institute")
        @DisplayName("Delete institute in use fails")
        void delete_institute_in_use_fails() {
            // Add a transaction referencing Sparkasse to make it in-use
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new java.math.BigDecimal("10.00"), LocalDate.now());

            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component instLayout = $(H3.class).withText("Institute verwalten").single().getParent().orElseThrow();
            Grid<DataService.InstituteDto> instGrid = $(Grid.class, instLayout).single();

            int initialSize = test(instGrid).size();

            // Find row index of "Sparkasse" (which has active transactions in seed data)
            int rowIndex = -1;
            for (int i = 0; i < test(instGrid).size(); i++) {
                if (test(instGrid).getRow(i).name().equals("Sparkasse")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            // Attempt to delete it
            Button delBtn = (Button) test(instGrid).getCellComponent(rowIndex, 1);
            test(delBtn).click();

            // Verify error notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Institut wird verwendet.");

            // Verify size did not change
            assertThat(test(instGrid).size()).isEqualTo(initialSize);
        }
    }
}
