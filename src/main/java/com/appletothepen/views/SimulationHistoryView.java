package com.appletothepen.views;

import java.util.ArrayList;
import java.util.List;

import com.appletothepen.model.SampleData;
import com.appletothepen.model.SimulationRecord;
import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SimulationHistoryView extends VBox {

    private final ObservableList<SimulationRecord> allRecords =
            FXCollections.observableArrayList(SampleData.historyRecords());
    private final VBox rowsContainer = new VBox(10);
    private final ComboBox<String> statusFilter = new ComboBox<>();
    private final ComboBox<String> routeFilter = new ComboBox<>();

    public SimulationHistoryView() {
        getStyleClass().add("view-root");

        getChildren().add(new PageHeader("Simulation History and Interactive Replay Console"));

        Card card = new Card();
        card.getChildren().add(buildFilterRow());
        card.getChildren().add(buildColumnHeaderRow());
        card.getChildren().add(rowsContainer);
        VBox.setVgrow(card, Priority.ALWAYS);
        VBox.setVgrow(rowsContainer, Priority.ALWAYS);

        VBox contentWrap = new VBox(card);
        contentWrap.setPadding(new Insets(20));
        VBox.setVgrow(contentWrap, Priority.ALWAYS);
        getChildren().add(contentWrap);

        refreshRows();
    }

    private HBox buildFilterRow() {
        Label filtersLabel = new Label("FILTERS:");
        filtersLabel.getStyleClass().add("filters-label");

        ComboBox<String> dateRange = new ComboBox<>(FXCollections.observableArrayList(
                "Last 7 Days", "Last 30 Days", "All Time"));
        dateRange.setValue("Last 30 Days");
        dateRange.getStyleClass().add("filter-chip");

        statusFilter.setItems(FXCollections.observableArrayList("All Statuses", "Completed", "Aborted"));
        statusFilter.setValue("Completed");
        statusFilter.getStyleClass().add("filter-chip");
        statusFilter.setOnAction(e -> refreshRows());

        List<String> routes = new ArrayList<>();
        routes.add("All Routes");
        for (SimulationRecord record : allRecords) {
            if (!routes.contains(record.getPlannedRoute())) {
                routes.add(record.getPlannedRoute());
            }
        }
        routeFilter.setItems(FXCollections.observableArrayList(routes));
        routeFilter.setValue("All Routes");
        routeFilter.getStyleClass().add("filter-chip");
        routeFilter.setOnAction(e -> refreshRows());

        Button clearButton = new Button("Clear Filters");
        clearButton.getStyleClass().add("secondary-button-small");
        clearButton.setOnAction(e -> {
            statusFilter.setValue("All Statuses");
            routeFilter.setValue("All Routes");
            dateRange.setValue("Last 30 Days");
            refreshRows();
        });

        HBox row = new HBox(10, filtersLabel, dateRange, statusFilter, routeFilter, clearButton);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(0, 0, 14, 0));
        return row;
    }

    private HBox buildColumnHeaderRow() {
        HBox header = new HBox();
        header.getStyleClass().add("history-column-header");
        header.getChildren().addAll(
                headerCell("Sim Name", 90), headerCell("Date", 100), headerCell("Planned Route", 210),
                headerCell("Duration", 80), headerCell("Safety Score", 80), headerCell("Status", 110));
        return header;
    }

    private Label headerCell(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.getStyleClass().add("history-header-cell");
        return label;
    }

    private void refreshRows() {
        rowsContainer.getChildren().clear();
        String status = statusFilter.getValue();
        String route = routeFilter.getValue();

        for (SimulationRecord record : allRecords) {
            boolean statusMatches = status == null || status.equals("All Statuses")
                    || record.getStatus().name().equalsIgnoreCase(status);
            boolean routeMatches = route == null || route.equals("All Routes")
                    || record.getPlannedRoute().equals(route);
            if (statusMatches && routeMatches) {
                rowsContainer.getChildren().add(new HistoryRow(record, () -> {
                    allRecords.remove(record);
                    refreshRows();
                }));
            }
        }

        if (rowsContainer.getChildren().isEmpty()) {
            Label empty = new Label("No simulations match the current filters.");
            empty.getStyleClass().add("hint-text");
            rowsContainer.getChildren().add(empty);
        }
    }
}
