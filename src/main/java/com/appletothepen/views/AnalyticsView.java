package com.appletothepen.views;

import java.util.function.Consumer;

import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;
import com.appletothepen.ui.StatCard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AnalyticsView extends VBox {

    public AnalyticsView(Consumer<String> onNavigate) {
        getStyleClass().add("view-root");
        getChildren().add(new PageHeader("Analytics and Performance Dashboard"));

        VBox contentWrap = new VBox(20);
        contentWrap.setPadding(new Insets(20));
        VBox.setVgrow(contentWrap, Priority.ALWAYS);

        contentWrap.getChildren().add(buildStatRow());

        HBox chartsRow = new HBox(20,
                buildBarChartCard(), buildPieChartCard());
        HBox.setHgrow(chartsRow.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(chartsRow.getChildren().get(1), Priority.ALWAYS);
        contentWrap.getChildren().add(chartsRow);

        HBox bottomRow = new HBox(20, buildLineChartCard(), buildTravelTimeCard());
        HBox.setHgrow(bottomRow.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(bottomRow.getChildren().get(1), Priority.ALWAYS);
        contentWrap.getChildren().add(bottomRow);

        contentWrap.getChildren().add(buildReplayLogsCard(onNavigate));

        getChildren().add(contentWrap);
    }

    private HBox buildStatRow() {
        HBox row = new HBox(20,
                new StatCard("Safety Score", "98.2 / 100", "Gauge: High Compliance"),
                new StatCard("Total Simulations", "205 Runs", "+12 vs Last Month"),
                new StatCard("Average Travel Time", "42.5 Min", "-2.4 Min Optimization"),
                new StatCard("Collision Events", "0 Occurrences", "100% Zero-Crash"));
        for (var node : row.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }
        return row;
    }

    private Card buildBarChartCard() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(null);
        chart.setLegendVisible(true);
        chart.setAnimated(false);
        chart.setPrefHeight(250);

        XYChart.Series<String, Number> speed = new XYChart.Series<>();
        speed.setName("Speed (mph)");
        speed.getData().add(new XYChart.Data<>("Braking", 18));
        speed.getData().add(new XYChart.Data<>("Accel.", 42));
        speed.getData().add(new XYChart.Data<>("Cornering", 28));
        speed.getData().add(new XYChart.Data<>("Cruise", 65));

        // Scaled by 100x so it's visible next to Speed on the same axis (0.6 g -> 60).
        XYChart.Series<String, Number> gForce = new XYChart.Series<>();
        gForce.setName("G-Force (x100)");
        gForce.getData().add(new XYChart.Data<>("Braking", 60));
        gForce.getData().add(new XYChart.Data<>("Accel.", 35));
        gForce.getData().add(new XYChart.Data<>("Cornering", 50));
        gForce.getData().add(new XYChart.Data<>("Cruise", 10));

        chart.getData().addAll(speed, gForce);

        Card card = new Card("Driving Performance Metrics (Speed, G-Force)", chart);
        VBox.setVgrow(chart, Priority.ALWAYS);
        return card;
    }

    private Card buildPieChartCard() {
        PieChart chart = new PieChart();
        chart.setLegendVisible(true);
        chart.setLabelsVisible(true);
        chart.setAnimated(false);
        chart.setPrefHeight(250);
        chart.getData().addAll(
                new PieChart.Data("Pedestrian Near-Miss", 3),
                new PieChart.Data("Vehicle Merge", 8),
                new PieChart.Data("Static Object", 2),
                new PieChart.Data("Road Debris", 1));

        Card card = new Card("Collision & Near-Miss Stats by Obstacle Type", chart);
        VBox.setVgrow(chart, Priority.ALWAYS);
        return card;
    }

    private Card buildLineChartCard() {
        NumberAxis xAxis = new NumberAxis(1, 8, 1);
        xAxis.setLabel("Ride #");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Efficiency");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefHeight(200);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Energy / Time Optimization");
        double[] values = { 71, 74, 78, 76, 82, 85, 87, 91 };
        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data<>(i + 1, values[i]));
        }
        chart.getData().add(series);

        Card card = new Card("Route Efficiency Analysis", chart);
        VBox.setVgrow(chart, Priority.ALWAYS);
        return card;
    }

    private Card buildTravelTimeCard() {
        Card card = new Card("Travel Time Summary Table");
        String[] rows = {
                "SIM-129 (Highway Run) - 45 Min - 32 Mi",
                "SIM-128 (Urban Downtown) - 18 Min - 4.5 Mi",
                "SIM-127 (Rain Fog Test) - 52 Min - 28 Mi"
        };
        for (String row : rows) {
            Label label = new Label(row);
            label.getStyleClass().add("summary-row");
            card.getChildren().add(label);
        }
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private Card buildReplayLogsCard(Consumer<String> onNavigate) {
        Label caption = new Label("Run 205 Complete Logs - Generated 2026-03-29");
        caption.getStyleClass().add("hint-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button launchButton = new Button("Launch Visual Replay");
        launchButton.getStyleClass().add("secondary-button");
        launchButton.setOnAction(e -> onNavigate.accept("live-simulation"));

        HBox row = new HBox(10, caption, spacer, launchButton);
        row.setAlignment(Pos.CENTER_LEFT);

        return new Card("Quick Telemetry Replay Logs", row);
    }
}
