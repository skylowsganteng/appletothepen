package com.appletothepen.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/** Small layout helpers so views don't repeat the same "label ... control" row wiring. */
public final class Rows {

    private Rows() { }

    public static HBox labeledRow(String text, Node control) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(10, label, spacer, control);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 0, 6, 0));
        return row;
    }
}
