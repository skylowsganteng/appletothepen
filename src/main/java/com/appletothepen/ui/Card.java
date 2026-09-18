package com.appletothepen.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * White rounded panel used throughout the app to group related content.
 * Usable directly as an FXML tag: {@code <Card title="Recent Simulations List"> ... </Card>} —
 * nested children land in the card body via VBox's default "children" property.
 */
public class Card extends VBox {

    private final StringProperty title = new SimpleStringProperty();
    private final Label titleLabel = new Label();

    public Card() {
        getStyleClass().add("card");
        titleLabel.getStyleClass().add("card-title");
        title.addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                getChildren().remove(titleLabel);
            } else {
                titleLabel.setText(newVal);
                if (!getChildren().contains(titleLabel)) {
                    getChildren().add(0, titleLabel);
                }
            }
        });
    }

    public Card(Node... content) {
        this();
        getChildren().addAll(content);
    }

    public Card(String titleText, Node... content) {
        this();
        setTitle(titleText);
        getChildren().addAll(content);
    }

    public String getTitle() { return title.get(); }
    public void setTitle(String text) { title.set(text); }
    public StringProperty titleProperty() { return title; }
}
