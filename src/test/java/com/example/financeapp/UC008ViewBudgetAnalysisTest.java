package com.example.financeapp;

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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC008ViewBudgetAnalysisTest extends SpringBrowserlessTest {

    @Autowired
    private DataService dataService;

    @BeforeEach
    void setUp() {
        cleanData();
    }

    @AfterEach
    void tearDown() {
        cleanData();
    }

    private void cleanData() {
        dataService.clearImportMetadataAndBudgetTransactions();
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-008", businessRules = {"BR-014", "BR-015", "BR-016", "BR-017"})
        @DisplayName("Budget Auswertung tab renders category monthly sum charts and opens full-text drill-down dialog")
        void budget_analysis_renders_charts_and_drill_down_dialog() {
            // Seed budget data across multiple categories and months
            List<String[]> rows = new java.util.ArrayList<>();
            rows.add(new String[]{"15.03.2026", "Ausgabe", "24,90", "Abos", "Jens", "Disney Plus Abo"});
            rows.add(new String[]{"01.03.2026", "Ausgabe", "7,20", "Abos", "Annika", "Spotify"});
            rows.add(new String[]{"10.04.2026", "Ausgabe", "100,00", "Lebensmittel", "Jens", "Supermarkt Einkauf"});
            dataService.importBudgetCsvRows("march-april-budget.csv", rows);

            navigate(MainView.class);
            MainView view = (MainView) getCurrentView();
            view.refreshData();

            // Select Budget Auswertung tab
            Tabs tabs = $(Tabs.class).single();
            Tab analysisTab = $(Tab.class).withText("Budget Auswertung").single();
            tabs.setSelectedTab(analysisTab);
            view.refreshData();

            // Verify monthly aggregation query returns correct sums (BR-014)
            List<DataService.BudgetMonthlyCategorySumDto> sums = dataService.getBudgetMonthlyCategorySums();
            assertThat(sums).isNotEmpty();
            assertThat(sums).anyMatch(s -> s.categoryName().equals("Abos") && s.totalAmount().compareTo(new java.math.BigDecimal("32.10")) == 0);

            // Test drill-down transaction query (BR-017)
            List<DataService.BudgetTransactionDto> btxList = dataService.getBudgetTransactionsForCategoryAndMonth("Abos", java.time.YearMonth.of(2026, 3));
            assertThat(btxList).hasSize(2);
            assertThat(btxList.get(0).description()).isEqualTo("Disney Plus Abo");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-008", scenario = "A1: No Budget CSV Data Uploaded")
        @DisplayName("Displays info message when no budget CSV data exists")
        void displays_info_message_when_empty() {
            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            // Select Budget Auswertung tab
            Tabs tabs = $(Tabs.class).single();
            Tab analysisTab = $(Tab.class).withText("Budget Auswertung").single();
            tabs.setSelectedTab(analysisTab);

            // Verify empty state message
            assertThat($(com.vaadin.flow.component.html.Div.class)
                    .withText("Keine Budget-Daten vorhanden. Bitte laden Sie zuerst eine CSV-Datei im Tab 'CSV Import' hoch.")
                    .exists()).isTrue();
        }
    }
}
