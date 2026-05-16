package com.example.demo;
import java.time.LocalDate;
public class ActivityFactory {

    public static Activity createActivity(
            String category,
            LocalDate date,
            String type,
            double amount,
            boolean recycled // this is like creating an object and the switch chooses them
            // after the category is entered in the main or written
            // it is chosen in the switch instead of declaring the object here
    ) {
        switch (category) {
            case "Transport":
                return new TransportationActivity(date,type,amount);
            case "Electricity":
                return new ElectricityActivity(date,type,amount);
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
}
