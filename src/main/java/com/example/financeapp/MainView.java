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
    private final Grid<DataService.TransactionDto> jensGrid = new Grid<>();
    private final Grid<DataService.TransactionDto> annikaGrid = new Grid<>();
    private final ComboBox<String> jensInstCombo = new ComboBox<>("Institut");
    private final ComboBox<String> jensCatCombo = new ComboBox<>("Kategorie");
    private final ComboBox<String> annikaInstCombo = new ComboBox<>("Institut");
    private final ComboBox<String> annikaCatCombo = new ComboBox<>("Kategorie");

    @Autowired
    public MainView(DataService service) {
        this.service = service;

        // Force Dark Mode Lumo Theme
        UI.getCurrent().getElement().setAttribute("theme", Lumo.DARK);

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Couples Finance Tracker");
        title.getStyle().set("margin-top", "10px").set("background", "linear-gradient(45deg, #1572A1, #9ad0ec)").set("-webkit-background-clip", "text").set("-webkit-text-fill-color", "transparent");
        add(title);

        // Tab setup
        Tab dashboardTab = new Tab("Dashboard");
        Tab jensTab = new Tab("Jens");
        Tab annikaTab = new Tab("Annika");
        Tab forecastTab = new Tab("Prognose");
        Tab settingsTab = new Tab("Einstellungen");

        tabs.add(dashboardTab, jensTab, annikaTab, forecastTab, settingsTab);
        tabs.setWidth("100%");
        add(tabs);

        // Create component contents
        tabComponentMap.put(dashboardTab, createDashboardContent());
        tabComponentMap.put(jensTab, createUserTabContent("Jens", jensGrid, jensInstCombo, jensCatCombo));
        tabComponentMap.put(annikaTab, createUserTabContent("Annika", annikaGrid, annikaInstCombo, annikaCatCombo));
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

    private void refreshData() {
        // Refresh grids
        jensGrid.setItems(service.getTransactions("Jens"));
        annikaGrid.setItems(service.getTransactions("Annika"));

        // Refresh combo box items
        List<String> institutes = service.getInstitutes().stream().map(DataService.InstituteDto::name).collect(Collectors.toList());
        List<String> categories = service.getCategories().stream().map(DataService.CategoryDto::name).collect(Collectors.toList());
        jensInstCombo.setItems(institutes);
        jensCatCombo.setItems(categories);
        annikaInstCombo.setItems(institutes);
        annikaCatCombo.setItems(categories);

        // Refresh dashboard numbers
        dashboardContainer.removeAll();
        BigDecimal jensTotal = service.getTransactions("Jens").stream()
                .map(DataService.TransactionDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal annikaTotal = service.getTransactions("Annika").stream()
                .map(DataService.TransactionDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal combinedTotal = jensTotal.add(annikaTotal);

        Div cards = new Div();
        cards.getStyle().set("display", "flex").set("gap", "20px").set("justify-content", "center").set("flex-wrap", "wrap").set("margin-top", "20px");

        cards.add(createCard("Gesamtvermögen", formatAmount(combinedTotal), "linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)"));
        cards.add(createCard("Jens", formatAmount(jensTotal), "linear-gradient(135deg, #11998e 0%, #38ef7d 100%)"));
        cards.add(createCard("Annika", formatAmount(annikaTotal), "linear-gradient(135deg, #fc466b 0%, #3f5efb 100%)"));

        dashboardContainer.add(cards);

        // Simple listing of transactions in Dashboard
        Div details = new Div();
        details.getStyle().set("margin-top", "30px").set("width", "100%").set("max-width", "800px");
        details.add(new H3("Letzte Aktivitäten (Kombiniert)"));

        Grid<DataService.TransactionDto> combinedGrid = new Grid<>();
        combinedGrid.addColumn(DataService.TransactionDto::username).setHeader("Person");
        combinedGrid.addColumn(DataService.TransactionDto::date).setHeader("Datum");
        combinedGrid.addColumn(DataService.TransactionDto::institute).setHeader("Institut");
        combinedGrid.addColumn(DataService.TransactionDto::category).setHeader("Kategorie");
        combinedGrid.addColumn(r -> formatAmount(r.amount())).setHeader("Betrag");

        combinedGrid.setItems(service.getTransactions(null));
        combinedGrid.setAllRowsVisible(true);
        details.add(combinedGrid);
        dashboardContainer.add(details);
    }

    private Div createCard(String title, String value, String background) {
        Div card = new Div();
        card.getStyle()
                .set("background", background)
                .set("border-radius", "12px")
                .set("padding", "20px")
                .set("width", "220px")
                .set("box-shadow", "0 4px 15px rgba(0,0,0,0.3)")
                .set("text-align", "center")
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

    private Component createUserTabContent(String username, Grid<DataService.TransactionDto> grid, ComboBox<String> instCombo, ComboBox<String> catCombo) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setAlignItems(Alignment.CENTER);
        layout.setSpacing(true);

        H2 header = new H2(username + "'s Finanzverwaltung");
        layout.add(header);

        // Entry form
        HorizontalLayout form = new HorizontalLayout();
        form.setAlignItems(Alignment.END);
        form.setSpacing(true);
        form.getStyle().set("background", "#202020").set("padding", "15px").set("border-radius", "8px");

        BigDecimalField amountField = new BigDecimalField("Betrag (€)");
        amountField.setPlaceholder("z.B. 1500.00");

        DatePicker datePicker = new DatePicker("Datum");
        datePicker.setValue(LocalDate.now());

        Button submitBtn = new Button("Hinzufügen", e -> {
            if (amountField.getValue() == null || instCombo.getValue() == null || catCombo.getValue() == null || datePicker.getValue() == null) {
                Notification.show("Bitte fülle alle Felder aus!", 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            service.addTransaction(username, instCombo.getValue(), catCombo.getValue(), amountField.getValue(), datePicker.getValue());
            amountField.clear();
            instCombo.clear();
            catCombo.clear();
            datePicker.setValue(LocalDate.now());
            refreshData();
            Notification.show("Eintrag erfolgreich hinzugefügt!", 2000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        form.add(amountField, instCombo, catCombo, datePicker, submitBtn);
        layout.add(form);

        // Grid
        grid.addColumn(DataService.TransactionDto::date).setHeader("Datum");
        grid.addColumn(DataService.TransactionDto::institute).setHeader("Institut");
        grid.addColumn(DataService.TransactionDto::category).setHeader("Kategorie");
        grid.addColumn(r -> formatAmount(r.amount())).setHeader("Betrag");

        grid.addComponentColumn(item -> {
            Button deleteBtn = new Button("Löschen", e -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.setHeaderTitle("Eintrag löschen?");
                confirmDialog.add(new Paragraph("Möchtest du diesen Eintrag wirklich unwiderruflich löschen?"));

                Button confirmBtn = new Button("Ja, löschen", event -> {
                    service.deleteTransaction(item.id());
                    refreshData();
                    confirmDialog.close();
                    Notification.show("Eintrag gelöscht.", 2000, Notification.Position.TOP_CENTER);
                });
                confirmBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

                Button cancelBtn = new Button("Abbrechen", event -> confirmDialog.close());

                confirmDialog.getFooter().add(cancelBtn, confirmBtn);
                confirmDialog.open();
            });
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return deleteBtn;
        }).setHeader("Aktion");

        grid.setWidth("100%");
        grid.setMaxWidth("800px");
        grid.setAllRowsVisible(true);
        grid.getStyle().set("align-self", "center");
        layout.add(grid);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, grid);

        return layout;
    }

    private Component createForecastContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        H2 header = new H2("Vermögensprognose");
        layout.add(header);

        VerticalLayout calcForm = new VerticalLayout();
        calcForm.setAlignItems(Alignment.CENTER);
        calcForm.setMaxWidth("400px");
        calcForm.getStyle().set("background", "#202020").set("padding", "20px").set("border-radius", "8px");

        BigDecimalField savingsField = new BigDecimalField("Monatliche Sparrate (€)");
        savingsField.setValue(new BigDecimal("500"));

        NumberField returnField = new NumberField("Erwartete jährliche Rendite (%)");
        returnField.setValue(5.0);

        IntegerField periodField = new IntegerField("Zeitraum (Jahre)");
        periodField.setValue(10);

        Div resultContainer = new Div();
        resultContainer.getStyle().set("margin-top", "20px").set("font-size", "1.3rem").set("font-weight", "bold");

        Button calcBtn = new Button("Berechnen", e -> {
            BigDecimal currentWealth = service.getTransactions(null).stream()
                    .map(DataService.TransactionDto::amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

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
        catLayout.add(new H3("Kategorien verwalten"));
        TextField newCatField = new TextField();
        newCatField.setPlaceholder("Neue Kategorie...");

        Grid<DataService.CategoryDto> catGrid = new Grid<>();
        catGrid.addColumn(DataService.CategoryDto::name).setHeader("Name");
        catGrid.addComponentColumn(item -> {
            Button delBtn = new Button("Löschen", e -> {
                service.deleteCategory(item.id());
                catGrid.setItems(service.getCategories());
                Notification.show("Kategorie entfernt.");
            });
            delBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return delBtn;
        });

        Button addCatBtn = new Button("Hinzufügen", e -> {
            if (newCatField.isEmpty()) return;
            service.addCategory(newCatField.getValue());
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
