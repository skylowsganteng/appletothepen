package com.appletothepen.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

/**
 * Grey box with a diagonal cross, used everywhere the design shows an un-built map/chart/canvas area.
 * Usable directly as an FXML tag, e.g. {@code <PlaceholderBox caption="Route Preview Map" prefHeight="220"/>}.
 */
public class PlaceholderBox extends StackPane {

    private final StringProperty caption = new SimpleStringProperty("");
    private final Label label = new Label();

    public PlaceholderBox() {
        getStyleClass().add("placeholder-box");

        Pane crossPane = new Pane();
        Line diagonal1 = new Line();
        Line diagonal2 = new Line();
        diagonal1.getStyleClass().add("placeholder-cross");
        diagonal2.getStyleClass().add("placeholder-cross");

        diagonal1.startXProperty().bind(crossPane.widthProperty().multiply(0));
        diagonal1.startYProperty().bind(crossPane.heightProperty().multiply(0));
        diagonal1.endXProperty().bind(crossPane.widthProperty());
        diagonal1.endYProperty().bind(crossPane.heightProperty());

        diagonal2.startXProperty().bind(crossPane.widthProperty());
        diagonal2.startYProperty().bind(crossPane.heightProperty().multiply(0));
        diagonal2.endXProperty().bind(crossPane.widthProperty().multiply(0));
        diagonal2.endYProperty().bind(crossPane.heightProperty());

        crossPane.getChildren().addAll(diagonal1, diagonal2);
        crossPane.prefWidthProperty().bind(widthProperty());
        crossPane.prefHeightProperty().bind(heightProperty());

        label.getStyleClass().add("placeholder-caption");
        StackPane.setAlignment(label, Pos.CENTER);
        caption.addListener((obs, oldVal, newVal) -> label.setText(newVal == null ? "" : newVal.toUpperCase()));

        getChildren().addAll(crossPane, label);
    }

    public PlaceholderBox(String captionText) {
        this();
        setCaption(captionText);
    }

    public PlaceholderBox(String captionText, double prefHeight) {
        this(captionText);
        setPrefHeight(prefHeight);
    }

    public String getCaption() { return caption.get(); }
    public void setCaption(String value) { caption.set(value); }
    public StringProperty captionProperty() { return caption; }
}
