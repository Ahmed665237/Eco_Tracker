package com.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class recController {

    @FXML private Label highestcategory;
    @FXML private Label RecommendationLabel;

    @FXML
    public void initialize() {
        highestcategory.setText(AppData.HighestCategory);
        RecommendationLabel.setText(AppData.Recommendation);
    }

    @FXML
    private void toResults(ActionEvent event) throws IOException {
        HelloApplication.switchScene("Results.fxml");  // use switchScene consistently
    }

    @FXML
    private void toHome(ActionEvent actionEvent) throws IOException {
        HelloApplication.switchScene("primary (2).fxml");
    }
}
