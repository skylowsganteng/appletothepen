package com.appletothepen;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1180, 760);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Simulation Console");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(980);
        primaryStage.setMinHeight(640);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
