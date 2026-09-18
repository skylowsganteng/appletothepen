package com.appletothepen.ui;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/** A small iOS-style on/off switch, since JavaFX has no built-in toggle control. */
public class ToggleSwitch extends StackPane {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final Rectangle track;
    private final Circle knob;

    public ToggleSwitch() {
        this(false);
    }

    public ToggleSwitch(boolean initialState) {
        track = new Rectangle(40, 22);
        track.setArcWidth(22);
        track.setArcHeight(22);
        track.getStyleClass().add("toggle-track");

        knob = new Circle(9);
        knob.getStyleClass().add("toggle-knob");
        StackPane.setAlignment(knob, Pos.CENTER_LEFT);
        knob.setTranslateX(-9);

        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(track, knob);
        getStyleClass().add("toggle-switch");
        setMaxSize(40, 22);
        setMinSize(40, 22);

        setOnMouseClicked(e -> setSelected(!isSelected()));
        selected.addListener((obs, was, isNow) -> applyState(isNow, true));
        selected.set(initialState);
        applyState(initialState, false);
    }

    private void applyState(boolean on, boolean animate) {
        if (on) {
            track.getStyleClass().add("toggle-track-on");
        } else {
            track.getStyleClass().remove("toggle-track-on");
        }
        double targetX = on ? 9 : -9;
        if (animate) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(120), knob);
            tt.setToX(targetX);
            tt.play();
        } else {
            knob.setTranslateX(targetX);
        }
    }

    public boolean isSelected() { return selected.get(); }
    public void setSelected(boolean value) { selected.set(value); }
    public BooleanProperty selectedProperty() { return selected; }
}
