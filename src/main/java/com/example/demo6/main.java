package com.example.demo6;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;


//Every activity has a date, ID, and a category it belongs to
abstract class Activity {
    protected final String activityID;
    protected final String category; //"Transport", "Water", etc.
    protected final LocalDate date;

    public Activity(LocalDate date, String category) {
        this.activityID = UUID.randomUUID().toString(); //Universally Unique Identifier ID
        if (date == null || category == null)
            throw new IllegalArgumentException("Null values not allowed");
        this.category = category;
        this.date = date;
    }

    public abstract double calculateCO2();

    //getters
    public String getActivityID() {
        return activityID;
    }
    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}

final class EmissionFactors{
    //Transport(kg per km)
    public static final double CAR         = 0.21;
    public static final double BUS         = 0.089;
    public static final double METRO       = 0.041;
    public static final double MOTORCYCLE  = 0.12;
    public static final double TAXI        = 0.25;
    public static final double TRAIN       = 0.05;
    public static final double ELECTRIC_CAR = 0.08;

    //Electricity(kg per kWh)
    public static final double GRID_KWH    = 0.233;

    //Device wattage estimates
    public static final double WATTS_AC_HEATER   = 2000;
    public static final double WATTS_TECH         = 800;
    public static final double WATTS_KITCHEN      = 1600;
    public static final double WATTS_FAN          = 75;
    public static final double WATTS_DEFAULT      = 100;

    // Water(kg per litre)
    public static final double WATER_KG_PER_LITRE = 0.0003;

    //Food(kg COâ‚‚ per serving)
    public static final double FOOD_BEEF      = 27.0;
    public static final double FOOD_CHICKEN   = 6.9;
    public static final double FOOD_FISH      = 5.0;
    public static final double FOOD_VEG       = 0.5;
    public static final double FOOD_RICE      = 2.7;
    public static final double FOOD_DAIRY     = 3.0;
    public static final double FOOD_FAST_FOOD = 5.5;

    // Waste(kg COâ‚‚ per unit)
    public static final double WASTE_PLASTIC_BOTTLE = 0.1;
    public static final double WASTE_PLASTIC_BAG    = 0.04;
    public static final double WASTE_PAPER          = 0.02;
    public static final double WASTE_GLASS          = 0.03;
    public static final double WASTE_BATTERY        = 0.8;
    public static final double WASTE_ELECTRONICS    = 1.0;
}

//5 Subclasses (Inheritance is widely used in this part)
class TransportationActivity extends Activity {
    private String vehicleType;
    private double distanceKm; //make sure that the user inputs distance in (km)

    public TransportationActivity(LocalDate date, String vehicleType, double distanceKm){
        super(date, "Transport");
        if (distanceKm < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        this.vehicleType=vehicleType;
        this.distanceKm=distanceKm;
    }

    public String getVehicleType(){
        return vehicleType;}

    public double getDistanceKm(){
        return distanceKm;}

    @Override
    public double calculateCO2(){
        switch (vehicleType) {
            case "Car":
                return distanceKm * EmissionFactors.CAR;

            case "Bus":
                return distanceKm * EmissionFactors.BUS;

            case "Metro":
                return distanceKm * EmissionFactors.METRO;

            case "Motorcycle":
                return distanceKm * EmissionFactors.MOTORCYCLE;

            case "Taxi":
                return distanceKm * EmissionFactors.TAXI;

            case "Train":
                return distanceKm * EmissionFactors.TRAIN;

            case "Electric Car":
                return distanceKm * EmissionFactors.ELECTRIC_CAR;

            case "Bike/Walk":
                return 0.0;

            default:
                throw new IllegalArgumentException("Invalid selection: " + vehicleType);
        }
    }
}
class ElectricityActivity extends Activity {
    private String deviceType;
    private double hoursUsed;

