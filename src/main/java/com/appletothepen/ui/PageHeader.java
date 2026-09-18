package com.appletothepen.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * Title bar shown at the top of most pages, mirroring the "System Status: Connected" bar in the design.
 * Usable directly as an FXML tag: {@code <PageHeader title="Welcome, Nabil"/>}.
 * Screens with a bespoke right-hand side (New Simulation, Live Simulation) build their own header instead.
 */
public class PageHeader extends HBox {

    private final StringProperty title = new SimpleStringProperty("");

    public PageHeader() {
        getStyleClass().add("page-header");
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(14, 20, 14, 20));

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("page-title");
        titleLabel.textProperty().bind(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(titleLabel, spacer, buildStatus());
    }

    public PageHeader(String titleText) {
        this();
        setTitle(titleText);
    }

    private HBox buildStatus() {
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_RIGHT);
        Label label = new Label("System Status: Connected");
        label.getStyleClass().add("status-text");
        Circle dot = new Circle(4);
        dot.getStyleClass().add("status-dot");
        box.getChildren().addAll(label, dot);
        return box;
    }

    public String getTitle() { return title.get(); }
    public void setTitle(String text) { title.set(text); }
    public StringProperty titleProperty() { return title; }
}
