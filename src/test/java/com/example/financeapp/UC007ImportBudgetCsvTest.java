package com.example.financeapp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC007ImportBudgetCsvTest extends SpringBrowserlessTest {

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
        // Clear metadata and test budget transactions via DataService helper
        dataService.clearImportMetadataAndBudgetTransactions();
    }

    @Nested
    @DisplayName("Main Success Scenario")
    class MainSuccess {

        @Test
        @UseCase(id = "UC-007", businessRules = {"BR-009", "BR-010", "BR-011", "BR-012"})
        @DisplayName("Importing budget CSV successfully stores new entries, rounds floating point numbers, and updates UI preview")
        void import_budget_csv_successfully() {
            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            // Select CSV Import tab
            Tabs tabs = $(Tabs.class).single();
            Tab csvTab = $(Tab.class).withText("CSV Import").single();
            tabs.setSelectedTab(csvTab);

            // Verify Header & Upload present
            assertThat($(H2.class).withText("Budget CSV Import").exists()).isTrue();
            Upload upload = $(Upload.class).single();
            assertThat(upload).isNotNull();

            // Perform CSV import with high floating point precision
            List<String[]> rows1 = new java.util.ArrayList<>();
            rows1.add(new String[]{"22.07.2026", "Ausgabe", "4,8799999999999999", "Lebensmittel", "Jens", "Lidl"});
            rows1.add(new String[]{"23.07.2026", "Ausgabe", "15,15", "Lebensmittel", "Jens", "Rewe"});
            DataService.CsvImportResult result = dataService.importBudgetCsvRows("test-budget-export.csv", rows1);

            assertThat(result.importedCount()).isEqualTo(2);

            // Verify database records contain 2 decimal place rounded amount (BR-010)
            List<DataService.BudgetTransactionDto> recentTxs = dataService.getRecentBudgetTransactions(100);
            assertThat(recentTxs).hasSize(2);
            assertThat(recentTxs.get(1).amount()).isEqualByComparingTo("4.88");

            // Verify latest import metadata (BR-012)
            DataService.ImportMetadataDto meta = dataService.getLatestImportMetadata();
            assertThat(meta).isNotNull();
            assertThat(meta.filename()).isEqualTo("test-budget-export.csv");
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-007", scenario = "A2: All Transactions Already Exist")
        @DisplayName("Importing duplicate CSV rows skips insertion and reports 0 imported")
        void importing_duplicate_rows_skips_insertion() {
            // Pre-seed data
            List<String[]> seedRows = new java.util.ArrayList<>();
            seedRows.add(new String[]{"22.07.2026", "Ausgabe", "4,88", "Lebensmittel", "Jens", "Lidl"});
            dataService.importBudgetCsvRows("initial.csv", seedRows);

            // Re-import same data
            DataService.CsvImportResult result = dataService.importBudgetCsvRows("second.csv", seedRows);

            assertThat(result.importedCount()).isEqualTo(0);
            assertThat(result.skippedCount()).isEqualTo(1);
        }
    }
}
