package com.example.demo;

public class EcoCalculator {
    private int score = 0;

    public double calculateScore(User user) {
        double totalCO2 = user.calculateTotalCO2();

        if (totalCO2 < 5.5 && totalCO2 > 0) {
            score = 100;
        } else if (totalCO2 < 13.7 && totalCO2 > 5.5) {
            score = 80;
        } else if (totalCO2 < 27.4 && totalCO2 > 13.7) {
            score = 60;
        } else if (totalCO2 < 41.1 && totalCO2 > 27.4) {
            score = 40;
        } else if (totalCO2 < 54.8 && totalCO2 > 41.1) {
            score = 20;
        } else {
            score = 0;
        }

        return score;
    }

    public String getHighestCategory(User user) {
        double transport = 0;
        double electricity = 0;
        double water = 0;
        double waste = 0;
        double food = 0;

        for (Activity activity : user.getActivities()) {
            switch (activity.getCategory()) {
                case "Transport" -> transport += activity.calculateCO2();
                case "Electricity" -> electricity += activity.calculateCO2();
                case "Water" -> water += activity.calculateCO2();
                case "Waste" -> waste += activity.calculateCO2();
                case "Food" -> food += activity.calculateCO2();
            }
        }

        double highest = transport;
        String highestCategory = "Transport";

        if (electricity > highest) {
            highest = electricity;
            highestCategory = "Electricity";
        }

        if (water > highest) {
            highest = water;
            highestCategory = "Water";
        }

        if (waste > highest) {
            highest = waste;
            highestCategory = "Waste";
        }

        if (food > highest) {
            highest = food;
            highestCategory = "Food";
        }

        return highestCategory;
    }

    public String generateRecommendation(User user) {
        String category = getHighestCategory(user);

        return switch (category) {
            case "Transport" -> "Try using public transport or walking.";
            case "Food" -> "Try reducing meat meals this week.";
            case "Electricity" -> "Try reducing electricity usage.";
            case "Water" -> "Try reducing water consumption.";
            case "Waste" -> "Try recycling more plastic items.";
            default -> "Good job maintaining your eco habits.";
        };
    }

    public double CalculateScore(User user) {
        return calculateScore(user);
    }

    public String getLowestCategory(User user) {
        double transport = 0;
        double electricity = 0;
        double water = 0;
        double waste = 0;
        double food = 0;

        for (Activity activity : user.getActivities()) {
            switch (activity.getCategory()) {
                case "Transport" -> transport += activity.calculateCO2();
                case "Electricity" -> electricity += activity.calculateCO2();
                case "Water" -> water += activity.calculateCO2();
                case "Waste" -> waste += activity.calculateCO2();
                case "Food" -> food += activity.calculateCO2();
            }
        }

        double lowest = transport;
        String lowestCategory = "Transport";

        if (electricity < lowest) {
            lowest = electricity;
            lowestCategory = "Electricity";
        }
        if (water < lowest) {
            lowest = water;
            lowestCategory = "Water";
        }
        if (waste < lowest) {
            lowest = waste;
            lowestCategory = "Waste";
        }
        if (food < lowest) {
            lowest = food;
            lowestCategory = "Food";
        }

        return lowestCategory;
    }

    public String recommendationsGenerator(User user) {
        return generateRecommendation(user);
    }
}
