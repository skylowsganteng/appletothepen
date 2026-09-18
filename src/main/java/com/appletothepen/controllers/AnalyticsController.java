package com.appletothepen.controllers;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class AnalyticsController {

    @FXML private BarChart<String, Number> performanceChart;
    @FXML private PieChart obstacleChart;
    @FXML private LineChart<Number, Number> efficiencyChart;

    private Consumer<String> onNavigate;

    @FXML
    private void initialize() {
        populatePerformanceChart();
        populateObstacleChart();
        populateEfficiencyChart();
    }

    public void init(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }

    @FXML
    private void handleLaunchReplay() {
        onNavigate.accept("live-simulation");
    }

    private void populatePerformanceChart() {
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

        performanceChart.getData().addAll(speed, gForce);
    }

    private void populateObstacleChart() {
        obstacleChart.getData().addAll(
                new PieChart.Data("Pedestrian Near-Miss", 3),
                new PieChart.Data("Vehicle Merge", 8),
                new PieChart.Data("Static Object", 2),
                new PieChart.Data("Road Debris", 1));
    }

    private void populateEfficiencyChart() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Energy / Time Optimization");
        double[] values = { 71, 74, 78, 76, 82, 85, 87, 91 };
        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data<>(i + 1, values[i]));
        }
        efficiencyChart.getData().add(series);
    }
}
