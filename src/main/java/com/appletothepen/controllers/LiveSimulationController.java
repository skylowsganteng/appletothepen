package com.appletothepen.controllers;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LiveSimulationController {

    private static final double SPEED_LIMIT = 65;

    @FXML private Label progressLabel;
    @FXML private Label etaLabel;
    @FXML private Label speedValueLabel;
    @FXML private Label modeValueLabel;
    @FXML private StackPane canvasStack;
    @FXML private Canvas canvas;
    @FXML private Button pauseButton;

    private double dashOffset = 0;
    private double currentSpeed = 62;
    private double mile = 12;
    private final double totalMiles = 45;
    private int remainingMinutes = 22;
    private boolean manualOverride = false;
    private AnimationTimer timer;
    private long lastTickNanos = 0;
    private Consumer<String> onNavigate;

    @FXML
    private void initialize() {
        canvas.widthProperty().bind(canvasStack.widthProperty());
        canvas.heightProperty().bind(canvasStack.heightProperty());
        canvas.widthProperty().addListener((o, ov, nv) -> drawFrame());
        canvas.heightProperty().addListener((o, ov, nv) -> drawFrame());

        updateTelemetryLabels();
        startAnimation();

        canvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                stopAnimation();
            } else {
                startAnimation();
            }
        });
    }

    public void init(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }

    @FXML
    private void handleOverride() {
        manualOverride = !manualOverride;
        modeValueLabel.setText(manualOverride ? "MANUAL" : "AUTONOMOUS");
        modeValueLabel.getStyleClass().removeAll("telemetry-mode", "telemetry-mode-manual");
        modeValueLabel.getStyleClass().add(manualOverride ? "telemetry-mode-manual" : "telemetry-mode");
    }

    @FXML
    private void handlePause() {
        if (timer == null) {
            startAnimation();
            pauseButton.setText("Pause Simulation");
        } else {
            stopAnimation();
            pauseButton.setText("Resume Simulation");
        }
    }

    @FXML
    private void handleEnd() {
        onNavigate.accept("dashboard");
    }

    private void startAnimation() {
        if (timer != null) {
            return;
        }
        lastTickNanos = 0;
        timer = new AnimationTimer() {
            long lastDiscreteUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastTickNanos == 0) {
                    lastTickNanos = now;
                    lastDiscreteUpdate = now;
                }
                double deltaSeconds = (now - lastTickNanos) / 1_000_000_000.0;
                lastTickNanos = now;

                dashOffset -= deltaSeconds * (currentSpeed * 4);
                drawFrame();

                if (now - lastDiscreteUpdate > 1_000_000_000L) {
                    lastDiscreteUpdate = now;
                    tickTelemetry();
                }
            }
        };
        timer.start();
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void tickTelemetry() {
        double jitter = (Math.random() - 0.5) * 4;
        currentSpeed = Math.max(45, Math.min(SPEED_LIMIT, currentSpeed + jitter));
        mile = Math.min(totalMiles, mile + currentSpeed / 3600.0 * 60);
        if (remainingMinutes > 0 && Math.random() > 0.5) {
            remainingMinutes--;
        }
        updateTelemetryLabels();
    }

    private void updateTelemetryLabels() {
        speedValueLabel.setText(Math.round(currentSpeed) + " MPH");
        progressLabel.setText(String.format("Progress: Mile %.0f / %.0f", mile, totalMiles));
        etaLabel.setText(String.format("ETA: %d Min remaining", remainingMinutes));
    }

    private void drawFrame() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web("#f4f5f7"));
        gc.fillRect(0, 0, w, h);

        gc.setStroke(Color.web("#c7cad1"));
        gc.setLineWidth(2);
        gc.setLineDashes(14, 18);
        gc.setLineDashOffset(dashOffset % 32);
        gc.strokeLine(w / 2, 0, w / 2, h);
    }
}
