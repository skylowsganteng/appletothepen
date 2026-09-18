package com.appletothepen.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/** Title bar shown at the top of every page, mirroring the "System Status: Connected" bar in the design. */
public class PageHeader extends HBox {

    public PageHeader(String title) {
        this(title, defaultStatus());
    }

    public PageHeader(String title, Node right) {
        getStyleClass().add("page-header");
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(14, 20, 14, 20));

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("page-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(titleLabel, spacer, right);
    }

    private static HBox defaultStatus() {
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_RIGHT);
        Label label = new Label("System Status: Connected");
        label.getStyleClass().add("status-text");
        Circle dot = new Circle(4);
        dot.getStyleClass().add("status-dot");
        box.getChildren().addAll(label, dot);
        return box;
    }
}
