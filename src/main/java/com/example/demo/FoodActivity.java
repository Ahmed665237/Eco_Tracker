package com.example.demo;

import java.time.LocalDate;

public class FoodActivity extends Activity {
    private String mealType;
    private int servings;

    public FoodActivity(LocalDate date, String mealType, int servings) {
        super(date, "Food");

        if (servings <= 0) {
            throw new IllegalArgumentException("Servings must be greater than zero");
        }

        this.mealType = mealType;
        this.servings = servings;
    }

    public String getMealType() {
        return mealType;
    }

    public int getServings() {
        return servings;
    }

    @Override
    public double calculateCO2() {
        return switch (mealType) {
            case "Beef" -> EmissionFactors.FOOD_BEEF;
            case "Chicken" -> EmissionFactors.FOOD_CHICKEN;
            case "Fish" -> EmissionFactors.FOOD_FISH;
            case "Vegetables" -> EmissionFactors.FOOD_VEG;
            case "Rice" -> EmissionFactors.FOOD_RICE;
            case "Dairy" -> EmissionFactors.FOOD_DAIRY;
            case "Fast Food" -> EmissionFactors.FOOD_FAST_FOOD;
            default -> throw new IllegalArgumentException("Invalid selection: " + mealType);
        } * servings;
    }
}
