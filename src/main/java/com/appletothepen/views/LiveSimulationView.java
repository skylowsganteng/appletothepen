package com.appletothepen.views;

import java.util.function.Consumer;

import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LiveSimulationView extends VBox {

    private static final double SPEED_LIMIT = 65;

    private final Label speedValue = new Label();
    private final Label progressLabel = new Label();
    private final Label etaLabel = new Label();
    private final Label modeValue = new Label("AUTONOMOUS");
    private final Canvas canvas = new Canvas();
    private final Circle greenLight = new Circle(8);
    private final Circle yellowLight = new Circle(8);
    private final Circle redLight = new Circle(8);

    private double dashOffset = 0;
    private double currentSpeed = 62;
    private double mile = 12;
    private final double totalMiles = 45;
    private int remainingMinutes = 22;
    private boolean manualOverride = false;
    private AnimationTimer timer;
    private long lastTickNanos = 0;

    public LiveSimulationView(Consumer<String> onNavigate) {
        getStyleClass().add("view-root");
        getStyleClass().add("live-sim-root");

        Button rerouteButton = new Button("Dynamic Reroute");
        rerouteButton.getStyleClass().add("secondary-button");
        getChildren().add(buildLiveHeader(rerouteButton));

        HBox columns = new HBox(20, buildPerceptionCard(), buildCanvasCard(), buildTrafficCard());
        HBox.setHgrow(columns.getChildren().get(1), Priority.ALWAYS);
        columns.setPadding(new Insets(20, 20, 0, 20));
        VBox.setVgrow(columns, Priority.ALWAYS);
        getChildren().add(columns);

        getChildren().add(buildControlBar(onNavigate));

        updateTelemetryLabels();
        startAnimation();

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                stopAnimation();
            } else {
                startAnimation();
            }
        });
    }

    private HBox buildLiveHeader(Button rerouteButton) {
        Label title = new Label("LIVE SIM: SF Freeway Run");
        title.getStyleClass().add("page-title");

        progressLabel.getStyleClass().add("hint-text");
        etaLabel.getStyleClass().add("hint-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(20, title, progressLabel, etaLabel, spacer, rerouteButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("page-header");
        header.setPadding(new Insets(14, 20, 14, 20));
        return header;
    }

    private Card buildPerceptionCard() {
        Card card = new Card("Perception & Safety");
        card.setPrefWidth(230);
        card.setMinWidth(230);
        card.getChildren().addAll(
                statusRow("Obstacle Detection", "ACTIVE (LIDAR)", false),
                statusRow("Collision Avoidance", "STANDBY", false),
                statusRow("Pedestrian Count", "3 Detected", false),
                statusRow("Vehicle Count", "14 Nearby", false),
                statusRow("Blind Spot Left", "CLEAR", false),
                statusRow("Blind Spot Right", "OCCUPIED", true),
                statusRow("Emergency Braking", "READY", false));
        return card;
    }

    private VBox statusRow(String label, String value, boolean warning) {
        Label captionLabel = new Label(label);
        captionLabel.getStyleClass().add("field-label-small");
        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add(warning ? "status-value-warning" : "status-value");
        VBox row = new VBox(1, captionLabel, valueLabel);
        row.setPadding(new Insets(4, 0, 4, 0));
        return row;
    }

    private Card buildCanvasCard() {
        Label speedCaption = new Label("SPEED");
        speedCaption.getStyleClass().add("telemetry-caption");
        speedValue.getStyleClass().add("telemetry-value");
        VBox speedBox = new VBox(2, speedCaption, speedValue);
        speedBox.setAlignment(Pos.CENTER);

        Label limitCaption = new Label("LIMIT");
        limitCaption.getStyleClass().add("telemetry-caption");
        Label limitValue = new Label((int) SPEED_LIMIT + " MPH");
        limitValue.getStyleClass().add("telemetry-value-secondary");
        VBox limitBox = new VBox(2, limitCaption, limitValue);
        limitBox.setAlignment(Pos.CENTER);

        Label modeCaption = new Label("MODE");
        modeCaption.getStyleClass().add("telemetry-caption");
        modeValue.getStyleClass().add("telemetry-mode");
        VBox modeBox = new VBox(2, modeCaption, modeValue);
        modeBox.setAlignment(Pos.CENTER);

        HBox telemetryBar = new HBox(30, speedBox, limitBox, modeBox);
        telemetryBar.setAlignment(Pos.CENTER);
        telemetryBar.getStyleClass().add("telemetry-bar");
        telemetryBar.setPadding(new Insets(10));

        StackPane canvasStack = new StackPane();
        canvasStack.getStyleClass().add("canvas-stack");
        canvas.widthProperty().bind(canvasStack.widthProperty());
        canvas.heightProperty().bind(canvasStack.heightProperty());
        canvas.widthProperty().addListener((o, ov, nv) -> drawFrame());
        canvas.heightProperty().addListener((o, ov, nv) -> drawFrame());

        Label caption = new Label("[ 3D Simulation Real-Time Stream Canvas ]");
        caption.getStyleClass().add("placeholder-caption");
        StackPane.setAlignment(caption, Pos.BOTTOM_CENTER);
        StackPane.setMargin(caption, new Insets(0, 0, 14, 0));

        canvasStack.getChildren().addAll(canvas, caption);
        VBox.setVgrow(canvasStack, Priority.ALWAYS);

        Card card = new Card(telemetryBar, canvasStack);
        VBox.setVgrow(canvasStack, Priority.ALWAYS);
        HBox.setHgrow(card, Priority.ALWAYS);
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private Card buildTrafficCard() {
        Card card = new Card("Traffic Recognition");
        card.setPrefWidth(230);
        card.setMinWidth(230);

        Label historyLabel = new Label("Detection History (Last 5)");
        historyLabel.getStyleClass().add("field-label");

        FlowPane chips = new FlowPane(6, 6);
        for (String sign : new String[] { "STOP", "YIELD", "65", "NO ENT", "OW" }) {
            Label chip = new Label(sign);
            chip.getStyleClass().add("sign-chip");
            chips.getChildren().add(chip);
        }

        Label lightLabel = new Label("Traffic Light State");
        lightLabel.getStyleClass().add("field-label");
        VBox.setMargin(lightLabel, new Insets(14, 0, 4, 0));

        redLight.getStyleClass().addAll("traffic-light", "traffic-light-red");
        yellowLight.getStyleClass().addAll("traffic-light", "traffic-light-yellow");
        greenLight.getStyleClass().addAll("traffic-light", "traffic-light-green", "traffic-light-active");
        HBox lights = new HBox(10, redLight, yellowLight, greenLight);

        card.getChildren().addAll(historyLabel, chips, lightLabel, lights);
        return card;
    }

    private HBox buildControlBar(Consumer<String> onNavigate) {
        Button overrideButton = new Button("FORCE MANUAL OVERRIDE");
        overrideButton.getStyleClass().add("danger-button");
        overrideButton.setOnAction(e -> {
            manualOverride = !manualOverride;
            modeValue.setText(manualOverride ? "MANUAL" : "AUTONOMOUS");
            modeValue.getStyleClass().removeAll("telemetry-mode", "telemetry-mode-manual");
            modeValue.getStyleClass().add(manualOverride ? "telemetry-mode-manual" : "telemetry-mode");
        });

        Button pauseButton = new Button("Pause Simulation");
        pauseButton.getStyleClass().add("secondary-button");
        pauseButton.setOnAction(e -> {
            if (timer == null) {
                startAnimation();
                pauseButton.setText("Pause Simulation");
            } else {
                stopAnimation();
                pauseButton.setText("Resume Simulation");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button endButton = new Button("END SIMULATION & SAVE LOGS");
        endButton.getStyleClass().add("primary-button");
        endButton.setOnAction(e -> onNavigate.accept("dashboard"));

        HBox bar = new HBox(12, overrideButton, pauseButton, spacer, endButton);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("bottom-bar");
        bar.setPadding(new Insets(14, 20, 20, 20));
        return bar;
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
        speedValue.setText(Math.round(currentSpeed) + " MPH");
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
