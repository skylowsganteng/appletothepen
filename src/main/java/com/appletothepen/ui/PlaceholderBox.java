package com.appletothepen.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

/** Grey box with a diagonal cross, used everywhere the design shows an un-built map/chart/canvas area. */
public class PlaceholderBox extends StackPane {

    public PlaceholderBox(String caption) {
        this(caption, 220);
    }

    public PlaceholderBox(String caption, double prefHeight) {
        getStyleClass().add("placeholder-box");
        setPrefHeight(prefHeight);

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

        Label label = new Label(caption.toUpperCase());
        label.getStyleClass().add("placeholder-caption");
        StackPane.setAlignment(label, Pos.CENTER);

        getChildren().addAll(crossPane, label);
    }
}
