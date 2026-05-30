package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ResController {
    @FXML public Label username;
    @FXML public Label lowestCategory;
    @FXML public Label totalScore;
    @FXML public Label totalCO2;
    @FXML public Label HighestCategory;
    @FXML public AnchorPane anchorpane;
    @FXML public Button home;
    @FXML public Button tip;
    @FXML private ProgressBar scoreProgressBar;

    @FXML
    public void initialize() {
        double score = AppData.Score;
        double progress = score / 100.0;
        scoreProgressBar.setProgress(progress);
        username.setText(AppData.name);
        HighestCategory.setText(AppData.HighestCategory);
        lowestCategory.setText(AppData.LowestCategory);
        totalScore.setText(String.valueOf(AppData.Score));
        totalCO2.setText(String.valueOf(AppData.totalCO2));
    }

    public void GoToHome(ActionEvent actionEvent) throws IOException {
        HelloApplicationDemoRepeated.switchScene("primary (2).fxml");
    }

    public void toTip(ActionEvent actionEvent) throws IOException {
        HelloApplicationDemoRepeated.switchScene("recommendations.fxml");
    }
}

