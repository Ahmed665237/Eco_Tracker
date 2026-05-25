
package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;

public class PrimaryController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField amountField;
    @FXML private CheckBox recycledCheckBox;
    @FXML private Button calculateButton;

    private String selectedCategory;

    private static final Map<String, List<String>> CATEGORY_OPTIONS = Map.of(
            "Transport",   List.of("Car", "Bus", "Metro", "Motorcycle", "Taxi", "Train", "Electric Car", "Bike/Walk"),
            "Electricity", List.of("AC/Heater", "Tech Devices", "Kitchen Appliances", "Fan"),
            "Water",       List.of("Shower", "Laundry", "Dishwashing", "Watering Plants", "Car Washing"),
            "Food",        List.of("Beef", "Chicken", "Fish", "Vegetables", "Rice", "Dairy", "Fast Food"),
            "Waste",       List.of("Plastic Bottle", "Plastic Bag", "Paper", "Glass", "Battery", "Electronics"),

            );

    @FXML
    public void initialize() {
        calculateButton.setOnAction(e -> handleCalculate());
        recycledCheckBox.setVisible(false);
        recycledCheckBox.setManaged(false);
    }

    @FXML
    public void transportClicked() {
        selectCategory("Transport");
    }

    @FXML
    public void electricityClicked() {
        selectCategory("Electricity");
    }

    @FXML
    public void waterClicked() {
        selectCategory("Water");
    }

    @FXML
    public void foodClicked() {
        selectCategory("Food");
    }

    @FXML
    public void wasteClicked() {
        selectCategory("Waste");
    }

    private void selectCategory(String category) {
        selectedCategory = category;

        // Populate ComboBox with correct options for this category
        typeComboBox.getItems().setAll(CATEGORY_OPTIONS.get(category));
        typeComboBox.getSelectionModel().clearSelection();
        typeComboBox.setPromptText("Select type...");

        // Show recycled checkbox only for Waste
        boolean isWaste = "Waste".equals(category);
        recycledCheckBox.setVisible(isWaste);
        recycledCheckBox.setManaged(isWaste);
        recycledCheckBox.setSelected(false);

        amountField.clear();
    }

    private void handleCalculate() {
        if (selectedCategory == null) {
            showError("Please select a category first.");
            return;
        }

        String type = typeComboBox.getValue();
        if (type == null || type.isEmpty()) {
            showError("Please select a type from the dropdown.");
            return;
        }

        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            showError("Please enter a value in the amount field.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showError("Amount must be a valid number.");
            return;
        }

        if (amount < 0) {
            showError("Amount cannot be negative.");
            return;
        }

        if ("Food".equals(selectedCategory) && amount < 1) {
            showError("Servings must be at least 1.");
            return;
        }

        try {
            Activity activity = ActivityFactory.createActivity(
                    selectedCategory,
                    LocalDate.now(),
                    type,
                    amount,
                    recycledCheckBox.isSelected()
            );

            double co2 = activity.calculateCO2();
            String message = String.format(
                    "Category:  %s%nType:      %s%nCO\u2082 Emitted: %.4f kg",
                    selectedCategory, type, co2
            );
            new Alert(Alert.AlertType.INFORMATION, message).show();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}

