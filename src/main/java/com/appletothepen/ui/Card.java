package com.appletothepen.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/** White rounded panel used throughout the app to group related content. */
public class Card extends VBox {

    public Card(Node... content) {
        getStyleClass().add("card");
        getChildren().addAll(content);
    }

    public Card(String title, Node... content) {
        this();
        Label heading = new Label(title);
        heading.getStyleClass().add("card-title");
        getChildren().add(heading);
        getChildren().addAll(content);
    }
}
