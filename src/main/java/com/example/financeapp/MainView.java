package com.example.financeapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout {

    private final DataService service;

    private final Map<Tab, Component> tabComponentMap = new HashMap<>();
    private final Tabs tabs = new Tabs();
    private final Div contentContainer = new Div();

    // Elements to refresh across tabs
    private final Div dashboardContainer = new Div();
    private final Grid<DataService.DateSummaryDto> datesGrid = new Grid<>();
    private final DatePicker datePicker = new DatePicker("Datum");
    private final VerticalLayout formRowsContainer = new VerticalLayout();

    @Autowired
    public MainView(DataService service) {
        System.out.println("DEBUG - MainView constructor called!");
        this.service = service;

        // Force Dark Mode Lumo Theme
        UI.getCurrent().getElement().setAttribute("theme", Lumo.DARK);

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Couples Finance Tracker");
        title.addClassName("page-title");
        add(title);

        // Tab setup
        Tab dashboardTab = new Tab("Dashboard");
        Tab eingabeTab = new Tab("Eingabe");
        Tab forecastTab = new Tab("Prognose");
        Tab csvImportTab = new Tab("CSV Import");
        Tab budgetAnalysisTab = new Tab("Budget Auswertung");
        Tab settingsTab = new Tab("Einstellungen");

        tabs.add(dashboardTab, eingabeTab, forecastTab, csvImportTab, budgetAnalysisTab, settingsTab);
        tabs.setWidth("100%");
        add(tabs);

        // Create component contents
        tabComponentMap.put(dashboardTab, createDashboardContent());
        tabComponentMap.put(eingabeTab, createEingabeTabContent(null, datesGrid, datePicker, formRowsContainer));
        tabComponentMap.put(forecastTab, createForecastContent());
        tabComponentMap.put(csvImportTab, createCsvImportContent());
        tabComponentMap.put(budgetAnalysisTab, createBudgetAnalysisContent());
        tabComponentMap.put(settingsTab, createSettingsContent());

        contentContainer.setSizeFull();
        contentContainer.getStyle().set("overflow", "auto");
        add(contentContainer);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            contentContainer.removeAll();
            if (selectedTab != null) {
                refreshData();
                contentContainer.add(tabComponentMap.get(selectedTab));
            }
        });

        // Set default tab
        tabs.setSelectedTab(dashboardTab);
        contentContainer.add(tabComponentMap.get(dashboardTab));
        refreshData();
    }

    private String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0,00 €";
        }
        java.text.DecimalFormat formatter = (java.text.DecimalFormat) java.text.NumberFormat.getInstance(java.util.Locale.GERMANY);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(amount) + " €";
    }

    void refreshData() {
        // Refresh grid
        datesGrid.setItems(service.getDateSummaries(null));

        // Rebuild active form rows
        rebuildFormRows(null, datePicker.getValue(), formRowsContainer);

        // Refresh dashboard numbers
        dashboardContainer.removeAll();
        List<DataService.TransactionDto> allTx = service.getTransactions(null);
        if (allTx.isEmpty()) {
            Div noDataMessage = new Div();
            noDataMessage.setId("no-data-message");
            noDataMessage.setText("Bitte tragen Sie Ihre erste Transaktion ein, um Daten auf dem Dashboard anzuzeigen.");
            noDataMessage.getStyle()
                    .set("margin-top", "50px")
                    .set("font-size", "1.2rem")
                    .set("color", "var(--lumo-secondary-text-color)");
            dashboardContainer.add(noDataMessage);
        } else {
            BigDecimal totalWealth = service.getCurrentWealth(null);

            Div cards = new Div();
            cards.getStyle().set("display", "flex").set("gap", "24px").set("justify-content", "center").set("flex-wrap", "wrap").set("margin-top", "24px");

            cards.add(createCard("Gesamtvermögen", formatAmount(totalWealth), "linear-gradient(135deg, rgba(99, 102, 241, 0.4) 0%, rgba(79, 70, 229, 0.4) 100%)"));

            dashboardContainer.add(cards);

            // Wealth timeline chart over time (UC-004)
            List<DataService.DateSummaryDto> chronologicalSummaries = service.getChronologicalDateSummaries(null);
            dashboardContainer.add(createWealthTrendChart(chronologicalSummaries));

            // Category distribution pie chart for latest snapshot date (UC-004)
            LocalDate latestDate = service.getLatestTransactionDate();
            if (latestDate != null) {
                List<DataService.CategoryShareDto> categoryShares = service.getCategorySharesForDate(latestDate);
                dashboardContainer.add(createCategoryPieChart(latestDate, categoryShares));
            }

            // Historical percentage share timeline chart (UC-004, BR-013)
            List<DataService.CategoryHistoricalShareDto> historicalShares = service.getHistoricalCategorySharePercentages();
            dashboardContainer.add(createCategoryPercentageTimelineChart(historicalShares));

            // Wealth growth breakdown chart (UC-004, BR-018)
            List<DataService.WealthGrowthDecompositionDto> growthDecomp = service.getWealthGrowthDecomposition(null);
            dashboardContainer.add(createWealthGrowthDecompositionChart(growthDecomp));
        }
    }

    private Component createCategoryPieChart(LocalDate date, List<DataService.CategoryShareDto> shares) {
        if (shares == null || shares.isEmpty()) {
            return new Div();
        }

        Div card = new Div();
        card.setId("category-pie-chart-card");
        card.getStyle()
                .set("margin-top", "32px")
                .set("margin-bottom", "32px")
                .set("padding", "24px")
                .set("border-radius", "16px")
                .set("background", "var(--lumo-base-color)")
                .set("box-shadow", "0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)")
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("max-width", "900px")
                .set("width", "100%");

        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        H3 title = new H3("Kategorien-Verteilung (Stand: " + formattedDate + ")");
        title.getStyle().set("margin-top", "0").set("margin-bottom", "24px");
        card.add(title);

        BigDecimal grandTotal = shares.stream()
                .map(DataService.CategoryShareDto::totalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (grandTotal.compareTo(BigDecimal.ZERO) <= 0) {
            return card;
        }

        // Color palette for slices
        String[] colors = {
            "#6366f1", // Indigo
            "#10b981", // Emerald
            "#f59e0b", // Amber
            "#ec4899", // Pink
            "#8b5cf6", // Purple
            "#06b6d4", // Cyan
            "#3b82f6", // Blue
            "#f97316", // Orange
            "#14b8a6", // Teal
            "#eab308"  // Yellow
        };

        Div flexLayout = new Div();
        flexLayout.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("align-items", "center")
                .set("justify-content", "space-around")
                .set("gap", "32px");

        int svgSize = 280;
        int cx = 140;
        int cy = 140;
        int outerRadius = 110;
        int innerRadius = 55;

        String defaultTitle = formatCompactAmount(grandTotal);
        String defaultSub = "Gesamt";

        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg viewBox=\"0 0 %d %d\" style=\"width: %dpx; height: %dpx; font-family: var(--lumo-font-family);\">", svgSize, svgSize, svgSize, svgSize));

        double currentAngle = -Math.PI / 2;

        for (int i = 0; i < shares.size(); i++) {
            DataService.CategoryShareDto share = shares.get(i);
            double value = share.totalAmount().doubleValue();
            double fraction = value / grandTotal.doubleValue();
            double sliceAngle = fraction * 2 * Math.PI;

            double nextAngle = currentAngle + sliceAngle;
            String color = colors[i % colors.length];

            double x1 = cx + outerRadius * Math.cos(currentAngle);
            double y1 = cy + outerRadius * Math.sin(currentAngle);
            double x2 = cx + outerRadius * Math.cos(nextAngle);
            double y2 = cy + outerRadius * Math.sin(nextAngle);

            double x3 = cx + innerRadius * Math.cos(nextAngle);
            double y3 = cy + innerRadius * Math.sin(nextAngle);
            double x4 = cx + innerRadius * Math.cos(currentAngle);
            double y4 = cy + innerRadius * Math.sin(currentAngle);

            int largeArcFlag = sliceAngle > Math.PI ? 1 : 0;

            String pathData = String.format(java.util.Locale.US,
                    "M %.2f,%.2f A %d,%d 0 %d,1 %.2f,%.2f L %.2f,%.2f A %d,%d 0 %d,0 %.2f,%.2f Z",
                    x1, y1, outerRadius, outerRadius, largeArcFlag, x2, y2,
                    x3, y3, innerRadius, innerRadius, largeArcFlag, x4, y4);

            double percentage = fraction * 100.0;
            String catName = share.categoryName().replace("'", "\\'");
            String catAmount = formatAmount(share.totalAmount()).replace("'", "\\'");
            String catPct = String.format(java.util.Locale.GERMANY, "%.1f %%", percentage);

            String onMouseOver = String.format(
                "document.getElementById('donut-center-title').textContent='%s';" +
                "document.getElementById('donut-center-sub').textContent='%s (%s)';" +
                "this.style.opacity='0.85';",
                catName, catAmount, catPct
            );

            String onMouseOut = String.format(
                "document.getElementById('donut-center-title').textContent='%s';" +
                "document.getElementById('donut-center-sub').textContent='%s';" +
                "this.style.opacity='1.0';",
                defaultTitle, defaultSub
            );

            String tooltip = String.format("%s: %s (%.1f %%)", share.categoryName(), formatAmount(share.totalAmount()), percentage);

            svg.append(String.format(java.util.Locale.US,
                "<path d=\"%s\" fill=\"%s\" stroke=\"var(--lumo-base-color)\" stroke-width=\"2\" style=\"cursor: pointer; transition: opacity 0.15s;\" onmouseover=\"%s\" onmouseout=\"%s\">",
                pathData, color, onMouseOver, onMouseOut));
            svg.append(String.format("<title>%s</title>", tooltip));
            svg.append("</path>");

            currentAngle = nextAngle;
        }

        svg.append(String.format("<text id=\"donut-center-title\" x=\"%d\" y=\"%d\" text-anchor=\"middle\" dominant-baseline=\"middle\" font-weight=\"bold\" font-size=\"14\" fill=\"var(--lumo-primary-text-color)\">%s</text>",
                cx, cy - 8, defaultTitle));
        svg.append(String.format("<text id=\"donut-center-sub\" x=\"%d\" y=\"%d\" text-anchor=\"middle\" dominant-baseline=\"middle\" font-size=\"11\" fill=\"var(--lumo-secondary-text-color)\">%s</text>",
                cx, cy + 12, defaultSub));

        svg.append("</svg>");

        Div svgWrapper = new Div();
        svgWrapper.getElement().setProperty("innerHTML", svg.toString());

        Div legend = new Div();
        legend.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", "12px")
                .set("min-width", "260px");

        for (int i = 0; i < shares.size(); i++) {
            DataService.CategoryShareDto share = shares.get(i);
            String color = colors[i % colors.length];
            double percentage = share.totalAmount().doubleValue() / grandTotal.doubleValue() * 100.0;

            Div legendItem = new Div();
            legendItem.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "space-between")
                    .set("font-size", "0.95rem");

            Div leftGroup = new Div();
            leftGroup.getStyle().set("display", "flex").set("align-items", "center").set("gap", "10px");

            Div colorBadge = new Div();
            colorBadge.getStyle()
                    .set("width", "12px")
                    .set("height", "12px")
                    .set("border-radius", "50%")
                    .set("background", color)
                    .set("flex-shrink", "0");

            Span nameSpan = new Span(share.categoryName());
            nameSpan.getStyle().set("font-weight", "500");
            leftGroup.add(colorBadge, nameSpan);

            Div rightGroup = new Div();
            rightGroup.getStyle().set("display", "flex").set("gap", "12px").set("align-items", "center");

            Span amountSpan = new Span(formatAmount(share.totalAmount()));
            amountSpan.getStyle().set("font-weight", "600");

            Span pctSpan = new Span(String.format(java.util.Locale.GERMANY, "(%.1f %%)", percentage));
            pctSpan.getStyle().set("color", "var(--lumo-secondary-text-color)").set("font-size", "0.85rem");

            rightGroup.add(amountSpan, pctSpan);
            legendItem.add(leftGroup, rightGroup);
            legend.add(legendItem);
        }

        flexLayout.add(svgWrapper, legend);
        card.add(flexLayout);

        return card;
    }

    private Component createWealthTrendChart(List<DataService.DateSummaryDto> summaries) {
        if (summaries == null || summaries.isEmpty()) {
            return new Div();
        }

        Div chartCard = new Div();
        chartCard.setId("wealth-trend-chart");
        chartCard.getStyle()
                .set("margin-top", "32px")
                .set("padding", "24px")
                .set("border-radius", "16px")
                .set("background", "var(--lumo-base-color)")
                .set("box-shadow", "0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)")
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("max-width", "900px")
                .set("width", "100%");

        H3 chartTitle = new H3("Vermögensverlauf über die Zeit");
        chartTitle.getStyle().set("margin-top", "0").set("margin-bottom", "4px");

        Div chartSubTitle = new Div();
        chartSubTitle.setId("wealth-trend-hover-info");
        chartSubTitle.setText("Fahren Sie mit der Maus über einen Datenpunkt, um den genauen Vermögenswert anzuzeigen.");
        chartSubTitle.getStyle()
                .set("font-size", "0.95rem")
                .set("line-height", "24px")
                .set("height", "24px")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("margin-bottom", "16px");

        chartCard.add(chartTitle, chartSubTitle);

        BigDecimal maxAmount = summaries.stream()
                .map(DataService.DateSummaryDto::totalAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        double maxVal = maxAmount.doubleValue();
        double stepSize = 100_000.0;
        int maxStep = (int) Math.ceil(maxVal / stepSize);
        if (maxStep < 1) maxStep = 1;
        // add 1 headroom step so the top curve doesn't hit the top border
        maxStep++;

        double maxY = maxStep * stepSize;

        int width = 850;
        int height = 380;
        int paddingLeft = 110;
        int paddingRight = 40;
        int paddingTop = 45;
        int paddingBottom = 50;

        int plotWidth = width - paddingLeft - paddingRight;
        int plotHeight = height - paddingTop - paddingBottom;

        int n = summaries.size();

        LocalDate minDate = summaries.get(0).date();
        LocalDate maxDate = summaries.get(n - 1).date();
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, maxDate);

        record ChartPoint(double x, double y, LocalDate date, BigDecimal amount) {}
        List<ChartPoint> points = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            double x;
            if (totalDays <= 0 || n <= 1) {
                x = paddingLeft + (plotWidth / 2.0);
            } else {
                long elapsedDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, summaries.get(i).date());
                x = paddingLeft + ((double) elapsedDays / (double) totalDays * plotWidth);
            }
            double val = summaries.get(i).totalAmount().doubleValue();
            double y = paddingTop + plotHeight - (val / maxY * plotHeight);
            points.add(new ChartPoint(x, y, summaries.get(i).date(), summaries.get(i).totalAmount()));
        }

        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg viewBox=\"0 0 %d %d\" style=\"width: 100%%; height: auto; font-family: var(--lumo-font-family);\">", width, height));

        // SVG Gradient definition
        svg.append("<defs>")
           .append("<linearGradient id=\"wealthGradient\" x1=\"0\" y1=\"0\" x2=\"0\" y2=\"1\">")
           .append("<stop offset=\"0%\" stop-color=\"#6366f1\" stop-opacity=\"0.35\"/>")
           .append("<stop offset=\"100%\" stop-color=\"#6366f1\" stop-opacity=\"0.02\"/>")
           .append("</linearGradient>")
           .append("</defs>");

        // Horizontal grid lines and Y-axis labels in 100.000 € steps
        for (int step = 0; step <= maxStep; step++) {
            double labelVal = step * stepSize;
            double y = paddingTop + plotHeight - (labelVal / maxY * plotHeight);
            svg.append(String.format(java.util.Locale.US, "<line x1=\"%d\" y1=\"%.1f\" x2=\"%d\" y2=\"%.1f\" stroke=\"var(--lumo-contrast-10pct)\" stroke-dasharray=\"4,4\"/>",
                    paddingLeft, y, width - paddingRight, y));
            
            String labelText = formatAmount(BigDecimal.valueOf(labelVal)).replace(",00", "");
            svg.append(String.format(java.util.Locale.US, "<text x=\"%d\" y=\"%.1f\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"end\" dominant-baseline=\"middle\">%s</text>",
                    paddingLeft - 12, y, labelText));
        }

        // Build SVG paths for line & filled gradient area
        StringBuilder pathD = new StringBuilder();
        StringBuilder areaD = new StringBuilder();

        if (!points.isEmpty()) {
            pathD.append(String.format(java.util.Locale.US, "M %.1f,%.1f", points.get(0).x, points.get(0).y));
            areaD.append(String.format(java.util.Locale.US, "M %.1f,%.1f L %.1f,%.1f", points.get(0).x, (double) (paddingTop + plotHeight), points.get(0).x, points.get(0).y));

            for (int i = 1; i < points.size(); i++) {
                pathD.append(String.format(java.util.Locale.US, " L %.1f,%.1f", points.get(i).x, points.get(i).y));
                areaD.append(String.format(java.util.Locale.US, " L %.1f,%.1f", points.get(i).x, points.get(i).y));
            }

            areaD.append(String.format(java.util.Locale.US, " L %.1f,%.1f Z", points.get(points.size() - 1).x, (double) (paddingTop + plotHeight)));
        }

        svg.append(String.format("<path d=\"%s\" fill=\"url(#wealthGradient)\" />", areaD.toString()));
        svg.append(String.format("<path d=\"%s\" fill=\"none\" stroke=\"#6366f1\" stroke-width=\"3\" stroke-linecap=\"round\" stroke-linejoin=\"round\" />", pathD.toString()));

        // Points and X-axis date labels
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int i = 0; i < points.size(); i++) {
            ChartPoint p = points.get(i);
            String formattedDate = p.date.format(dateFmt);
            String formattedAmt = formatAmount(p.amount).replace("'", "\\'");

            String mouseOver = String.format(
                "document.getElementById(&quot;wealth-trend-hover-info&quot;).innerHTML=&quot;&lt;strong&gt;%s&lt;/strong&gt;: &lt;span style=\\&quot;color:#6366f1; font-weight:bold; font-size:0.95rem;\\&quot;&gt;%s&lt;/span&gt;&quot;; this.setAttribute(&quot;r&quot;, &quot;8&quot;);",
                formattedDate, formattedAmt
            );
            String mouseOut = "document.getElementById(&quot;wealth-trend-hover-info&quot;).textContent=&quot;Fahren Sie mit der Maus über einen Datenpunkt, um den genauen Vermögenswert anzuzeigen.&quot;; this.setAttribute(&quot;r&quot;, &quot;5&quot;);";

            svg.append(String.format(java.util.Locale.US, "<circle cx=\"%.1f\" cy=\"%.1f\" r=\"5\" fill=\"#6366f1\" stroke=\"#ffffff\" stroke-width=\"2\" style=\"cursor:pointer; transition: all 0.2s ease;\" onmouseover=\"%s\" onmouseout=\"%s\">", p.x, p.y, mouseOver, mouseOut));
            svg.append(String.format("<title>%s: %s</title>", formattedDate, formatAmount(p.amount)));
            svg.append("</circle>");

            if (n <= 12 || i % Math.max(1, n / 6) == 0 || i == n - 1) {
                svg.append(String.format(java.util.Locale.US, "<text x=\"%.1f\" y=\"%d\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"middle\">%s</text>",
                        p.x, height - 15, p.date.format(dateFmt)));
            }
        }

        svg.append("</svg>");

        Div svgContainer = new Div();
        svgContainer.getElement().setProperty("innerHTML", svg.toString());
        chartCard.add(svgContainer);

        return chartCard;
    }

    private Component createCategoryPercentageTimelineChart(List<DataService.CategoryHistoricalShareDto> historicalShares) {
        if (historicalShares == null || historicalShares.isEmpty()) {
            return new Div();
        }

        Div chartCard = new Div();
        chartCard.setId("category-percentage-timeline-card");
        chartCard.getStyle()
                .set("margin-top", "32px")
                .set("margin-bottom", "32px")
                .set("padding", "24px")
                .set("border-radius", "16px")
                .set("background", "var(--lumo-base-color)")
                .set("box-shadow", "0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)")
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("max-width", "900px")
                .set("width", "100%");

        H3 chartTitle = new H3("Prozentualer Vermögensverlauf nach Kategorien über die Zeit");
        chartTitle.getStyle().set("margin-top", "0").set("margin-bottom", "12px");
        chartCard.add(chartTitle);

        // Dynamic subtitle for hover details
        Div subTitle = new Div();
        subTitle.setId("pct-chart-hover-info");
        subTitle.setText("Fahren Sie mit der Maus über eine Linie oder Kategorie, um Details anzuzeigen.");
        subTitle.getStyle()
                .set("font-size", "0.95rem")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("margin-bottom", "16px")
                .set("min-height", "24px")
                .set("font-weight", "500");
        chartCard.add(subTitle);

        // Group by chronological distinct dates and distinct categories
        List<LocalDate> sortedDates = historicalShares.stream()
                .map(DataService.CategoryHistoricalShareDto::date)
                .distinct()
                .sorted()
                .toList();

        List<String> categories = historicalShares.stream()
                .map(DataService.CategoryHistoricalShareDto::categoryName)
                .distinct()
                .toList();

        if (sortedDates.isEmpty() || categories.isEmpty()) {
            return chartCard;
        }

        String[] colors = {
            "#6366f1", // Indigo
            "#10b981", // Emerald
            "#f59e0b", // Amber
            "#ec4899", // Pink
            "#8b5cf6", // Purple
            "#06b6d4", // Cyan
            "#3b82f6", // Blue
            "#f97316", // Orange
            "#14b8a6", // Teal
            "#eab308"  // Yellow
        };

        int width = 850;
        int height = 380;
        int paddingLeft = 60;
        int paddingRight = 40;
        int paddingTop = 45;
        int paddingBottom = 50;

        int plotWidth = width - paddingLeft - paddingRight;
        int plotHeight = height - paddingTop - paddingBottom;
        int nDates = sortedDates.size();

        LocalDate minDate = sortedDates.get(0);
        LocalDate maxDate = sortedDates.get(nDates - 1);
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, maxDate);

        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg viewBox=\"0 0 %d %d\" style=\"width: 100%%; height: auto; font-family: var(--lumo-font-family);\">", width, height));

        // Horizontal grid lines for Y-axis (0%, 25%, 50%, 75%, 100%)
        for (int pct = 0; pct <= 100; pct += 25) {
            double y = paddingTop + plotHeight - ((double) pct / 100.0 * plotHeight);
            svg.append(String.format(java.util.Locale.US, "<line x1=\"%d\" y1=\"%.1f\" x2=\"%d\" y2=\"%.1f\" stroke=\"var(--lumo-contrast-10pct)\" stroke-dasharray=\"4,4\"/>",
                    paddingLeft, y, width - paddingRight, y));
            svg.append(String.format(java.util.Locale.US, "<text x=\"%d\" y=\"%.1f\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"end\" dominant-baseline=\"middle\">%d %%</text>",
                    paddingLeft - 10, y, pct));
        }

        // Draw line & data points for each category
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yy");
        for (int catIdx = 0; catIdx < categories.size(); catIdx++) {
            String catName = categories.get(catIdx);
            String safeCatName = catName.replace("'", "\\'");
            String color = colors[catIdx % colors.length];

            StringBuilder pathD = new StringBuilder();
            boolean first = true;

            for (int dateIdx = 0; dateIdx < nDates; dateIdx++) {
                LocalDate date = sortedDates.get(dateIdx);
                double x;
                if (totalDays <= 0 || nDates <= 1) {
                    x = paddingLeft + (plotWidth / 2.0);
                } else {
                    long elapsedDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, date);
                    x = paddingLeft + ((double) elapsedDays / (double) totalDays * plotWidth);
                }

                var match = historicalShares.stream()
                        .filter(s -> s.date().equals(date) && s.categoryName().equals(catName))
                        .findFirst();

                double pctVal = match.map(DataService.CategoryHistoricalShareDto::percentage).orElse(0.0);
                double y = paddingTop + plotHeight - (pctVal / 100.0 * plotHeight);

                if (first) {
                    pathD.append(String.format(java.util.Locale.US, "M %.1f,%.1f", x, y));
                    first = false;
                } else {
                    pathD.append(String.format(java.util.Locale.US, " L %.1f,%.1f", x, y));
                }
            }

            String lineMouseOver = String.format(
                "document.querySelectorAll(&quot;.pct-line&quot;).forEach(el =&gt; el.style.opacity=&quot;0.2&quot;);" +
                "document.querySelectorAll(&quot;.pct-line-cat-%d&quot;).forEach(el =&gt; { el.style.opacity=&quot;1&quot;; el.style.strokeWidth=&quot;4.5&quot;; });" +
                "document.getElementById(&quot;pct-chart-hover-info&quot;).innerHTML=&quot;&lt;span style=\\&quot;color:%s; font-weight:bold;\\&quot;&gt;%s&lt;/span&gt; - Fahren Sie über Punkte für Details&quot;;",
                catIdx, color, safeCatName
            );

            String lineMouseOut =
                "document.querySelectorAll(&quot;.pct-line&quot;).forEach(el =&gt; { el.style.opacity=&quot;1&quot;; el.style.strokeWidth=&quot;2.5&quot;; });" +
                "document.getElementById(&quot;pct-chart-hover-info&quot;).textContent=&quot;Fahren Sie mit der Maus über eine Linie oder Kategorie, um Details anzuzeigen.&quot;;";

            svg.append(String.format(java.util.Locale.US,
                "<path class=\"pct-line pct-line-cat-%d\" d=\"%s\" fill=\"none\" stroke=\"%s\" stroke-width=\"2.5\" stroke-linecap=\"round\" stroke-linejoin=\"round\" style=\"cursor:pointer; transition: all 0.2s ease;\" onmouseover=\"%s\" onmouseout=\"%s\" />",
                catIdx, pathD.toString(), color, lineMouseOver, lineMouseOut));

            // Draw points with hover details
            for (int dateIdx = 0; dateIdx < nDates; dateIdx++) {
                LocalDate date = sortedDates.get(dateIdx);
                double x;
                if (totalDays <= 0 || nDates <= 1) {
                    x = paddingLeft + (plotWidth / 2.0);
                } else {
                    long elapsedDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, date);
                    x = paddingLeft + ((double) elapsedDays / (double) totalDays * plotWidth);
                }

                var match = historicalShares.stream()
                        .filter(s -> s.date().equals(date) && s.categoryName().equals(catName))
                        .findFirst();

                double pctVal = match.map(DataService.CategoryHistoricalShareDto::percentage).orElse(0.0);
                BigDecimal amt = match.map(DataService.CategoryHistoricalShareDto::totalAmount).orElse(BigDecimal.ZERO);
                double y = paddingTop + plotHeight - (pctVal / 100.0 * plotHeight);

                String formattedPct = String.format(java.util.Locale.GERMANY, "%.1f %%", pctVal);
                String formattedAmt = formatAmount(amt).replace("'", "\\'");
                String formattedDateStr = date.format(dateFmt);

                String pointMouseOver = String.format(
                    "document.querySelectorAll(&quot;.pct-line&quot;).forEach(el =&gt; el.style.opacity=&quot;0.2&quot;);" +
                    "document.querySelectorAll(&quot;.pct-line-cat-%d&quot;).forEach(el =&gt; { el.style.opacity=&quot;1&quot;; el.style.strokeWidth=&quot;4.5&quot;; });" +
                    "document.getElementById(&quot;pct-chart-hover-info&quot;).innerHTML=&quot;&lt;span style=\\&quot;color:%s; font-weight:bold;\\&quot;&gt;%s&lt;/span&gt; (%s): &lt;strong&gt;%s&lt;/strong&gt; (%s)&quot;;" +
                    "this.setAttribute(&quot;r&quot;, &quot;7&quot;);",
                    catIdx, color, safeCatName, formattedDateStr, formattedAmt, formattedPct
                );

                String pointMouseOut =
                    "document.querySelectorAll(&quot;.pct-line&quot;).forEach(el =&gt; { el.style.opacity=&quot;1&quot;; el.style.strokeWidth=&quot;2.5&quot;; });" +
                    "document.getElementById(&quot;pct-chart-hover-info&quot;).textContent=&quot;Fahren Sie mit der Maus über eine Linie oder Kategorie, um Details anzuzeigen.&quot;;" +
                    "this.setAttribute(&quot;r&quot;, &quot;4&quot;);";

                svg.append(String.format(java.util.Locale.US,
                    "<circle class=\"pct-line pct-line-cat-%d\" cx=\"%.1f\" cy=\"%.1f\" r=\"4\" fill=\"%s\" stroke=\"#ffffff\" stroke-width=\"1.5\" style=\"cursor:pointer; transition: all 0.2s ease;\" onmouseover=\"%s\" onmouseout=\"%s\">",
                    catIdx, x, y, color, pointMouseOver, pointMouseOut));
                svg.append(String.format("<title>%s (%s): %s (%s)</title>", catName, date.format(dateFmt), formatAmount(amt), formattedPct));
                svg.append("</circle>");
            }
        }

        // X-axis date labels
        for (int i = 0; i < nDates; i++) {
            LocalDate date = sortedDates.get(i);
            double x;
            if (totalDays <= 0 || nDates <= 1) {
                x = paddingLeft + (plotWidth / 2.0);
            } else {
                long elapsedDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, date);
                x = paddingLeft + ((double) elapsedDays / (double) totalDays * plotWidth);
            }
            if (nDates <= 12 || i % Math.max(1, nDates / 6) == 0 || i == nDates - 1) {
                svg.append(String.format(java.util.Locale.US, "<text x=\"%.1f\" y=\"%d\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"middle\">%s</text>",
                        x, height - 15, date.format(dateFmt)));
            }
        }

        svg.append("</svg>");

        Div svgContainer = new Div();
        svgContainer.getElement().setProperty("innerHTML", svg.toString());
        chartCard.add(svgContainer);

        // Interactive Legend below chart
        Div legend = new Div();
        legend.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("gap", "16px")
                .set("justify-content", "center")
                .set("margin-top", "16px");

        for (int catIdx = 0; catIdx < categories.size(); catIdx++) {
            String catName = categories.get(catIdx);
            String safeCatName = catName.replace("'", "\\'");
            String color = colors[catIdx % colors.length];

            Div item = new Div();
            item.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("gap", "8px")
                    .set("cursor", "pointer")
                    .set("padding", "4px 8px")
                    .set("border-radius", "6px")
                    .set("transition", "background-color 0.2s ease");

            String legendMouseOver = String.format(
                "document.querySelectorAll('.pct-line').forEach(el => el.style.opacity='0.2');" +
                "document.querySelectorAll('.pct-line-cat-%d').forEach(el => { el.style.opacity='1'; el.style.strokeWidth='4.5'; });" +
                "document.getElementById('pct-chart-hover-info').innerHTML='Kategorie: <span style=\"color:%s; font-weight:bold;\">%s</span>';" +
                "this.style.backgroundColor='var(--lumo-contrast-5pct)';",
                catIdx, color, safeCatName
            );

            String legendMouseOut =
                "document.querySelectorAll('.pct-line').forEach(el => { el.style.opacity='1'; el.style.strokeWidth='2.5'; });" +
                "document.getElementById('pct-chart-hover-info').textContent='Fahren Sie mit der Maus über eine Linie oder Kategorie, um Details anzuzeigen.';" +
                "this.style.backgroundColor='transparent';";

            item.getElement().setAttribute("onmouseover", legendMouseOver);
            item.getElement().setAttribute("onmouseout", legendMouseOut);

            Span badge = new Span();
            badge.getStyle().set("width", "12px").set("height", "12px").set("border-radius", "3px").set("background-color", color);

            Span name = new Span(catName);
            name.getStyle().set("font-size", "0.9rem").set("color", "var(--lumo-body-text-color)");

            item.add(badge, name);
            legend.add(item);
        }

        chartCard.add(legend);
        return chartCard;
    }

    private Component createWealthGrowthDecompositionChart(List<DataService.WealthGrowthDecompositionDto> decompositions) {
        Div chartCard = new Div();
        chartCard.addClassName("glass-panel");
        chartCard.getStyle()
                .set("margin-top", "24px")
                .set("padding", "24px")
                .set("border-radius", "16px")
                .set("width", "100%");

        H3 title = new H3("Vermögenszuwachs-Aufschlüsselung: Einnahmen/Ersparnisse vs. Wertsteigerung/Investitionen");
        title.getStyle().set("margin-top", "0").set("margin-bottom", "4px").set("text-align", "center");

        Div subTitle = new Div();
        subTitle.setId("growth-chart-hover-info");
        subTitle.setText("Fahren Sie mit der Maus über einen Zeitraum, um Details zu Einnahmen und Wertsteigerung anzuzeigen.");
        subTitle.getStyle()
                .set("text-align", "center")
                .set("font-size", "0.95rem")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("margin-bottom", "20px")
                .set("min-height", "24px");

        chartCard.add(title, subTitle);

        if (decompositions == null || decompositions.isEmpty()) {
            Div emptyMsg = new Div();
            emptyMsg.setText("Nicht genügend Stichtage für eine Zuwachs-Aufschlüsselung vorhanden (mindestens 2 Stichtage erforderlich).");
            emptyMsg.getStyle().set("text-align", "center").set("color", "var(--lumo-secondary-text-color)").set("padding", "20px");
            chartCard.add(emptyMsg);
            return chartCard;
        }

        int width = 800;
        int height = 300;
        int paddingLeft = 75;
        int paddingRight = 35;
        int paddingTop = 30;
        int paddingBottom = 45;

        int plotWidth = width - paddingLeft - paddingRight;
        int plotHeight = height - paddingTop - paddingBottom;
        int nIntervals = decompositions.size();

        double minVal = 0.0;
        double maxVal = 0.0;
        for (var d : decompositions) {
            double tot = d.totalDelta().doubleValue();
            double sav = d.netBudgetSavings().doubleValue();
            double app = d.investmentAppreciation().doubleValue();

            maxVal = Math.max(maxVal, Math.max(tot, Math.max(sav, app)));
            minVal = Math.min(minVal, Math.min(tot, Math.min(sav, app)));
        }

        if (maxVal == 0.0 && minVal == 0.0) {
            maxVal = 1000.0;
        }
        double valRange = maxVal - minVal;
        if (valRange <= 0) valRange = 1000.0;
        maxVal += valRange * 0.15;
        minVal -= valRange * 0.15;
        valRange = maxVal - minVal;

        double zeroY = paddingTop + plotHeight - ((0.0 - minVal) / valRange * plotHeight);

        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg viewBox=\"0 0 %d %d\" style=\"width: 100%%; height: auto; font-family: var(--lumo-font-family);\">", width, height));

        double[] steps = {minVal, minVal + valRange * 0.33, minVal + valRange * 0.66, maxVal};
        for (double v : steps) {
            double y = paddingTop + plotHeight - ((v - minVal) / valRange * plotHeight);
            svg.append(String.format(java.util.Locale.US, "<line x1=\"%d\" y1=\"%.1f\" x2=\"%d\" y2=\"%.1f\" stroke=\"var(--lumo-contrast-10pct)\" stroke-dasharray=\"4,4\"/>",
                    paddingLeft, y, width - paddingRight, y));
            svg.append(String.format(java.util.Locale.US, "<text x=\"%d\" y=\"%.1f\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"end\" dominant-baseline=\"middle\">%s</text>",
                    paddingLeft - 10, y, formatCompactAmount(BigDecimal.valueOf(v))));
        }

        if (zeroY >= paddingTop && zeroY <= paddingTop + plotHeight) {
            svg.append(String.format(java.util.Locale.US, "<line x1=\"%d\" y1=\"%.1f\" x2=\"%d\" y2=\"%.1f\" stroke=\"var(--lumo-contrast-30pct)\" stroke-width=\"1.5\"/>",
                    paddingLeft, zeroY, width - paddingRight, zeroY));
        }

        double slotWidth = (double) plotWidth / nIntervals;
        double groupWidth = Math.max(12.0, Math.min(48.0, slotWidth * 0.7));
        double barWidth = groupWidth / 2.0;

        java.time.format.DateTimeFormatter dateFmt = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yy");

        String savingsColor = "#10b981"; // Emerald green
        String investColor = "#6366f1";  // Indigo blue

        for (int i = 0; i < nIntervals; i++) {
            var decomp = decompositions.get(i);
            double groupX = paddingLeft + (i * slotWidth) + (slotWidth - groupWidth) / 2.0;

            double savVal = decomp.netBudgetSavings().doubleValue();
            double appVal = decomp.investmentAppreciation().doubleValue();

            double savHeight = Math.abs(savVal) / valRange * plotHeight;
            double savY = savVal >= 0 ? zeroY - savHeight : zeroY;

            double appHeight = Math.abs(appVal) / valRange * plotHeight;
            double appY = appVal >= 0 ? zeroY - appHeight : zeroY;

            String intervalLabel = decomp.startDate().format(dateFmt) + " bis " + decomp.endDate().format(dateFmt);
            String safeIntervalStr = intervalLabel.replace("'", "\\'");
            String formattedSavStr = formatAmount(decomp.netBudgetSavings()).replace("'", "\\'");
            String formattedAppStr = formatAmount(decomp.investmentAppreciation()).replace("'", "\\'");
            String formattedTotStr = formatAmount(decomp.totalDelta()).replace("'", "\\'");

            String mouseOver = String.format(
                "document.getElementById(&quot;growth-chart-hover-info&quot;).innerHTML=&quot;&lt;strong&gt;%s&lt;/strong&gt; | Gesamtzuwachs: &lt;strong&gt;%s&lt;/strong&gt; (&lt;span style=\\&quot;color:%s; font-weight:bold;\\&quot;&gt;Ersparnisse: %s&lt;/span&gt;, &lt;span style=\\&quot;color:%s; font-weight:bold;\\&quot;&gt;Wertsteigerung: %s&lt;/span&gt;)&quot;;",
                safeIntervalStr, formattedTotStr, savingsColor, formattedSavStr, investColor, formattedAppStr
            );

            String mouseOut =
                "document.getElementById(&quot;growth-chart-hover-info&quot;).textContent=&quot;Fahren Sie mit der Maus über einen Zeitraum, um Details zu Einnahmen und Wertsteigerung anzuzeigen.&quot;;";

            String startStr = decomp.startDate().toString();
            String endStr = decomp.endDate().toString();

            svg.append(String.format(java.util.Locale.US,
                "<rect x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" fill=\"%s\" rx=\"2\" class=\"growth-bar\" data-start=\"%s\" data-end=\"%s\" style=\"cursor:pointer; transition: opacity 0.2s ease;\" onmouseover=\"%s\" onmouseout=\"%s\">",
                groupX, savY, barWidth, Math.max(1.0, savHeight), savingsColor, startStr, endStr, mouseOver, mouseOut));
            svg.append(String.format("<title>%s - Ersparnisse/Einnahmen: %s (Klicken für Einzelbuchungen)</title>", intervalLabel, formatAmount(decomp.netBudgetSavings())));
            svg.append("</rect>");

            svg.append(String.format(java.util.Locale.US,
                "<rect x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" fill=\"%s\" rx=\"2\" class=\"growth-bar\" data-start=\"%s\" data-end=\"%s\" style=\"cursor:pointer; transition: opacity 0.2s ease;\" onmouseover=\"%s\" onmouseout=\"%s\">",
                groupX + barWidth, appY, barWidth, Math.max(1.0, appHeight), investColor, startStr, endStr, mouseOver, mouseOut));
            svg.append(String.format("<title>%s - Wertsteigerung/Investitionen: %s (Klicken für Einzelbuchungen)</title>", intervalLabel, formatAmount(decomp.investmentAppreciation())));
            svg.append("</rect>");

            if (nIntervals <= 10 || i % Math.max(1, nIntervals / 5) == 0 || i == nIntervals - 1) {
                svg.append(String.format(java.util.Locale.US,
                        "<text x=\"%.1f\" y=\"%d\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"10\" text-anchor=\"middle\">%s</text>",
                        groupX + groupWidth / 2.0, height - 15, decomp.endDate().format(dateFmt)));
            }
        }

        svg.append("</svg>");

        Div svgWrapper = new Div();
        svgWrapper.getElement().setProperty("innerHTML", svg.toString());

        svgWrapper.getElement().addEventListener("click", e -> {
            String sDate = e.getEventData().get("event.target.dataset.start").asString();
            String eDate = e.getEventData().get("event.target.dataset.end").asString();
            if (sDate != null && eDate != null && !sDate.isEmpty() && !eDate.isEmpty()) {
                try {
                    LocalDate start = LocalDate.parse(sDate);
                    LocalDate end = LocalDate.parse(eDate);
                    openGrowthIntervalDetailDialog(start, end);
                } catch (Exception ex) {
                    // ignore
                }
            }
        }).addEventData("event.target.dataset.start").addEventData("event.target.dataset.end");

        chartCard.add(svgWrapper);

        Div legend = new Div();
        legend.getStyle()
                .set("display", "flex")
                .set("gap", "24px")
                .set("justify-content", "center")
                .set("margin-top", "12px");

        Div legendSav = new Div();
        legendSav.getStyle().set("display", "flex").set("align-items", "center").set("gap", "8px");
        Span badgeSav = new Span();
        badgeSav.getStyle().set("width", "12px").set("height", "12px").set("border-radius", "3px").set("background-color", savingsColor);
        Span nameSav = new Span("Einnahmen / Netto-Ersparnisse (CSV)");
        nameSav.getStyle().set("font-size", "0.9rem");
        legendSav.add(badgeSav, nameSav);

        Div legendApp = new Div();
        legendApp.getStyle().set("display", "flex").set("align-items", "center").set("gap", "8px");
        Span badgeApp = new Span();
        badgeApp.getStyle().set("width", "12px").set("height", "12px").set("border-radius", "3px").set("background-color", investColor);
        Span nameApp = new Span("Wertsteigerung / Investitionen");
        nameApp.getStyle().set("font-size", "0.9rem");
        legendApp.add(badgeApp, nameApp);

        legend.add(legendSav, legendApp);
        chartCard.add(legend);

        return chartCard;
    }

    private void openGrowthIntervalDetailDialog(LocalDate startDate, LocalDate endDate) {
        Dialog dialog = new Dialog();
        dialog.setWidth("900px");
        dialog.setMaxWidth("95vw");

        java.time.format.DateTimeFormatter dFmt = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String titleStr = "Einzelbuchungen im Zeitraum: " + startDate.format(dFmt) + " bis " + endDate.format(dFmt);
        H3 header = new H3(titleStr);
        header.getStyle().set("margin-top", "0").set("margin-bottom", "16px");

        List<DataService.BudgetTransactionDto> transactions = service.getBudgetTransactionsForInterval(startDate, endDate);

        Span infoSpan = new Span();
        if (transactions.isEmpty()) {
            infoSpan.setText("Keine CSV-Budget-Einzelbuchungen in diesem Zeitraum vorhanden.");
        } else {
            infoSpan.setText("CSV-Budget-Einzelbuchungen (" + transactions.size() + " Einträge):");
        }
        infoSpan.getStyle().set("font-weight", "bold").set("font-size", "1.05rem").set("margin-bottom", "16px");

        Grid<DataService.BudgetTransactionDto> detailGrid = new Grid<>();
        detailGrid.setHeight("380px");
        detailGrid.setWidthFull();
        detailGrid.addColumn(dto -> dto.date() != null ? dto.date().format(dFmt) : "")
                .setHeader("Datum")
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setSortable(true);
        detailGrid.addColumn(DataService.BudgetTransactionDto::type)
                .setHeader("Typ")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(dto -> formatAmount(dto.amount()))
                .setHeader("Betrag")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(DataService.BudgetTransactionDto::category)
                .setHeader("Kategorie")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(DataService.BudgetTransactionDto::person)
                .setHeader("Person")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(dto -> dto.description() != null ? dto.description() : "")
                .setHeader("Beschreibung")
                .setAutoWidth(true)
                .setFlexGrow(1);
        detailGrid.setItems(transactions);

        Button closeBtn = new Button("Schließen", e -> dialog.close());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        closeBtn.getStyle().set("margin-top", "16px");

        VerticalLayout dlgLayout = new VerticalLayout(header, infoSpan, detailGrid, closeBtn);
        dlgLayout.setPadding(false);
        dlgLayout.setSpacing(true);

        dialog.add(dlgLayout);
        dialog.open();
    }

    private String formatCompactAmount(BigDecimal amount) {
        if (amount == null) return "0 €";
        double d = amount.doubleValue();
        if (d >= 1_000_000) {
            return String.format(java.util.Locale.GERMANY, "%.1f Mio €", d / 1_000_000);
        } else if (d >= 10_000) {
            return String.format(java.util.Locale.GERMANY, "%.0f T€", d / 1_000);
        } else {
            return String.format(java.util.Locale.GERMANY, "%.0f €", d);
        }
    }

    private Div createCard(String title, String value, String background) {
        Div card = new Div();
        card.addClassName("metric-card");
        card.getStyle()
                .set("background", background)
                .set("width", "240px")
                .set("color", "white");

        H3 t = new H3(title);
        t.getStyle().set("margin", "0").set("font-size", "1.1rem").set("opacity", "0.9");
        Paragraph v = new Paragraph(value);
        v.getStyle().set("margin", "10px 0 0 0").set("font-size", "1.8rem").set("font-weight", "bold");

        card.add(t, v);
        return card;
    }

    private Component createDashboardContent() {
        dashboardContainer.setWidth("100%");
        dashboardContainer.getStyle().set("display", "flex").set("flex-direction", "column").set("align-items", "center");
        return dashboardContainer;
    }

    private Component createEingabeTabContent(String username, Grid<DataService.DateSummaryDto> datesGrid, DatePicker datePicker, VerticalLayout formRowsContainer) {
        HorizontalLayout mainSplit = new HorizontalLayout();
        mainSplit.setSizeFull();
        mainSplit.setSpacing(true);

        // Left Column: History
        VerticalLayout leftCol = new VerticalLayout();
        leftCol.setWidth("40%");
        leftCol.setSpacing(true);
        leftCol.add(new H3("Historische Einträge"));

        datesGrid.addColumn(DataService.DateSummaryDto::date).setHeader("Datum");
        datesGrid.addColumn(r -> formatAmount(r.totalAmount())).setHeader("Gesamt");

        datesGrid.addComponentColumn(item -> {
            Button deleteBtn = new Button("Löschen", e -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.setHeaderTitle("Einträge löschen?");
                confirmDialog.add(new Paragraph("Möchtest du alle Einträge für den " + item.date() + " wirklich unwiderruflich löschen?"));

                Button confirmBtn = new Button("Ja, löschen", event -> {
                    service.deleteEntriesForDate(username, item.date());
                    refreshData();
                    confirmDialog.close();
                    Notification.show("Einträge gelöscht.", 2000, Notification.Position.TOP_CENTER);
                });
                confirmBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

                Button cancelBtn = new Button("Abbrechen", event -> confirmDialog.close());

                confirmDialog.getFooter().add(cancelBtn, confirmBtn);
                confirmDialog.open();
            });
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return deleteBtn;
        }).setHeader("Aktion");

        datesGrid.setWidthFull();
        datesGrid.setAllRowsVisible(true);

        leftCol.add(datesGrid);

        // Right Column: Form
        VerticalLayout rightCol = new VerticalLayout();
        rightCol.setWidth("60%");
        rightCol.setSpacing(true);
        rightCol.addClassName("glass-panel");

        rightCol.add(new H3("Werte eintragen / bearbeiten"));

        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(e -> {
            rebuildFormRows(username, e.getValue(), formRowsContainer);
        });

        formRowsContainer.setWidthFull();
        formRowsContainer.setSpacing(true);
        formRowsContainer.setPadding(false);

        Button saveBtn = new Button("Speichern", e -> {
            saveFormValues(username, datePicker.getValue(), formRowsContainer);
        });
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        rightCol.add(datePicker, formRowsContainer, saveBtn);

        mainSplit.add(leftCol, rightCol);

        // When selecting an entry in the grid, load it into the form
        datesGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                datePicker.setValue(event.getValue().date());
            }
        });

        return mainSplit;
    }

    private void rebuildFormRows(String username, LocalDate date, VerticalLayout container) {
        container.removeAll();
        if (date == null) {
            return;
        }

        List<DataService.InstituteDto> institutes = service.getInstitutes();
        List<String> categories = service.getCategories().stream().map(DataService.CategoryDto::name).collect(Collectors.toList());

        // Get existing entries for this specific date
        List<DataService.DateEntryDto> currentEntries = service.getEntriesForDate(username, date);

        List<DataService.DateEntryDto> targetEntriesList;
        if (!currentEntries.isEmpty()) {
            targetEntriesList = currentEntries;
        } else {
            LocalDate lastDate = service.getMostRecentEntryDateBefore(username, date);
            if (lastDate != null) {
                targetEntriesList = service.getEntriesForDate(username, lastDate);
            } else {
                targetEntriesList = List.of();
            }
        }

        java.util.Set<String> renderedInstitutes = new java.util.HashSet<>();

        for (var entry : targetEntriesList) {
            renderedInstitutes.add(entry.instituteName());

            BigDecimal referenceAmount = null;
            String referenceCategory = entry.categoryName();

            if (!currentEntries.isEmpty()) {
                DataService.LastEntryDto last = service.getLastEntry(username, entry.instituteName(), entry.categoryName(), date.minusDays(1));
                if (last != null) {
                    referenceAmount = last.amount();
                }
            } else {
                referenceAmount = entry.amount();
            }

            HorizontalLayout row = createEntryRow(entry.instituteName(), categories, referenceAmount, referenceCategory, !currentEntries.isEmpty() ? entry.amount() : null, entry.categoryName());
            container.add(row);
        }

        for (var inst : institutes) {
            if (!renderedInstitutes.contains(inst.name())) {
                DataService.LastEntryDto last = service.getLastEntry(username, inst.name(), date);
                BigDecimal referenceAmount = (last != null) ? last.amount() : null;
                String referenceCategory = (last != null) ? last.categoryName() : null;

                HorizontalLayout row = createEntryRow(inst.name(), categories, referenceAmount, referenceCategory, null, referenceCategory);
                container.add(row);
            }
        }
    }

    private HorizontalLayout createEntryRow(String instituteName, List<String> categories, BigDecimal referenceAmount, String referenceCategory, BigDecimal currentValue, String currentCategory) {
        HorizontalLayout row = new HorizontalLayout();
        row.setAlignItems(Alignment.CENTER);
        row.setSpacing(true);
        row.setWidthFull();

        Span nameLabel = new Span(instituteName);
        nameLabel.getStyle().set("font-weight", "bold").set("width", "180px");

        Span referenceSpan = new Span();
        if (referenceAmount != null) {
            String catPart = referenceCategory != null ? " (" + referenceCategory + ")" : "";
            referenceSpan.setText("Zuletzt: " + formatAmount(referenceAmount) + catPart);
        } else {
            referenceSpan.setText("Kein vorheriger Wert");
        }
        referenceSpan.getStyle().set("color", "var(--lumo-secondary-text-color)").set("width", "220px");

        BigDecimalField amountField = new BigDecimalField();
        amountField.setPlaceholder("Neuer Wert (€)");
        amountField.setWidth("150px");
        if (currentValue != null) {
            amountField.setValue(currentValue);
        }
        amountField.getElement().setAttribute("data-inst", instituteName);

        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.setItems(categories);
        categoryCombo.setPlaceholder("Kategorie");
        categoryCombo.setWidth("150px");
        if (currentCategory != null) {
            categoryCombo.setValue(currentCategory);
        } else if (referenceCategory != null) {
            categoryCombo.setValue(referenceCategory);
        }

        row.add(nameLabel, referenceSpan, amountField, categoryCombo);
        return row;
    }

    @SuppressWarnings("unchecked")
    private void saveFormValues(String username, LocalDate date, VerticalLayout container) {
        if (date == null) {
            Notification.show("Bitte ein Datum auswählen!", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        List<DataService.DateEntryDto> entries = new java.util.ArrayList<>();

        for (int i = 0; i < container.getComponentCount(); i++) {
            Component child = container.getComponentAt(i);
            if (child instanceof HorizontalLayout row) {
                BigDecimalField amtField = null;
                ComboBox<String> catCombo = null;
                String instName = null;

                for (int j = 0; j < row.getComponentCount(); j++) {
                    Component rowChild = row.getComponentAt(j);
                    if (rowChild instanceof BigDecimalField field) {
                        amtField = field;
                        instName = field.getElement().getAttribute("data-inst");
                    } else if (rowChild instanceof ComboBox<?> combo) {
                        catCombo = (ComboBox<String>) combo;
                    }
                }

                if (instName != null && amtField != null && catCombo != null) {
                    BigDecimal amount = amtField.getValue();
                    String category = catCombo.getValue();

                    if (amount != null) {
                        if (amount.compareTo(BigDecimal.ZERO) < 0) {
                            Notification.show("Beträge dürfen nicht negativ sein!", 3000, Notification.Position.TOP_CENTER)
                                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                            return;
                        }
                        if (category == null || category.isEmpty()) {
                            Notification.show("Bitte Kategorie für " + instName + " auswählen!", 3000, Notification.Position.TOP_CENTER)
                                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                            return;
                        }
                        entries.add(new DataService.DateEntryDto(instName, category, amount));
                    }
                }
            }
        }

        service.saveEntriesForDate(username, date, entries);
        refreshData();
        Notification.show("Einträge erfolgreich gespeichert!", 2000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private Component createForecastContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        H2 header = new H2("Vermögensprognose");
        layout.add(header);

        VerticalLayout calcForm = new VerticalLayout();
        calcForm.setAlignItems(Alignment.CENTER);
        calcForm.setMaxWidth("400px");
        calcForm.addClassName("glass-panel");

        BigDecimalField savingsField = new BigDecimalField("Monatliche Sparrate (€)");
        savingsField.setValue(new BigDecimal("500"));

        NumberField returnField = new NumberField("Erwartete jährliche Rendite (%)");
        returnField.setValue(5.0);

        IntegerField periodField = new IntegerField("Zeitraum (Jahre)");
        periodField.setValue(10);

        Div resultContainer = new Div();
        resultContainer.getStyle().set("margin-top", "20px").set("font-size", "1.3rem").set("font-weight", "bold");

        Button calcBtn = new Button("Berechnen", e -> {
            boolean hasError = false;

            if (savingsField.getValue() == null || savingsField.getValue().compareTo(BigDecimal.ZERO) < 0) {
                savingsField.setInvalid(true);
                savingsField.setErrorMessage("Sparrate darf nicht negativ oder leer sein");
                hasError = true;
            } else {
                savingsField.setInvalid(false);
            }

            if (returnField.getValue() == null || returnField.getValue() < 0) {
                returnField.setInvalid(true);
                returnField.setErrorMessage("Rendite darf nicht negativ oder leer sein");
                hasError = true;
            } else {
                returnField.setInvalid(false);
            }

            if (periodField.getValue() == null || periodField.getValue() <= 0) {
                periodField.setInvalid(true);
                periodField.setErrorMessage("Zeitraum muss größer als 0 sein");
                hasError = true;
            } else {
                periodField.setInvalid(false);
            }

            if (hasError) {
                Notification notif = Notification.show("Bitte korrigieren Sie die ungültigen Parameter.", 3000, Notification.Position.MIDDLE);
                notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                resultContainer.setText("");
                return;
            }

            BigDecimal currentWealth = service.getCurrentWealth(null);

            double rate = returnField.getValue() / 100.0 / 12.0;
            int months = periodField.getValue() * 12;
            BigDecimal monthlySavings = savingsField.getValue();

            double futureValue = currentWealth.doubleValue();
            for (int i = 0; i < months; i++) {
                futureValue = (futureValue + monthlySavings.doubleValue()) * (1 + rate);
            }

            BigDecimal roundedResult = BigDecimal.valueOf(futureValue).setScale(2, RoundingMode.HALF_UP);
            resultContainer.setText("Geschätztes Endvermögen: " + roundedResult.toString() + " €");
        });
        calcBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        calcForm.add(savingsField, returnField, periodField, calcBtn, resultContainer);
        layout.add(calcForm);

        return layout;
    }

    private Component createSettingsContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);

        HorizontalLayout lists = new HorizontalLayout();
        lists.setSizeFull();

        // Institutes management
        VerticalLayout instLayout = new VerticalLayout();
        instLayout.addClassName("glass-panel");
        instLayout.add(new H3("Institute verwalten"));
        TextField newInstField = new TextField();
        newInstField.setPlaceholder("Neues Institut...");

        Grid<DataService.InstituteDto> instGrid = new Grid<>();
        instGrid.addColumn(DataService.InstituteDto::name).setHeader("Name");
        instGrid.addComponentColumn(item -> {
            Button delBtn = new Button("Löschen", e -> {
                if (service.isInstituteInUse(item.id())) {
                    Notification.show("Institut wird verwendet.", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                service.deleteInstitute(item.id());
                instGrid.setItems(service.getInstitutes());
                Notification.show("Institut entfernt.");
            });
            delBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return delBtn;
        });

        Button addInstBtn = new Button("Hinzufügen", e -> {
            if (newInstField.isEmpty()) return;
            String name = newInstField.getValue().trim();
            if (service.instituteExists(name)) {
                Notification.show("Institut existiert bereits.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            service.addInstitute(name);
            newInstField.clear();
            instGrid.setItems(service.getInstitutes());
            Notification.show("Institut hinzugefügt.");
        });
        instLayout.add(new HorizontalLayout(newInstField, addInstBtn), instGrid);
        instGrid.setItems(service.getInstitutes());

        // Categories management
        VerticalLayout catLayout = new VerticalLayout();
        catLayout.addClassName("glass-panel");
        catLayout.add(new H3("Kategorien verwalten"));
        TextField newCatField = new TextField();
        newCatField.setPlaceholder("Neue Kategorie...");

        Grid<DataService.CategoryDto> catGrid = new Grid<>();
        catGrid.addColumn(DataService.CategoryDto::name).setHeader("Name");
        catGrid.addComponentColumn(item -> {
            Button delBtn = new Button("Löschen", e -> {
                if (service.isCategoryInUse(item.id())) {
                    Notification.show("Kategorie wird verwendet.", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                service.deleteCategory(item.id());
                catGrid.setItems(service.getCategories());
                Notification.show("Kategorie entfernt.");
            });
            delBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return delBtn;
        });

        Button addCatBtn = new Button("Hinzufügen", e -> {
            if (newCatField.isEmpty()) return;
            String name = newCatField.getValue().trim();
            if (service.categoryExists(name)) {
                Notification.show("Kategorie existiert bereits.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            service.addCategory(name);
            newCatField.clear();
            catGrid.setItems(service.getCategories());
            Notification.show("Kategorie hinzugefügt.");
        });
        catLayout.add(new HorizontalLayout(newCatField, addCatBtn), catGrid);
        catGrid.setItems(service.getCategories());

        lists.add(instLayout, catLayout);
        layout.add(lists);

        return layout;
    }

    private Component createCsvImportContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.getStyle()
                .set("overflow-y", "auto")
                .set("padding-bottom", "80px")
                .set("max-width", "1100px")
                .set("margin", "0 auto");
        layout.setSpacing(true);
        layout.setPadding(true);

        H2 header = new H2("Budget CSV Import");
        layout.add(header);

        // Top Metadata Header Card (BR-012)
        Div metaCard = new Div();
        metaCard.addClassName("glass-panel");
        metaCard.getStyle()
                .set("width", "100%")
                .set("padding", "16px")
                .set("border-radius", "12px")
                .set("margin-bottom", "16px");

        Span metaText = new Span();
        metaText.getStyle().set("font-size", "1.1rem").set("font-weight", "500");

        Runnable updateMetaDisplay = () -> {
            DataService.ImportMetadataDto meta = service.getLatestImportMetadata();
            if (meta != null && meta.filename() != null) {
                String formattedTime = meta.uploadTimestamp() != null ?
                        meta.uploadTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "";
                metaText.setText("Zuletzt hochgeladene Datei: " + meta.filename() + " (" + formattedTime + ")");
            } else {
                metaText.setText("Noch keine CSV-Datei hochgeladen.");
            }
        };
        metaCard.add(metaText);
        layout.add(metaCard);

        // Upload Component
        com.vaadin.flow.component.upload.Upload upload = new com.vaadin.flow.component.upload.Upload();
        com.vaadin.flow.component.upload.receivers.MemoryBuffer buffer = new com.vaadin.flow.component.upload.receivers.MemoryBuffer();
        upload.setReceiver(buffer);
        upload.setAcceptedFileTypes(".csv");
        upload.setDropLabel(new Span("Budget CSV Datei hierher ziehen oder auswählen"));

        Grid<DataService.BudgetTransactionDto> budgetGrid = new Grid<>();
        budgetGrid.setWidthFull();
        budgetGrid.setMinHeight("400px");
        budgetGrid.getStyle().set("margin-bottom", "40px");
        budgetGrid.addColumn(dto -> dto.date() != null ? dto.date().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "").setHeader("Datum").setSortable(true);
        budgetGrid.addColumn(DataService.BudgetTransactionDto::type).setHeader("Typ");
        budgetGrid.addColumn(dto -> formatAmount(dto.amount())).setHeader("Betrag");
        budgetGrid.addColumn(DataService.BudgetTransactionDto::category).setHeader("Kategorie");
        budgetGrid.addColumn(DataService.BudgetTransactionDto::person).setHeader("Person");
        budgetGrid.addColumn(DataService.BudgetTransactionDto::description).setHeader("Beschreibung");

        Runnable refreshBudgetGrid = () -> budgetGrid.setItems(service.getRecentBudgetTransactions(100));

        layout.addAttachListener(e -> {
            updateMetaDisplay.run();
            refreshBudgetGrid.run();
        });
        updateMetaDisplay.run();
        refreshBudgetGrid.run();

        upload.addSucceededListener(event -> {
            String filename = event.getFileName();
            try (java.io.InputStream is = buffer.getInputStream();
                 java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8))) {
                
                List<String[]> csvRows = new java.util.ArrayList<>();
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue; // Skip header row
                    }
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(";");
                    csvRows.add(parts);
                }

                DataService.CsvImportResult result = service.importBudgetCsvRows(filename, csvRows);
                updateMetaDisplay.run();
                refreshBudgetGrid.run();

                Notification.show(String.format("Import abgeschlossen: %d neue Eintr%ss importiert, %d bestehende Eintr%ss übersprungen.",
                        result.importedCount(), result.importedCount() == 1 ? "ag" : "äge",
                        result.skippedCount(), result.skippedCount() == 1 ? "ag" : "äge"),
                        4000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (Exception ex) {
                Notification.show("Fehler beim Importieren der CSV-Datei: " + ex.getMessage(), 4000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        Div uploadWrapper = new Div(upload);
        uploadWrapper.getStyle().set("margin-bottom", "20px");
        layout.add(uploadWrapper);

        H3 previewHeader = new H3("Zuletzt importierte Daten (Vorschau max. 100 Einträge)");
        layout.add(previewHeader, budgetGrid);

        return layout;
    }

    private Component createBudgetAnalysisContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.getStyle()
                .set("overflow-y", "auto")
                .set("padding-bottom", "80px")
                .set("max-width", "1100px")
                .set("margin", "0 auto");
        layout.setSpacing(true);
        layout.setPadding(true);

        H2 header = new H2("Budget Auswertung");
        layout.add(header);

        List<DataService.BudgetMonthlyCategorySumDto> monthlySums = service.getBudgetMonthlyCategorySums();
        if (monthlySums == null || monthlySums.isEmpty()) {
            Div emptyMsg = new Div();
            emptyMsg.setText("Keine Budget-Daten vorhanden. Bitte laden Sie zuerst eine CSV-Datei im Tab 'CSV Import' hoch.");
            emptyMsg.getStyle()
                    .set("margin-top", "40px")
                    .set("font-size", "1.1rem")
                    .set("color", "var(--lumo-secondary-text-color)");
            layout.add(emptyMsg);
            return layout;
        }

        // Group by category name
        Map<String, List<DataService.BudgetMonthlyCategorySumDto>> byCategory = monthlySums.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        DataService.BudgetMonthlyCategorySumDto::categoryName,
                        java.util.LinkedHashMap::new,
                        java.util.stream.Collectors.toList()
                ));

        String[] categoryColors = {
            "#6366f1", // Indigo
            "#10b981", // Emerald
            "#f59e0b", // Amber
            "#ec4899", // Pink
            "#8b5cf6", // Purple
            "#06b6d4", // Cyan
            "#3b82f6", // Blue
            "#f97316", // Orange
            "#14b8a6", // Teal
            "#eab308"  // Yellow
        };

        int catColorIdx = 0;
        java.time.format.DateTimeFormatter ymFmt = java.time.format.DateTimeFormatter.ofPattern("MM.yy");

        for (var entry : byCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<DataService.BudgetMonthlyCategorySumDto> categoryItems = entry.getValue();
            categoryItems.sort(java.util.Comparator.comparing(DataService.BudgetMonthlyCategorySumDto::yearMonth));

            String themeColor = categoryColors[catColorIdx % categoryColors.length];
            catColorIdx++;

            Div card = new Div();
            card.addClassName("glass-panel");
            card.getStyle()
                    .set("margin-top", "24px")
                    .set("padding", "24px")
                    .set("border-radius", "16px")
                    .set("width", "100%");

            H3 cardTitle = new H3(categoryName);
            cardTitle.getStyle().set("margin-top", "0").set("margin-bottom", "16px").set("color", themeColor);
            card.add(cardTitle);

            double maxVal = categoryItems.stream()
                    .map(DataService.BudgetMonthlyCategorySumDto::totalAmount)
                    .mapToDouble(BigDecimal::doubleValue)
                    .max()
                    .orElse(1.0);
            if (maxVal <= 0) maxVal = 1.0;

            int width = 800;
            int height = 260;
            int paddingLeft = 70;
            int paddingRight = 30;
            int paddingTop = 30;
            int paddingBottom = 45;

            int plotWidth = width - paddingLeft - paddingRight;
            int plotHeight = height - paddingTop - paddingBottom;
            int nMonths = categoryItems.size();

            StringBuilder svg = new StringBuilder();
            svg.append(String.format("<svg viewBox=\"0 0 %d %d\" style=\"width: 100%%; height: auto; font-family: var(--lumo-font-family);\">", width, height));

            // Grid lines for Y-axis (0, 50%, 100% of maxVal)
            double[] ySteps = {0, maxVal * 0.5, maxVal};
            for (double val : ySteps) {
                double y = paddingTop + plotHeight - (val / maxVal * plotHeight);
                svg.append(String.format(java.util.Locale.US, "<line x1=\"%d\" y1=\"%.1f\" x2=\"%d\" y2=\"%.1f\" stroke=\"var(--lumo-contrast-10pct)\" stroke-dasharray=\"4,4\"/>",
                        paddingLeft, y, width - paddingRight, y));
                svg.append(String.format(java.util.Locale.US, "<text x=\"%d\" y=\"%.1f\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"end\" dominant-baseline=\"middle\">%s</text>",
                        paddingLeft - 10, y, formatCompactAmount(BigDecimal.valueOf(val))));
            }

            // Draw bars for each month
            double slotWidth = (double) plotWidth / nMonths;
            double barWidth = Math.max(8.0, Math.min(36.0, slotWidth * 0.6));

            for (int i = 0; i < nMonths; i++) {
                var item = categoryItems.get(i);
                double val = item.totalAmount().doubleValue();
                double barHeight = (val / maxVal) * plotHeight;
                double x = paddingLeft + (i * slotWidth) + (slotWidth - barWidth) / 2.0;
                double y = paddingTop + plotHeight - barHeight;

                String formattedMonth = item.yearMonth().format(ymFmt);
                String formattedAmt = formatAmount(item.totalAmount()).replace("'", "\\'");
                String safeCat = categoryName.replace("'", "\\'");

                String barTitle = String.format("%s (%s): %s - Klicken für Einzelbuchungen", categoryName, formattedMonth, formatAmount(item.totalAmount()));

                // SVG rect with click trigger attribute
                String ymStr = item.yearMonth().toString(); // e.g. 2026-07
                String safeCatAttr = categoryName.replace("\"", "&quot;");

                svg.append(String.format(java.util.Locale.US,
                    "<rect x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" fill=\"%s\" rx=\"4\" class=\"budget-bar\" data-cat=\"%s\" data-ym=\"%s\" style=\"cursor:pointer; transition: opacity 0.2s ease;\" onmouseover=\"this.style.opacity='0.75';\" onmouseout=\"this.style.opacity='1';\">",
                    x, y, barWidth, barHeight, themeColor, safeCatAttr, ymStr));
                svg.append(String.format("<title>%s</title>", barTitle));
                svg.append("</rect>");

                // X-axis label
                if (nMonths <= 12 || i % Math.max(1, nMonths / 8) == 0 || i == nMonths - 1) {
                    svg.append(String.format(java.util.Locale.US,
                            "<text x=\"%.1f\" y=\"%d\" fill=\"var(--lumo-secondary-text-color)\" font-size=\"11\" text-anchor=\"middle\">%s</text>",
                            x + barWidth / 2.0, height - 15, formattedMonth));
                }
            }

            svg.append("</svg>");

            Div svgWrapper = new Div();
            svgWrapper.getElement().setProperty("innerHTML", svg.toString());

            // Add click listener via Vaadin element event for SVG rects
            final String finalCatName = categoryName;
            svgWrapper.getElement().addEventListener("click", e -> {
                String clickedCat = e.getEventData().get("event.target.dataset.cat").asString();
                String clickedYm = e.getEventData().get("event.target.dataset.ym").asString();
                if (clickedCat != null && clickedYm != null && !clickedCat.isEmpty() && !clickedYm.isEmpty()) {
                    try {
                        java.time.YearMonth ym = java.time.YearMonth.parse(clickedYm);
                        openBudgetMonthDetailDialog(clickedCat, ym);
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            }).addEventData("event.target.dataset.cat").addEventData("event.target.dataset.ym");

            card.add(svgWrapper);

            layout.add(card);
        }

        return layout;
    }

    private void openBudgetMonthDetailDialog(String categoryName, java.time.YearMonth yearMonth) {
        Dialog dialog = new Dialog();
        dialog.setWidth("900px");
        dialog.setMaxWidth("95vw");

        String formattedYm = yearMonth.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy", java.util.Locale.GERMANY));
        H3 header = new H3("Einzelbuchungen: " + categoryName + " (" + formattedYm + ")");
        header.getStyle().set("margin-top", "0").set("margin-bottom", "16px");

        List<DataService.BudgetTransactionDto> transactions = service.getBudgetTransactionsForCategoryAndMonth(categoryName, yearMonth);

        BigDecimal sum = transactions.stream()
                .map(DataService.BudgetTransactionDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Span totalSpan = new Span("Monatssumme (" + transactions.size() + " Buchungen): " + formatAmount(sum));
        totalSpan.getStyle().set("font-weight", "bold").set("font-size", "1.05rem").set("margin-bottom", "16px");

        Grid<DataService.BudgetTransactionDto> detailGrid = new Grid<>();
        detailGrid.setHeight("380px");
        detailGrid.setWidthFull();
        detailGrid.addColumn(dto -> dto.date() != null ? dto.date().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "")
                .setHeader("Datum")
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setSortable(true);
        detailGrid.addColumn(DataService.BudgetTransactionDto::type)
                .setHeader("Typ")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(dto -> formatAmount(dto.amount()))
                .setHeader("Betrag")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(DataService.BudgetTransactionDto::person)
                .setHeader("Person")
                .setAutoWidth(true)
                .setFlexGrow(0);
        detailGrid.addColumn(dto -> dto.description() != null ? dto.description() : "")
                .setHeader("Beschreibung")
                .setAutoWidth(true)
                .setFlexGrow(1);
        detailGrid.setItems(transactions);

        Button closeBtn = new Button("Schließen", e -> dialog.close());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        closeBtn.getStyle().set("margin-top", "16px");

        VerticalLayout dlgLayout = new VerticalLayout(header, totalSpan, detailGrid, closeBtn);
        dlgLayout.setPadding(false);
        dlgLayout.setSpacing(true);

        dialog.add(dlgLayout);
        dialog.open();
    }
}
