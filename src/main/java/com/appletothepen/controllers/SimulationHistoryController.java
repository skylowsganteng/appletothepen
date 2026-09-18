package com.appletothepen.controllers;

import java.util.ArrayList;
import java.util.List;

import com.appletothepen.model.SampleData;
import com.appletothepen.model.SimulationRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SimulationHistoryController {

    @FXML private ComboBox<String> dateRangeCombo;
    @FXML private ComboBox<String> statusCombo;
    @FXML private ComboBox<String> routeCombo;
    @FXML private VBox rowsContainer;

    private final ObservableList<SimulationRecord> allRecords =
            FXCollections.observableArrayList(SampleData.historyRecords());

    @FXML
    private void initialize() {
        dateRangeCombo.setItems(FXCollections.observableArrayList("Last 7 Days", "Last 30 Days", "All Time"));
        dateRangeCombo.setValue("Last 30 Days");

        statusCombo.setItems(FXCollections.observableArrayList("All Statuses", "Completed", "Aborted"));
        statusCombo.setValue("Completed");
        statusCombo.setOnAction(e -> refreshRows());

        List<String> routes = new ArrayList<>();
        routes.add("All Routes");
        for (SimulationRecord record : allRecords) {
            if (!routes.contains(record.getPlannedRoute())) {
                routes.add(record.getPlannedRoute());
            }
        }
        routeCombo.setItems(FXCollections.observableArrayList(routes));
        routeCombo.setValue("All Routes");
        routeCombo.setOnAction(e -> refreshRows());

        refreshRows();
    }

    @FXML
    private void handleClearFilters() {
        statusCombo.setValue("All Statuses");
        routeCombo.setValue("All Routes");
        dateRangeCombo.setValue("Last 30 Days");
        refreshRows();
    }

    private void refreshRows() {
        rowsContainer.getChildren().clear();
        String status = statusCombo.getValue();
        String route = routeCombo.getValue();

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