    public ElectricityActivity(LocalDate date, String deviceType, double hoursUsed){
        super(date, "Electricity");
        if (hoursUsed < 0)
            throw new IllegalArgumentException("Hours used cannot be negative");
        this.deviceType = deviceType;
        this.hoursUsed = hoursUsed;

    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setHoursUsed(double hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    @Override
    public double calculateCO2() {
        double watts;

        switch (this.deviceType) {
            case "AC/Heater":
                watts = EmissionFactors.WATTS_AC_HEATER;
                break;

            case "Tech Devices":
                watts = EmissionFactors.WATTS_TECH;
                break;

            case "Kitchen Appliances":
                watts = EmissionFactors.WATTS_KITCHEN;
                break;

            case "Fan":
                watts = EmissionFactors.WATTS_FAN;
                break;

            default:
                watts = EmissionFactors.WATTS_DEFAULT;
                break;
        }
        double kwh=(watts/ 1000.0) * hoursUsed;
        return kwh * EmissionFactors.GRID_KWH; // CO2 factor //Every 1kwh produces about 0.233kg of CO2
    }
}
class WaterActivity extends Activity{
    private String usageType;
    private int durationMinutes;
    public WaterActivity(LocalDate date, String usageType, int durationMinutes){
        super(date,"Water");
        if (durationMinutes < 0)
            throw new IllegalArgumentException("Duration cannot be negative");
        this.usageType=usageType;
        this.durationMinutes=durationMinutes;
    }

    public String getUsageType() {
        return usageType;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public double calculateCO2(){
        double litresUsed;

        switch (usageType) {
            case "Shower":
                litresUsed = durationMinutes * 8.0;
                break;

            case "Laundry":
                litresUsed = 60.0; // fixed: 60 liters per wash cycle
                break;

            case "Dishwashing":
                litresUsed = durationMinutes * 6.0;
                break;

            case "Watering Plants":
                litresUsed = durationMinutes * 10.0;
                break;

            case "Car Washing":
                litresUsed = durationMinutes * 15.0;
                break;

            default:
                throw new IllegalArgumentException("Invalid selection: " + usageType);
        }
        //convert water usage to CO2
        return litresUsed*EmissionFactors.WATER_KG_PER_LITRE;
    }
}
class WasteActivity extends Activity{
    private String wasteType;
    private int quantity;
    private boolean recycled;

    public WasteActivity(LocalDate date, String wasteType, int quantity, boolean recycled){
        super(date,"Waste");
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");
        this.wasteType=wasteType;
        this.quantity=quantity;
        this.recycled=recycled;
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
    public double calculateCO2(){
        double basePerUnit;

        switch (wasteType) {
            case "Plastic Bottle":
                basePerUnit = EmissionFactors.WASTE_PLASTIC_BOTTLE;
                break;

            case "Plastic Bag":
                basePerUnit = EmissionFactors.WASTE_PLASTIC_BAG;
                break;

            case "Paper":
                basePerUnit = EmissionFactors.WASTE_PAPER;
                break;

            case "Glass":
                basePerUnit = EmissionFactors.WASTE_GLASS;
                break;

            case "Battery":
                basePerUnit = EmissionFactors.WASTE_BATTERY;
                break;

            case "Electronics":
                basePerUnit = EmissionFactors.WASTE_ELECTRONICS;
                break;

            default:
                throw new IllegalArgumentException("Invalid selection: " + wasteType);
        }
        double total = basePerUnit * quantity;
        if(recycled){
            return total*0.3; //reduce emissions
        }
        else{
            return total;
        }
    }
}
class FoodActivity extends Activity{
    private String mealType;
    private int servings;

    public FoodActivity(LocalDate date, String mealType, int servings){
        super(date, "Food");
        if (servings <= 0)
            throw new IllegalArgumentException("Servings must be greater than zero");
        this.mealType=mealType;
        this.servings=servings;
    }

    public String getMealType() {
        return mealType;
    }

    public int getServings() {
        return servings;
    }

    @Override
    public double calculateCO2(){
        double basePerServing;

        switch (mealType) {
            case "Beef":
                basePerServing = EmissionFactors.FOOD_BEEF;
                break;

            case "Chicken":
                basePerServing = EmissionFactors.FOOD_CHICKEN;
                break;

            case "Fish":
                basePerServing = EmissionFactors.FOOD_FISH;
                break;

            case "Vegetables":
                basePerServing = EmissionFactors.FOOD_VEG;
                break;

            case "Rice":
                basePerServing = EmissionFactors.FOOD_RICE;
                break;

            case "Dairy":
                basePerServing = EmissionFactors.FOOD_DAIRY;
                break;

            case "Fast Food":
                basePerServing = EmissionFactors.FOOD_FAST_FOOD;
                break;

            default:
                throw new IllegalArgumentException("Invalid selection: " + mealType);
        }

        return basePerServing * servings;
    }
}


class user { //
    private String username ;
    private String password ;
    private String city;
    private String name ;
    private String LifeStyleType ;
    private static ArrayList<Activity> activities  ;

    public user(String username, String password, String name, String city, String LifeStyleType, ArrayList<Activity> arr) {
        this.username = username;
        this.password = password ;
        this.name = name ;
        this.LifeStyleType = LifeStyleType ;
        this.city = city ;
        this.activities = arr ;
    }

    public static ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLifeStyleType() {
        return LifeStyleType;
    }

    public void setLifeStyleType(String lifeStyleType) {
        LifeStyleType = lifeStyleType;
    }

    public void addActivity (Activity activity) {
        activities.add(activity) ;
    }

    public void removeActivity (Activity activity)
    {
        activities.remove(activity) ;
    }

    public double CalculateTotalC02 () //calculates total carbon footprint per day
    {
        double total=0;
        for (Activity a : activities )
        {
            total += a.calculateCO2() ;
        }
        return total ;
    }

}

class EcoCalculator {

    int score = 0;
    public double CalculateScore (user user) {
        double totalCO2 = user.CalculateTotalC02();
        if (totalCO2 < 5.5 && totalCO2 > 0) {
            score = 100;
            System.out.println("Your Sustainability score is: " + score);
        } else if (totalCO2 < 13.7 && totalCO2 > 5.5) {
            score = 80;
            System.out.println("Your Sustainability score is: " + score);
        } else if (totalCO2 < 27.4 && totalCO2 > 13.7) {
            score = 60;
            System.out.println("Your Sustainability score is: " + score);
        } else if (totalCO2 < 41.1 && totalCO2 > 27.4) {
            score = 40;
            System.out.println("Your Sustainability score is: " + score);
        } else if (totalCO2 < 54.8 && totalCO2 > 41.1) {
            score = 20;
            System.out.println("Your Sustainability score is: " + score);
        } else {
            System.out.println("Your carbon footprint is way too high");
        }
        return score ;
    }


    public String getHighestCategory (user user)
    {
        double transportation = 0 ;
        double electricity = 0;
        double water = 0;
        double waste = 0 ;
        double food = 0;

        for ( Activity a : user.getActivities() )
        {
            String category = a.getCategory() ;
            if (category.equals("Transportation"))
            {
                transportation+= a.calculateCO2() ;
            }
            if (category.equals("Electricity"))
            {
                electricity+= a.calculateCO2() ;
            }
            if (category.equals("Water"))
            {
                water+= a.calculateCO2() ;
            }
            if (category.equals("Waste"))
            {
                waste+= a.calculateCO2() ;
            }
            if (category.equals("Food"))
            {
                food+= a.calculateCO2() ;
            }

        }
        double highest = transportation;
        String highestCategory = "Transportation";
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

    public String getLowestCategory (user user)
    {
        double transportation = 0 ;
        double electricity = 0;
        double water = 0;
        double waste = 0 ;
        double food = 0;

        for ( Activity a : user.getActivities() )
        {
            String category = a.getCategory() ;
            if (category.equals("Transportation"))
            {
                transportation+= a.calculateCO2() ;
            }
            if (category.equals("Electricity"))
            {
                electricity+= a.calculateCO2() ;
            }
            if (category.equals("Water"))
            {
                water+= a.calculateCO2() ;
            }
            if (category.equals("Waste"))
            {
                waste+= a.calculateCO2() ;
            }
            if (category.equals("Food"))
            {
                food+= a.calculateCO2() ;
            }

        }
        double lowest = transportation;
        String lowestCategory = "Transportation";
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

        public String recommendationsGenerator (user user)
        {
            String category = getHighestCategory(user) ;
            if (score==10)
                System.out.println();
            if (category.equals("Transportation")) {
                return "Try using public transport or walking.";
            }
            else if (category.equals("Food")) {
                return "Try reducing meat meals this week.";
            }
            else if (category.equals("Electricity")) {
                return "Try reducing electricity usage.";
            }
            else if (category.equals("Water")) {
                return "Try reducing water consumption.";
            }
            else if (category.equals("Waste")) {
                return "Try recycling more plastic items.";
            }
            return "Good job maintaining your eco habits.";
        }
}



