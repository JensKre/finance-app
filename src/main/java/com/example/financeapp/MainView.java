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
        Tab settingsTab = new Tab("Einstellungen");

        tabs.add(dashboardTab, eingabeTab, forecastTab, settingsTab);
        tabs.setWidth("100%");
        add(tabs);

        // Create component contents
        tabComponentMap.put(dashboardTab, createDashboardContent());
        tabComponentMap.put(eingabeTab, createEingabeTabContent(null, datesGrid, datePicker, formRowsContainer));
        tabComponentMap.put(forecastTab, createForecastContent());
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
        }
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
        chartTitle.getStyle().set("margin-top", "0").set("margin-bottom", "20px");
        chartCard.add(chartTitle);

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

        record ChartPoint(double x, double y, LocalDate date, BigDecimal amount) {}
        List<ChartPoint> points = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            double x = paddingLeft + (n > 1 ? (double) i / (n - 1) * plotWidth : plotWidth / 2.0);
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
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yy");
        for (int i = 0; i < points.size(); i++) {
            ChartPoint p = points.get(i);
            svg.append(String.format(java.util.Locale.US, "<circle cx=\"%.1f\" cy=\"%.1f\" r=\"5\" fill=\"#6366f1\" stroke=\"#ffffff\" stroke-width=\"2\">", p.x, p.y));
            svg.append(String.format("<title>%s: %s</title>", p.date.format(dateFmt), formatAmount(p.amount)));
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
}
