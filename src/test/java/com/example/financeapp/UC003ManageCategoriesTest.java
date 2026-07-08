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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC003ManageCategoriesTest extends SpringBrowserlessTest {

    @Autowired
    private DataService dataService;

    @BeforeEach
    void setUp() {
        cleanTestAddedCategories();
    }

    @AfterEach
    void tearDown() {
        cleanTestAddedCategories();
    }

    private void cleanTestAddedCategories() {
        // Unconditionally delete all transactions to avoid foreign key constraint errors
        for (DataService.TransactionDto tx : dataService.getTransactions(null)) {
            dataService.deleteTransaction(tx.id());
        }

        List<DataService.CategoryDto> cats = dataService.getCategories();
        for (DataService.CategoryDto cat : cats) {
            if (!cat.name().equals("Girokonto") && 
                !cat.name().equals("Tagesgeldkonto") && 
                !cat.name().equals("ETF") && 
                !cat.name().equals("Gold") && 
                !cat.name().equals("Krypto")) {
                dataService.deleteCategory(cat.id());
            }
        }
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-003")
        @DisplayName("Add a new category successfully")
        void main_success_scenario_manage_categories() {
            navigate(MainView.class);

            // Select Einstellungen tab
            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            // Locate components in the categories layout
            Component catLayout = $(H3.class).withText("Kategorien verwalten").single().getParent().orElseThrow();
            TextField newCatField = $(TextField.class, catLayout).single();
            Button addCatBtn = $(Button.class, catLayout).withText("Hinzufügen").single();
            Grid<DataService.CategoryDto> catGrid = $(Grid.class, catLayout).single();
            catGrid.setItems(dataService.getCategories());

            int initialSize = test(catGrid).size();

            // Fill and submit
            test(newCatField).setValue("Aktien");
            test(addCatBtn).click();

            // Verify success notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Kategorie hinzugefügt.");

            // Verify grid updates
            assertThat(test(catGrid).size()).isEqualTo(initialSize + 1);
            
            // Verify new category exists in grid rows
            boolean found = false;
            for (int i = 0; i < test(catGrid).size(); i++) {
                if (test(catGrid).getRow(i).name().equals("Aktien")) {
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
        @UseCase(id = "UC-003", scenario = "A1: Duplicate Category Name", businessRules = {"BR-003"})
        @DisplayName("Adding a duplicate category fails")
        void registration_fails_when_duplicate_category() {
            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component catLayout = $(H3.class).withText("Kategorien verwalten").single().getParent().orElseThrow();
            TextField newCatField = $(TextField.class, catLayout).single();
            Button addCatBtn = $(Button.class, catLayout).withText("Hinzufügen").single();
            Grid<DataService.CategoryDto> catGrid = $(Grid.class, catLayout).single();
            catGrid.setItems(dataService.getCategories());

            int initialSize = test(catGrid).size();

            // Fill with duplicate name "Girokonto" (which exists in seeds)
            test(newCatField).setValue("Girokonto");
            test(addCatBtn).click();

            // Verify error notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Kategorie existiert bereits.");

            // Verify size did not change
            assertThat(test(catGrid).size()).isEqualTo(initialSize);
        }

        @Test
        @UseCase(id = "UC-003", scenario = "A2: Delete Category")
        @DisplayName("Delete category not in use succeeds")
        void delete_category_not_in_use() {
            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component catLayout = $(H3.class).withText("Kategorien verwalten").single().getParent().orElseThrow();
            TextField newCatField = $(TextField.class, catLayout).single();
            Button addCatBtn = $(Button.class, catLayout).withText("Hinzufügen").single();
            Grid<DataService.CategoryDto> catGrid = $(Grid.class, catLayout).single();
            catGrid.setItems(dataService.getCategories());

            // 1. Add temporary category
            test(newCatField).setValue("TempCat");
            test(addCatBtn).click();
            assertThat($(Notification.class).exists()).isTrue();

            int sizeAfterAdd = test(catGrid).size();

            // 2. Find row index of "TempCat"
            int rowIndex = -1;
            for (int i = 0; i < test(catGrid).size(); i++) {
                if (test(catGrid).getRow(i).name().equals("TempCat")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            // 3. Delete it
            Button delBtn = (Button) test(catGrid).getCellComponent(rowIndex, 1);
            test(delBtn).click();

            // 4. Verify success notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Kategorie entfernt.");

            // 5. Verify size decreased
            assertThat(test(catGrid).size()).isEqualTo(sizeAfterAdd - 1);
        }

        @Test
        @UseCase(id = "UC-003", scenario = "A2: Delete Category")
        @DisplayName("Delete category in use fails")
        void delete_category_in_use_fails() {
            // Add a transaction referencing Girokonto to make it in-use
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new java.math.BigDecimal("10.00"), LocalDate.now());

            navigate(MainView.class);

            Tabs tabs = $(Tabs.class).single();
            Tab settingsTab = $(Tab.class).withText("Einstellungen").single();
            tabs.setSelectedTab(settingsTab);

            Component catLayout = $(H3.class).withText("Kategorien verwalten").single().getParent().orElseThrow();
            Grid<DataService.CategoryDto> catGrid = $(Grid.class, catLayout).single();
            catGrid.setItems(dataService.getCategories());

            int initialSize = test(catGrid).size();

            // Find row index of "Girokonto"
            int rowIndex = -1;
            for (int i = 0; i < test(catGrid).size(); i++) {
                if (test(catGrid).getRow(i).name().equals("Girokonto")) {
                    rowIndex = i;
                    break;
                }
            }
            assertThat(rowIndex).isNotEqualTo(-1);

            // Attempt to delete it
            Button delBtn = (Button) test(catGrid).getCellComponent(rowIndex, 1);
            test(delBtn).click();

            // Verify error notification
            assertThat($(Notification.class).exists()).isTrue();
            Notification notification = $(Notification.class).last();
            assertThat(test(notification).getText()).isEqualTo("Kategorie wird verwendet.");

            // Verify size did not change
            assertThat(test(catGrid).size()).isEqualTo(initialSize);
        }
    }
}
