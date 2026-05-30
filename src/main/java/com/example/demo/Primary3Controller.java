package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;

public class Primary3Controller {

    // ── Category cards ────────────────────────────────────────────────────────
    @FXML private VBox transportVBox;
    @FXML private VBox ElectricityVBox;
    @FXML private VBox WaterVBox;
    @FXML private VBox WasteVBox;
    @FXML private VBox FoodVBox;
    @FXML private VBox OtherVBox;

    // ── Input controls ────────────────────────────────────────────────────────
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField        amountField;
    @FXML private CheckBox         recycledCheckBox;
    @FXML private Label            resultCO2Label;
    @FXML private Button           nextPageButton;
    @FXML private Button           calculateButton;

    // ── Card styles ───────────────────────────────────────────────────────────
    private static final String CARD_NORMAL =
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: green;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 10;" +
            "-fx-cursor: hand;" +
            "-fx-effect: none;";

    private static final String CARD_HOVER =
            "-fx-background-color: #e8f5e9;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #2e7d32;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 10;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,150,0,0.30), 14, 0, 0, 5);";

    private static final String CARD_SELECTED =
            "-fx-background-color: #c8e6c9;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #1b5e20;" +
            "-fx-border-width: 2.5;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 10;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,100,0,0.45), 18, 0, 0, 6);";

    // ── State ─────────────────────────────────────────────────────────────────
    private String selectedCategory = null;
    private VBox   selectedCard     = null;

    // ─────────────────────────────────────────────────────────────────────────
    // Category click handlers
    // ─────────────────────────────────────────────────────────────────────────

    @FXML
    public void transportClicked() {
        selectCard(transportVBox, "Transport",
                "Car", "Bus", "Metro", "Motorcycle", "Taxi", "Train", "Electric Car", "Bike/Walk");
    }

    @FXML
    public void electricityClicked() {
        selectCard(ElectricityVBox, "Electricity",
                "AC/Heater", "Tech Devices", "Kitchen Appliances", "Fan");
    }

    @FXML
    public void waterClicked() {
        selectCard(WaterVBox, "Water",
                "Shower", "Laundry", "Dishwashing", "Watering Plants", "Car Washing");
    }

    @FXML
    public void wasteClicked() {
        selectCard(WasteVBox, "Waste",
                "Plastic Bottle", "Plastic Bag", "Paper", "Glass", "Battery", "Electronics");
    }

    @FXML
    public void foodClicked() {
        selectCard(FoodVBox, "Food",
                "Beef", "Chicken", "Fish", "Vegetables", "Rice", "Dairy", "Fast Food");
    }

    @FXML
    public void otherClicked() {
        selectCard(OtherVBox, "Other", "General Activity");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Hover effects
    // ─────────────────────────────────────────────────────────────────────────

    @FXML
    public void onCardHover(MouseEvent e) {
        VBox card = (VBox) e.getSource();
        if (card != selectedCard) {
            card.setStyle(CARD_HOVER);
        }
    }

    @FXML
    public void onCardExit(MouseEvent e) {
        VBox card = (VBox) e.getSource();
        if (card != selectedCard) {
            card.setStyle(CARD_NORMAL);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Calculate
    // ─────────────────────────────────────────────────────────────────────────

    @FXML
    private void handleCalculate() {
        try {
            String type = typeComboBox.getValue();
            String amountText = amountField.getText().trim();
            boolean recycled = recycledCheckBox.isSelected();

            if (selectedCategory == null) {
                showAlert("Please select a category first.");
                return;
            }
            if (type == null || type.isBlank()) {
                showAlert("Please select a type of activity.");
                return;
            }
            if (amountText.isEmpty()) {
                showAlert("Please enter a value.");
                return;
            }

            double amount = Double.parseDouble(amountText);

            Activity activity = ActivityFactory.createActivity(
                    selectedCategory,
                    LocalDate.now(),
                    type,
                    amount,
                    recycled
            );

            double co2 = activity.calculateCO2();
            animateResult(co2);

        } catch (NumberFormatException ex) {
            showAlert("Please enter a valid number in the value field.");
        } catch (Exception ex) {
            showAlert("Something went wrong: " + ex.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Next page navigation
    // ─────────────────────────────────────────────────────────────────────────

    @FXML
    private void handleNextPage() {
        try {
            // ── Replace "nextPage.fxml" with your actual next screen filename ──
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/nextPage.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) nextPageButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Next page not found.\nUpdate the FXML path in handleNextPage().");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Selects a card: highlights it, resets the previous one, loads combo items. */
    private void selectCard(VBox card, String category, String... items) {
        // Reset previous
        if (selectedCard != null) {
            selectedCard.setStyle(CARD_NORMAL);
        }

        // Pop animation
        ScaleTransition pop = new ScaleTransition(Duration.millis(100), card);
        pop.setFromX(1.0); pop.setFromY(1.0);
        pop.setToX(1.06);  pop.setToY(1.06);
        pop.setAutoReverse(true);
        pop.setCycleCount(2);
        pop.play();

        card.setStyle(CARD_SELECTED);
        selectedCard     = card;
        selectedCategory = category;

        typeComboBox.getItems().setAll(items);
        typeComboBox.setValue(null);
        typeComboBox.setPromptText("Choose an activity…");

        // Reset result when category changes
        resultCO2Label.setText("0.00");
    }

    /** Smoothly animates the CO2 label from its current value to the target. */
    private void animateResult(double target) {
        double start;
        try {
            start = Double.parseDouble(resultCO2Label.getText());
        } catch (NumberFormatException e) {
            start = 0.0;
        }

        final double from  = start;
        final int    steps = 30;
        final double delta = (target - from) / steps;
        final int[]  frame = {0};

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(16), e -> {
                    frame[0]++;
                    resultCO2Label.setText(
                            String.format("%.2f", from + delta * frame[0])
                    );
                })
        );
        timeline.setCycleCount(steps);
        timeline.setOnFinished(e ->
                resultCO2Label.setText(String.format("%.2f", target))
        );
        timeline.play();
    }

    /** Shows a simple warning alert. */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

