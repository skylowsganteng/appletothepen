package com.appletothepen.views;

import com.appletothepen.model.SimulationRecord;
import com.appletothepen.ui.PlaceholderBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/** One expandable row in the Simulation History table: a summary line plus a collapsible detail panel. */
class HistoryRow extends VBox {

    private final VBox detailPanel;
    private boolean expanded = false;

    HistoryRow(SimulationRecord record, Runnable onDelete) {
        getStyleClass().add("history-row");

        HBox summary = new HBox();
        summary.setAlignment(Pos.CENTER_LEFT);
        summary.getStyleClass().add("history-row-summary");

        summary.getChildren().addAll(
                column(record.getSimName(), 90),
                column(record.getDate(), 100),
                column(record.getPlannedRoute(), 210),
                column(record.getDuration(), 80),
                column(String.valueOf(record.getSafetyScore()), 80));

        Label statusBadge = new Label(record.getStatus().name());
        statusBadge.getStyleClass().add(record.getStatus() == SimulationRecord.Status.COMPLETED
                ? "badge-completed" : "badge-aborted");
        HBox statusColumn = new HBox(statusBadge);
        statusColumn.setPrefWidth(110);
        summary.getChildren().add(statusColumn);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        summary.getChildren().add(spacer);

        Button viewButton = new Button("View");
        viewButton.getStyleClass().add("primary-button-small");

        Button delButton = new Button("Del");
        delButton.getStyleClass().add("secondary-button-small");
        delButton.setOnAction(e -> onDelete.run());

        summary.getChildren().addAll(viewButton, delButton);

        detailPanel = buildDetailPanel(record);
        detailPanel.setVisible(false);
        detailPanel.setManaged(false);

        viewButton.setOnAction(e -> toggleExpanded());

        getChildren().addAll(summary, detailPanel);
    }

    private Label column(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.getStyleClass().add("history-cell");
        return label;
    }

    private void toggleExpanded() {
        expanded = !expanded;
        detailPanel.setVisible(expanded);
        detailPanel.setManaged(expanded);
    }

    private VBox buildDetailPanel(SimulationRecord record) {
        Label mapLabel = new Label("Expanded Details: Route Map");
        mapLabel.getStyleClass().add("field-label");
        PlaceholderBox mapBox = new PlaceholderBox("Selected Route Telemetry Map Placeholder", 150);
        VBox mapColumn = new VBox(6, mapLabel, mapBox);
        mapColumn.setPrefWidth(320);

        Label eventsLabel = new Label("Key Events Timeline");
        eventsLabel.getStyleClass().add("field-label");
        VBox eventsList = new VBox(4);
        for (String event : record.getKeyEvents()) {
            Label eventLabel = new Label(event);
            eventLabel.getStyleClass().add("timeline-event");
            eventsList.getChildren().add(eventLabel);
        }
        VBox eventsColumn = new VBox(6, eventsLabel, eventsList);
        HBox.setHgrow(eventsColumn, Priority.ALWAYS);

        HBox content = new HBox(20, mapColumn, eventsColumn);
        VBox panel = new VBox(content);
        panel.getStyleClass().add("history-detail-panel");
        panel.setPadding(new Insets(14));
        return panel;
    }
}
