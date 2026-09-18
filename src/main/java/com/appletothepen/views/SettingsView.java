package com.appletothepen.views;

import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;
import com.appletothepen.ui.Rows;
import com.appletothepen.ui.ToggleSwitch;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class SettingsView extends VBox {

    public SettingsView() {
        getStyleClass().add("view-root");
        getChildren().add(new PageHeader("Settings"));

        Card card = new Card("Application Preferences");
        card.getChildren().addAll(
                Rows.labeledRow("Enable Desktop Notifications", new ToggleSwitch(true)),
                Rows.labeledRow("Dark Mode", new ToggleSwitch(false)),
                Rows.labeledRow("Auto-Save Simulation Logs", new ToggleSwitch(true)));

        VBox contentWrap = new VBox(card);
        contentWrap.setPadding(new Insets(20));
        getChildren().add(contentWrap);
    }
}
