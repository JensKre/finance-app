package com.example.financeapp;

import java.math.BigDecimal;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UC004ViewJointDashboardTest extends SpringBrowserlessTest {

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
        @UseCase(id = "UC-004")
        @DisplayName("Joint dashboard displays correct aggregated wealth and transaction grid")
        void joint_dashboard_displays_correct_aggregations() {
            // Seed transactions for Jens and Annika
            dataService.addTransaction("Jens", "Sparkasse", "Girokonto", new BigDecimal("1500.50"), LocalDate.now());
            dataService.addTransaction("Annika", "Trade Republic", "Tagesgeldkonto", new BigDecimal("2500.75"), LocalDate.now());

            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            // Select Dashboard tab
            Tabs tabs = $(Tabs.class).single();
            Tab dashboardTab = $(Tab.class).withText("Dashboard").single();
            tabs.setSelectedTab(dashboardTab);

            // Verify wealth cards
            Component gesamtTitle = $(H3.class).withText("Gesamtvermögen").single();
            Div gesamtCard = (Div) gesamtTitle.getParent().orElseThrow();
            Paragraph gesamtValue = $(Paragraph.class, gesamtCard).single();
            assertThat(gesamtValue.getText()).isEqualTo("4.001,25 €");

            Component jensTitle = $(H3.class).withText("Jens").single();
            Div jensCard = (Div) jensTitle.getParent().orElseThrow();
            Paragraph jensValue = $(Paragraph.class, jensCard).single();
            assertThat(jensValue.getText()).isEqualTo("1.500,50 €");

            Component annikaTitle = $(H3.class).withText("Annika").single();
            Div annikaCard = (Div) annikaTitle.getParent().orElseThrow();
            Paragraph annikaValue = $(Paragraph.class, annikaCard).single();
            assertThat(annikaValue.getText()).isEqualTo("2.500,75 €");

            // Verify combined grid exists and contains both entries
            Grid<DataService.TransactionDto> combinedGrid = $(Grid.class).single();
            assertThat(test(combinedGrid).size()).isEqualTo(2);

            // No A1 warning is shown
            assertThat($(Div.class).withId("no-data-message").exists()).isFalse();
        }
    }

    @Nested
    @DisplayName("Alternative Flows")
    class AlternativeFlows {

        @Test
        @UseCase(id = "UC-004", scenario = "A1: No Data Found")
        @DisplayName("Suggestions message is displayed when there are no transactions")
        void warning_displayed_when_no_data() {
            // No transactions exist (guaranteed by setUp clean)

            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            // Select Dashboard tab
            Tabs tabs = $(Tabs.class).single();
            Tab dashboardTab = $(Tab.class).withText("Dashboard").single();
            tabs.setSelectedTab(dashboardTab);

            // Verify suggestion message is displayed
            Div noDataMessage = $(Div.class).withId("no-data-message").single();
            assertThat(noDataMessage.getText()).isEqualTo("Bitte tragen Sie Ihre erste Transaktion ein, um Daten auf dem Dashboard anzuzeigen.");

            // Wealth cards and activities grid must NOT exist
            assertThat($(H3.class).withText("Gesamtvermögen").exists()).isFalse();
            assertThat($(H3.class).withText("Jens").exists()).isFalse();
            assertThat($(H3.class).withText("Annika").exists()).isFalse();
            assertThat($(Grid.class).exists()).isFalse();
        }
    }
}
