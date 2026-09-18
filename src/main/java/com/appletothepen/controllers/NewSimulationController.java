package com.appletothepen.controllers;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class NewSimulationController {

    @FXML private Slider speedSlider;
    @FXML private Label speedValueLabel;

    private Consumer<String> onNavigate;

    @FXML
    private void initialize() {
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                speedValueLabel.setText(newVal.intValue() + " MPH"));
    }

    public void init(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }

    @FXML
    private void handleStart() {
        onNavigate.accept("live-simulation");
    }
}
