package com.appletothepen.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Small stat tile used on the Analytics dashboard (label, big value, caption).
 * Usable directly as an FXML tag, e.g. {@code <StatCard title="Safety Score" value="98.2 / 100" caption="..."/>}.
 */
public class StatCard extends VBox {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty value = new SimpleStringProperty("");
    private final StringProperty caption = new SimpleStringProperty("");

    public StatCard() {
        getStyleClass().add("stat-card");

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("stat-title");
        title.addListener((obs, oldVal, newVal) -> titleLabel.setText(newVal == null ? "" : newVal.toUpperCase()));

        Label valueLabel = new Label();
        valueLabel.getStyleClass().add("stat-value");
        valueLabel.textProperty().bind(value);

        Label captionLabel = new Label();
        captionLabel.getStyleClass().add("stat-caption");
        captionLabel.textProperty().bind(caption);

        getChildren().addAll(titleLabel, valueLabel, captionLabel);
    }

    public StatCard(String title, String value, String caption) {
        this();
        setTitle(title);
        setValue(value);
        setCaption(caption);
    }

    public String getTitle() { return title.get(); }
    public void setTitle(String text) { title.set(text); }
    public StringProperty titleProperty() { return title; }

    public String getValue() { return value.get(); }
    public void setValue(String text) { value.set(text); }
    public StringProperty valueProperty() { return value; }

    public String getCaption() { return caption.get(); }
    public void setCaption(String text) { caption.set(text); }
    public StringProperty captionProperty() { return caption; }
}
