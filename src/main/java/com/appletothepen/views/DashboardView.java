package com.appletothepen.views;

import java.util.List;
import java.util.function.Consumer;

import com.appletothepen.model.SampleData;
import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {

    public DashboardView(String userName, String userEmail, Consumer<String> onNavigate) {
        getStyleClass().add("view-root");

        getChildren().add(new PageHeader("Welcome, " + userName));

        HBox columns = new HBox(20);
        columns.setPadding(new Insets(20));
        VBox.setVgrow(columns, Priority.ALWAYS);

        VBox leftColumn = new VBox(20);
        leftColumn.setPrefWidth(340);
        leftColumn.getChildren().addAll(buildProfileCard(userName, userEmail), buildProgressCard());

        VBox rightColumn = new VBox(20);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        rightColumn.getChildren().add(buildRecentSimulationsCard(onNavigate));

        columns.getChildren().addAll(leftColumn, rightColumn);
        getChildren().add(columns);

        Label quote = new Label("\"Never gonna give you up, Never gonna let you down\" - Rick Astley");
        quote.getStyleClass().add("quote-banner");
        quote.setMaxWidth(Double.MAX_VALUE);
        VBox quoteWrap = new VBox(quote);
        quoteWrap.setPadding(new Insets(0, 20, 20, 20));
        getChildren().add(quoteWrap);
    }

    private Card buildProfileCard(String userName, String userEmail) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label("AVATAR");
        avatar.getStyleClass().add("avatar-placeholder");

        VBox identity = new VBox(2);
        Label name = new Label(userName);
        name.getStyleClass().add("profile-name");
        Label email = new Label(userEmail);
        email.getStyleClass().add("profile-email");
        identity.getChildren().addAll(name, email);

        row.getChildren().addAll(avatar, identity);

        Button editButton = new Button("Edit Profile Details");
        editButton.getStyleClass().add("secondary-button");
        editButton.setMaxWidth(Double.MAX_VALUE);

        Card card = new Card(row, editButton);
        VBox.setMargin(editButton, new Insets(12, 0, 0, 0));
        return card;
    }

    private Card buildProgressCard() {
        Label heading = new Label("Saved Progress Indicator");
        heading.getStyleClass().add("card-title");

        Label taskLabel = new Label("CURRENT TASK: ROUTE SETUP #12 (85% SAVED)");
        taskLabel.getStyleClass().add("progress-caption");

        ProgressBar bar = new ProgressBar(0.85);
        bar.setMaxWidth(Double.MAX_VALUE);
        bar.getStyleClass().add("dashboard-progress");

        return new Card(heading, taskLabel, bar);
    }

    private Card buildRecentSimulationsCard(Consumer<String> onNavigate) {
        Card card = new Card("Recent Simulations List");

        for (String summary : SampleData.recentSimSummaries()) {
            String[] parts = summary.split("\\|");
            card.getChildren().add(buildRecentRow(parts[0], parts[1], parts[2]));
        }

        Button viewAll = new Button("View Full History");
        viewAll.getStyleClass().add("secondary-button");
        viewAll.setOnAction(e -> onNavigate.accept("history"));
        VBox.setMargin(viewAll, new Insets(12, 0, 0, 0));
        card.getChildren().add(viewAll);

        return card;
    }

    private HBox buildRecentRow(String title, String date, String status) {
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
