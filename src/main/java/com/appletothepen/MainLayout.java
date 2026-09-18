package com.appletothepen;

import com.appletothepen.ui.Sidebar;
import com.appletothepen.views.AnalyticsView;
import com.appletothepen.views.DashboardView;
import com.appletothepen.views.LiveSimulationView;
import com.appletothepen.views.NewSimulationView;
import com.appletothepen.views.ProfileView;
import com.appletothepen.views.SettingsView;
import com.appletothepen.views.SimulationHistoryView;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/** Root layout: a fixed sidebar on the left and a swappable content area on the right. */
public class MainLayout extends BorderPane {

    private static final String USER_NAME = "Nabil Wajdi";
    private static final String USER_EMAIL = "NabilWajdi@gmail.com";

    private final StackPane content = new StackPane();
    private final Sidebar sidebar;

    public MainLayout() {
        getStyleClass().add("root-layout");
        sidebar = new Sidebar(this::navigate);
        setLeft(sidebar);
        setCenter(content);
        navigate("dashboard");
    }

    private void navigate(String key) {
        sidebar.setActive(routableKey(key));
        content.getChildren().setAll(buildView(key));
    }

    private String routableKey(String key) {
        return key.equals("live-simulation") ? "new-simulation" : key;
    }

    private javafx.scene.Node buildView(String key) {
        return switch (key) {
            case "dashboard" -> new DashboardView(USER_NAME, USER_EMAIL, this::navigate);
            case "new-simulation" -> new NewSimulationView(this::navigate);
            case "history" -> new SimulationHistoryView();
            case "analytics" -> new AnalyticsView(this::navigate);
            case "live-simulation" -> new LiveSimulationView(this::navigate);
            case "profile" -> new ProfileView(USER_NAME, USER_EMAIL);
            case "settings" -> new SettingsView();
            default -> new Label("Unknown view: " + key);
        };
    }
}
