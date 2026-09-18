package com.appletothepen.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {

    private final Map<String, Label> navButtons = new LinkedHashMap<>();
    private String activeKey;

    public Sidebar(Consumer<String> onNavigate) {
        getStyleClass().add("sidebar");
        setPrefWidth(190);
        setMinWidth(190);

        HBox brand = new HBox(8);
        brand.setAlignment(Pos.CENTER_LEFT);
        Label logo = new Label("DR");
        logo.getStyleClass().add("brand-logo");
        Label name = new Label("Simulation");
        name.getStyleClass().add("brand-name");
        brand.getChildren().addAll(logo, name);
        brand.setPadding(new Insets(4, 0, 24, 4));

        getChildren().add(brand);

        addNavItem("dashboard", "Dashboard", onNavigate);
        addNavItem("new-simulation", "New Simulation", onNavigate);
        addNavItem("history", "Simulation History", onNavigate);
        addNavItem("analytics", "Analytics", onNavigate);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);

        addNavItem("profile", "Profile", onNavigate);
        addNavItem("settings", "Settings", onNavigate);

        setPadding(new Insets(20, 14, 20, 14));
    }

    private void addNavItem(String key, String text, Consumer<String> onNavigate) {
        Label item = new Label(text);
        item.getStyleClass().add("nav-item");
        item.setMaxWidth(Double.MAX_VALUE);
        item.setOnMouseClicked(e -> {
            setActive(key);
            onNavigate.accept(key);
        });
        navButtons.put(key, item);
        getChildren().add(item);
    }

    public void setActive(String key) {
        if (activeKey != null && navButtons.containsKey(activeKey)) {
            navButtons.get(activeKey).getStyleClass().remove("nav-item-active");
        }
        activeKey = key;
        Label item = navButtons.get(key);
        if (item != null) {
            item.getStyleClass().add("nav-item-active");
        }
    }
}
