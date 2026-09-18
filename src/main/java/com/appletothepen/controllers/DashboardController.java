package com.appletothepen.controllers;

import java.util.function.Consumer;

import com.appletothepen.model.SampleData;
import com.appletothepen.ui.PageHeader;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML private PageHeader header;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private VBox recentList;

    private Consumer<String> onNavigate;

    public void init(String userName, String userEmail, Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
        header.setTitle("Welcome, " + userName);
        nameLabel.setText(userName);
        emailLabel.setText(userEmail);
        populateRecentList();
    }

    @FXML
    private void handleViewFullHistory() {
        onNavigate.accept("history");
    }

    private void populateRecentList() {
        for (String summary : SampleData.recentSimSummaries()) {
            String[] parts = summary.split("\\|");
            recentList.getChildren().add(buildRow(parts[0], parts[1], parts[2]));
        }
    }

    private HBox buildRow(String title, String date, String status) {
        VBox textBox = new VBox(2);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("row-title");
        Label dateLabel = new Label("DATE: " + date);
        dateLabel.getStyleClass().add("row-caption");
        textBox.getChildren().addAll(titleLabel, dateLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label(status);
        statusBadge.getStyleClass().add(status.startsWith("COMPLETED") ? "badge-completed" : "badge-aborted");

        HBox row = new HBox(10, textBox, spacer, statusBadge);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("list-row");
        return row;
    }
}
