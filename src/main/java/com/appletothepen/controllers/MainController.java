package com.appletothepen.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/** Controller for main.fxml: owns the sidebar and swaps the center content between the FXML screens. */
public class MainController {

    private static final String FXML_BASE = "/com/appletothepen/fxml/";
    private static final String USER_NAME = "Nabil Wajdi";
    private static final String USER_EMAIL = "NabilWajdi@gmail.com";

    @FXML private StackPane contentArea;
    @FXML private Label navDashboard;
    @FXML private Label navNewSimulation;
    @FXML private Label navHistory;
    @FXML private Label navAnalytics;
    @FXML private Label navProfile;
    @FXML private Label navSettings;

    private Label activeNavItem;

    @FXML
    private void initialize() {
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        LoadedView view = loadFxml("dashboard.fxml");
        ((DashboardController) view.controller()).init(USER_NAME, USER_EMAIL, this::navigateTo);
        display(view.root(), navDashboard);
    }

    @FXML
    private void showNewSimulation() {
        LoadedView view = loadFxml("new_simulation.fxml");
        ((NewSimulationController) view.controller()).init(this::navigateTo);
        display(view.root(), navNewSimulation);
    }

    @FXML
    private void showHistory() {
        LoadedView view = loadFxml("history.fxml");
        display(view.root(), navHistory);
    }

    @FXML
    private void showAnalytics() {
        LoadedView view = loadFxml("analytics.fxml");
        ((AnalyticsController) view.controller()).init(this::navigateTo);
        display(view.root(), navAnalytics);
    }

    @FXML
    private void showProfile() {
        LoadedView view = loadFxml("profile.fxml");
        ((ProfileController) view.controller()).init(USER_NAME, USER_EMAIL);
        display(view.root(), navProfile);
    }

    @FXML
    private void showSettings() {
        LoadedView view = loadFxml("settings.fxml");
        display(view.root(), navSettings);
    }

    private void showLiveSimulation() {
        LoadedView view = loadFxml("live_simulation.fxml");
        ((LiveSimulationController) view.controller()).init(this::navigateTo);
        // Live Simulation is launched from New Simulation / Analytics, not its own sidebar item.
        display(view.root(), navNewSimulation);
    }

    /** Shared by every screen's buttons that need to jump to another screen (e.g. "Start Simulation"). */
    private void navigateTo(String key) {
        switch (key) {
            case "dashboard" -> showDashboard();
            case "new-simulation" -> showNewSimulation();
            case "history" -> showHistory();
            case "analytics" -> showAnalytics();
            case "live-simulation" -> showLiveSimulation();
            case "profile" -> showProfile();
            case "settings" -> showSettings();
            default -> throw new IllegalArgumentException("Unknown view: " + key);
        }
    }

    private void display(Parent view, Label navItem) {
        if (activeNavItem != null) {
            activeNavItem.getStyleClass().remove("nav-item-active");
        }
        activeNavItem = navItem;
        activeNavItem.getStyleClass().add("nav-item-active");
        contentArea.getChildren().setAll(view);
    }

    private LoadedView loadFxml(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_BASE + fxmlName));
            Parent root = loader.load();
            return new LoadedView(root, loader.getController());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fxmlName, e);
        }
    }

    private record LoadedView(Parent root, Object controller) { }
}
