package com.example.demo;

import java.time.LocalDate;

public class WasteActivity extends Activity {
    private String wasteType;
    private int quantity;
    private boolean recycled;

    public WasteActivity(LocalDate date, String wasteType, int quantity, boolean recycled) {
        super(date, "Waste");

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        this.wasteType = wasteType;
        this.quantity = quantity;
        this.recycled = recycled;
    }

    public String getWasteType() {
        return wasteType;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isRecycled() {
        return recycled;
    }

    @Override
    public double calculateCO2() {
        double basePerUnit = switch (wasteType) {
            case "Plastic Bottle" -> EmissionFactors.WASTE_PLASTIC_BOTTLE;
            case "Plastic Bag" -> EmissionFactors.WASTE_PLASTIC_BAG;
            case "Paper" -> EmissionFactors.WASTE_PAPER;
            case "Glass" -> EmissionFactors.WASTE_GLASS;
            case "Battery" -> EmissionFactors.WASTE_BATTERY;
            case "Electronics" -> EmissionFactors.WASTE_ELECTRONICS;
            default -> throw new IllegalArgumentException("Invalid selection: " + wasteType);
        };

        double total = basePerUnit * quantity;

        if (recycled) {
            return total * 0.3;
        }

        return total;
    }
}