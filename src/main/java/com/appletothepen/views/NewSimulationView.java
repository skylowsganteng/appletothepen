package com.appletothepen.views;

import java.util.function.Consumer;

import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;
import com.appletothepen.ui.PlaceholderBox;
import com.appletothepen.ui.Rows;
import com.appletothepen.ui.ToggleSwitch;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class NewSimulationView extends VBox {

    public NewSimulationView(Consumer<String> onNavigate) {
        getStyleClass().add("view-root");

        Label draftBadge = new Label("CONFIG STATE: DRAFT");
        draftBadge.getStyleClass().add("badge-draft");
        getChildren().add(new PageHeader("Active Prep: SIM-205 Configuration", draftBadge));

        HBox columns = new HBox(20, buildDestinationCard(), buildDrivingConfigCard());
        columns.setPadding(new Insets(20, 20, 0, 20));
        HBox.setHgrow(columns.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(columns.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(columns, Priority.ALWAYS);
        getChildren().add(columns);

        getChildren().add(buildStartBar(onNavigate));
    }

    private Card buildDestinationCard() {
        Card card = new Card("1. Destination Selection & Planning");

        Label searchLabel = new Label("Search Destination Coordinates");
        searchLabel.getStyleClass().add("field-label");
        TextField searchField = new TextField("Market St, San Francisco");

        Label routeLabel = new Label("Route Planning Algorithm Options");
        routeLabel.getStyleClass().add("field-label");

        ToggleGroup group = new ToggleGroup();
        ToggleButton fastest = new ToggleButton("Fastest");
        ToggleButton shortest = new ToggleButton("Shortest");
        ToggleButton scenic = new ToggleButton("Scenic");
        for (ToggleButton button : new ToggleButton[] { fastest, shortest, scenic }) {
            button.setToggleGroup(group);
            button.getStyleClass().add("route-option");
        }
        fastest.setSelected(true);
        HBox routeOptions = new HBox(8, fastest, shortest, scenic);

        HBox rerouteRow = Rows.labeledRow("Enable Dynamic Rerouting On Obstacle Detection", new ToggleSwitch(true));

        Label mapCaption = new Label("Route Preview Map Placeholder");
        mapCaption.getStyleClass().add("field-label");
        PlaceholderBox mapBox = new PlaceholderBox("Route Preview Map View Placeholder");

        VBox.setVgrow(mapBox, Priority.ALWAYS);
        card.getChildren().addAll(searchLabel, searchField, routeLabel, routeOptions, rerouteRow, mapCaption, mapBox);
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private Card buildDrivingConfigCard() {
        Card card = new Card("2. Autonomous Driving Config");

        card.getChildren().add(Rows.labeledRow("Autonomous Mode (Default Start)", new ToggleSwitch(true)));
        card.getChildren().add(Rows.labeledRow("Manual Override Access Trigger", new ToggleSwitch(true)));
        card.getChildren().add(Rows.labeledRow("Lane Keeping Assistance (LKA)", new ToggleSwitch(false)));
        card.getChildren().add(Rows.labeledRow("Adaptive Cruise Control (ACC)", new ToggleSwitch(true)));

        Label speedLabel = new Label("Target Cruising Speed");
        speedLabel.getStyleClass().add("field-label");
        Label speedValue = new Label("65 MPH");
        speedValue.getStyleClass().add("field-value");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox speedHeader = new HBox(speedLabel, spacer, speedValue);
        speedHeader.setAlignment(Pos.CENTER_LEFT);
        speedHeader.setPadding(new Insets(10, 0, 4, 0));

        Slider speedSlider = new Slider(0, 120, 65);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                speedValue.setText(newVal.intValue() + " MPH"));

        CheckBox avpCheck = new CheckBox("Automatic Valet Parking Capability (AVP)");
        avpCheck.setSelected(true);
        avpCheck.getStyleClass().add("field-checkbox");
        VBox.setMargin(avpCheck, new Insets(14, 0, 0, 0));

        card.getChildren().addAll(speedHeader, speedSlider, avpCheck);
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private HBox buildStartBar(Consumer<String> onNavigate) {
        Label hint = new Label("Ensure environment sensors are initialized before starting simulation.");
        hint.getStyleClass().add("hint-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button startButton = new Button("START SIMULATION ▶");
        startButton.getStyleClass().add("primary-button");
        startButton.setOnAction(e -> onNavigate.accept("live-simulation"));

        HBox bar = new HBox(16, hint, spacer, startButton);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("bottom-bar");
        bar.setPadding(new Insets(14, 20, 20, 20));
        return bar;
    }
}
