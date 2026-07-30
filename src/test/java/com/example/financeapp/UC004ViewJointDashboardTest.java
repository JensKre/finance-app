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
import com.vaadin.flow.component.html.Span;
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
        @UseCase(id = "UC-004", businessRules = {"BR-004", "BR-005", "BR-006"})
        @DisplayName("Joint dashboard displays correct aggregated wealth, timeline chart, and category distribution pie chart")
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

            // Verify wealth card (BR-004)
            Component gesamtTitle = $(H3.class).withText("Gesamtvermögen").single();
            Div gesamtCard = (Div) gesamtTitle.getParent().orElseThrow();
            Paragraph gesamtValue = $(Paragraph.class, gesamtCard).single();
            assertThat(gesamtValue.getText()).isEqualTo("4.001,25 €");

            // Verify wealth trend chart exists (BR-005)
            Div chartCard = $(Div.class).withId("wealth-trend-chart").single();
            assertThat(chartCard).isNotNull();
            assertThat($(H3.class, chartCard).withText("Vermögensverlauf über die Zeit").exists()).isTrue();

            // Verify category pie chart card exists (BR-006)
            Div pieCard = $(Div.class).withId("category-pie-chart-card").single();
            assertThat(pieCard).isNotNull();
            assertThat($(H3.class, pieCard).withTextContaining("Kategorien-Verteilung").exists()).isTrue();

            // Verify wealth growth decomposition chart exists (BR-018)
            assertThat($(H3.class).withText("Vermögenszuwachs-Aufschlüsselung: Einnahmen/Ersparnisse vs. Wertsteigerung/Investitionen").exists()).isTrue();

            // No A1 warning is shown
            assertThat($(Div.class).withId("no-data-message").exists()).isFalse();
        }

        @Test
        @UseCase(id = "UC-004", businessRules = {"BR-006"})
        @DisplayName("Category pie chart uses exact defined category names dynamically")
        void category_pie_chart_uses_exact_configured_category_names() {
            // Seed categories and transactions with custom category names and Platzhalter institute
            dataService.addCategory("Tagesgeld+Sparkonto+Girokonto");
            dataService.addCategory("Genussrechte Stihl");
            dataService.addInstitute("Platzhalter");

            dataService.addTransaction("Jens", "Platzhalter", "Tagesgeld+Sparkonto+Girokonto", new BigDecimal("10000.00"), LocalDate.now());
            dataService.addTransaction("Jens", "Platzhalter", "Genussrechte Stihl", new BigDecimal("5000.00"), LocalDate.now());

            navigate(MainView.class);
            ((MainView) getCurrentView()).refreshData();

            Div pieCard = $(Div.class).withId("category-pie-chart-card").single();
            assertThat(pieCard).isNotNull();

            // Verify that legend contains Spans with the exact category names
            List<Span> categorySpans = $(Span.class, pieCard).all();
            List<String> spanTexts = categorySpans.stream().map(Span::getText).toList();
            assertThat(spanTexts).contains("Tagesgeld+Sparkonto+Girokonto", "Genussrechte Stihl");
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

            // Wealth cards and trend chart must NOT exist
            assertThat($(H3.class).withText("Gesamtvermögen").exists()).isFalse();
            assertThat($(Div.class).withId("wealth-trend-chart").exists()).isFalse();
        }
    }
}
