package com.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        mainStage.setTitle("EcoTracker");

        // This is the first screen that opens
        switchScene("Results.fxml");

        mainStage.show();
    }

    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/demo6/" + fxmlFile)
        );
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
        mainStage.setScene(scene);
    }
}