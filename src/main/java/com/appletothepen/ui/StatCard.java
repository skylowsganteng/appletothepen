package com.appletothepen.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/** Small stat tile used on the Analytics dashboard (label, big value, caption). */
public class StatCard extends VBox {

    public StatCard(String title, String value, String caption) {
        getStyleClass().add("stat-card");

        Label titleLabel = new Label(title.toUpperCase());
        titleLabel.getStyleClass().add("stat-title");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("stat-value");

        Label captionLabel = new Label(caption);
        captionLabel.getStyleClass().add("stat-caption");

        getChildren().addAll(titleLabel, valueLabel, captionLabel);
    }
}
