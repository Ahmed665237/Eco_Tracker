package com.example.demo;

import java.time.LocalDate;

public class WaterActivity extends Activity {
    private String usageType;
    private int durationMinutes;

    public WaterActivity(LocalDate date, String usageType, int durationMinutes) {
        super(date, "Water");

        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        this.usageType = usageType;
        this.durationMinutes = durationMinutes;
    }

    public String getUsageType() {
        return usageType;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public double calculateCO2() {
        double litresUsed = switch (usageType) {
            case "Shower" -> durationMinutes * 8.0;
            case "Laundry" -> 60.0;
            case "Dishwashing" -> durationMinutes * 6.0;
            case "Watering Plants" -> durationMinutes * 10.0;
            case "Car Washing" -> durationMinutes * 15.0;
            default -> throw new IllegalArgumentException("Invalid selection: " + usageType);
        };

        return litresUsed * EmissionFactors.WATER_KG_PER_LITRE;
    }
}