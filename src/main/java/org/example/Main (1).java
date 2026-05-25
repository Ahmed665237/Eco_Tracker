package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javafx.application.Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

//BASE ABSTRACT CLASS
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
//EMISSION CONSTANTS
final class EmissionFactors{
    //Transport(kg per km)
    public static final double CAR= 0.21;
    public static final double BUS= 0.089;
    public static final double METRO= 0.041;
    public static final double MOTORCYCLE= 0.12;
    public static final double TAXI= 0.25;
    public static final double TRAIN= 0.05;
    public static final double ELECTRIC_CAR= 0.08;

    //Electricity(kg per kWh)
    public static final double GRID_KWH= 0.233;

    //Device wattage estimates
    public static final double WATTS_AC_HEATER= 2000;
    public static final double WATTS_TECH= 800;
    public static final double WATTS_KITCHEN= 1600;
    public static final double WATTS_FAN= 75;
    public static final double WATTS_DEFAULT= 100;

    // Water(kg per litre)
    public static final double WATER_KG_PER_LITRE= 0.0003;

    //Food(kg COâ‚‚ per serving)
    public static final double FOOD_BEEF= 27.0;
    public static final double FOOD_CHICKEN= 6.9;
    public static final double FOOD_FISH= 5.0;
    public static final double FOOD_VEG= 0.5;
    public static final double FOOD_RICE= 2.7;
    public static final double FOOD_DAIRY= 3.0;
    public static final double FOOD_FAST_FOOD= 5.5;

    // Waste(kg COâ‚‚ per unit)
    public static final double WASTE_PLASTIC_BOTTLE= 0.1;
    public static final double WASTE_PLASTIC_BAG= 0.04;
    public static final double WASTE_PAPER= 0.02;
    public static final double WASTE_GLASS= 0.03;
    public static final double WASTE_BATTERY= 0.8;
    public static final double WASTE_ELECTRONICS= 1.0;
}
//ACTIVITY SUBCLASSES
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
        return switch (vehicleType) {
            case "Car"->distanceKm * EmissionFactors.CAR;
            case "Bus"->distanceKm * EmissionFactors.BUS;
            case "Metro"->distanceKm * EmissionFactors.METRO;
            case "Motorcycle"->distanceKm * EmissionFactors.MOTORCYCLE;
            case "Taxi"->distanceKm * EmissionFactors.TAXI;
            case "Train"-> distanceKm * EmissionFactors.TRAIN;
            case "Electric Car"->distanceKm * EmissionFactors.ELECTRIC_CAR;
            case "Bike/Walk"->0.0;
            default -> throw new IllegalArgumentException("Invalid selection: " + vehicleType);
        };
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
        double watts= switch (this.deviceType) {
            case "AC/Heater"->EmissionFactors.WATTS_AC_HEATER;
            case "Tech Devices"->EmissionFactors.WATTS_TECH;
            case "Kitchen Appliances"->EmissionFactors.WATTS_KITCHEN;
            case "Fan"->EmissionFactors.WATTS_FAN;
            default ->EmissionFactors.WATTS_DEFAULT;
        }
                ;
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
        double litresUsed= switch(usageType){
            case "Shower" -> durationMinutes*8.0;
            case "Laundry" -> 60.0; //fixed: 60 liters per wash cycle
            case "Dishwashing" -> durationMinutes * 6.0;
            case "Watering Plants" -> durationMinutes * 10.0;
            case "Car Washing" -> durationMinutes * 15.0;
            default -> throw new IllegalArgumentException("Invalid selection: " + usageType);
        };
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
        double basePerUnit= switch(wasteType){
            case "Plastic Bottle" ->EmissionFactors.WASTE_PLASTIC_BOTTLE;
            case "Plastic Bag" ->EmissionFactors.WASTE_PLASTIC_BAG;
            case "Paper" ->EmissionFactors.WASTE_PAPER;
            case "Glass" ->EmissionFactors.WASTE_GLASS;
            case "Battery" ->EmissionFactors.WASTE_BATTERY;
            case "Electronics" ->EmissionFactors.WASTE_ELECTRONICS;
            default -> throw new IllegalArgumentException("Invalid selection: " + wasteType);
        };
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
        return switch (mealType) {
            case "Beef" ->EmissionFactors.FOOD_BEEF;
            case "Chicken" ->EmissionFactors.FOOD_CHICKEN;
            case "Fish" ->EmissionFactors.FOOD_FISH;
            case "Vegetables" ->EmissionFactors.FOOD_VEG;
            case "Rice" ->EmissionFactors.FOOD_RICE;
            case "Dairy" ->EmissionFactors.FOOD_DAIRY;
            case "Fast Food" ->EmissionFactors.FOOD_FAST_FOOD;
            default -> throw new IllegalArgumentException("Invalid selection: " + mealType);
        }*servings;
    }
}
//FACTORY
class ActivityFactory {
     // Creates and returns the correct Activity subclass based on category.
//NE3ML DROPDOWNS BOXES IN JAVAFX SO THAT THE USER CANT INPUT SOMETHING WRONG //GYI SHOULD SHOW THE RIGHT LABEL
//DISTANCE -> KM / DURATION -> MINUTES
     //category     "Transport", "Electricity", "Water", "Food", "Waste"
     //date          the date of the activity (use LocalDate.now() from the GUI)
     //type          the specific type within the category (e.g. "Car", "Shower", "Beef")
     //amount        distance (km), hours used, duration (minutes), or servings — as a double //
     //recycled      only used for Waste; pass false for every other category
     //return              an Activity object ready to call .calculateCO2()
    //no reason to create an object from ActivityFactory
    public static Activity createActivity(String category, LocalDate date,
                                          String type, double amount, boolean recycled) {
        return switch (category) {
            case "Transport"    -> new TransportationActivity(date, type, amount);
            case "Electricity"  -> new ElectricityActivity(date, type, amount);
            case "Water"        -> new WaterActivity(date, type, (int) amount);
            case "Food"         -> new FoodActivity(date, type, (int) amount);
            case "Waste"        -> new WasteActivity(date, type, (int) amount, recycled);
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }
}


class user { //
    private String username ;
    private String password ;
    private String city;
    private String name ;
    private String LifeStyleType ;
    private ArrayList<Activity> activities  ;

    public user(String username, String password, String name, String city, String LifeStyleType, ArrayList<Activity> arr) {
        this.username = username;
        this.password = password ;
        this.name = name ;
        this.LifeStyleType = LifeStyleType ;
        this.city = city ;
        this.activities = arr ;
    }

    public ArrayList<Activity> getActivities() {
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
            System.out.println("Your Sustainibilty score is: " + score);
        } else if (totalCO2 < 13.7 && totalCO2 > 5.5) {
            score = 80;
            System.out.println("Your Sustainibilty score is: " + score);
        } else if (totalCO2 < 27.4 && totalCO2 > 13.7) {
            score = 60;
            System.out.println("Your Sustainibilty score is: " + score);
        } else if (totalCO2 < 41.1 && totalCO2 > 27.4) {
            score = 40;
            System.out.println("Your Sustainibilty score is: " + score);
        } else if (totalCO2 < 54.8 && totalCO2 > 41.1) {
            score = 20;
            System.out.println("Your Sustainibilty score is: " + score);
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
            if (category.equals("Transport"))
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

    public String getLowestCategrory(user user)
    {
        double transportation = 0 ;
        double electricity = 0;
        double water = 0;
        double waste = 0 ;
        double food = 0;

        for ( Activity a : user.getActivities() )
        {
            String category = a.getCategory() ;
            if (category.equals("Transport"))
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

    public String recommendationsGenerator (user user)
    {
        String category = getHighestCategory(user) ;
        if (score==10)
            System.out.println();
        if (category.equals("Transport")) {
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
class userValidations{
    public static String inputValidations(String ip,String name){
        boolean con=true,check=false;
        if(ip.length()==0)
            return "you have to enter chararacters in "+name;
        for(int i=0;i<ip.length();i++){
            if(ip.charAt(i)==' ')
                return "no spaces in "+name;
        }
        for(int i=0;i<ip.length();i++){
            if(!Character.isDigit(ip.charAt(i)))
                con=false; // to check that there are no numbers only
        }
        if(con==true)
            return name+" cannot contain digits ONLY!";
        if(ip.length()>12)
            return name+" is too long";
        if(ip.length()<4)
            return name+" is too short";
        return null;
    } // validations before checking if the account exists or not

    public static String checkuser(String username, String password, ArrayList<String > files){
        int k=0; // this is used to make sure this is the password of the exact username
        boolean check=false,con=false;
        for(int i=0;i<files.size();i++){
            String [] f=files.get(i).split(",");
            if(f[1].equals(username)) {
                check = true;
                k=i;
            }
        }
        if(!check)
            return "incorrect username ";
        else{
            for(int j=0;j<files.size();j++){
                String [] f=files.get(j).split(",");
                if(f[2].equals(password)&&k==j)
                    con=true;
            }
            if(!con)
                return "incorrect password";
            else
                return null;
        }
    }
    // this is a method to check if the username and passwords are in the csv file
}



class addAccount {
    public static String addAccount(String firstName, String username, String password, ArrayList<String> files){
        boolean con= false;
        String result=userValidations.inputValidations(username,"username");
        String result2=userValidations.inputValidations(password,"password");
        for(int i=0;i<firstName.length();i++){
            if(Character.isDigit(firstName.charAt(i)))// a real name cant contain numbers
                con=true;
        }
        if(result2!=null&&result!=null&&con==true)
            return result+"\n"+result2+"\n"+"name cannot contain numbers";
        if(result2!=null&&result!=null&&firstName.length()>15)
            return result+"\n"+result2+"\n"+" name cannot be more than 15 characters";
        if(con==true)
            return "name cannot contain numbers";
        if(firstName.length()>15)
            return "name cannot be more than 15 characters";
        if(result2!=null&&result!=null)
            return result+"\n"+result2;
        if(result!=null)
            return result;
        if(result2!=null)
            return result2;
        String newUser= firstName+","+username+","+password;
        files.add(newUser);
        // these are if statements to also validate userinput from the userValidations class
        // and also a new validation for the users actual name
        try {
            FileWriter writer = new FileWriter("src//users.csv"); // object to rewrite the file
            writer.write("name,username,password\n"); // writing the first part of the file tittles
            for (int i=0;i<files.size();i++) {
                writer.write(files.get(i)+"\n"); // after updating the arraylist we write it in the new file
            }

            writer.close();
        } catch(IOException e){
            return "error saving user to file";
        }
        return null;
    }
}


 class Filereader{
    private String filename;
    private ArrayList<String> filestorer=new ArrayList<>();
    public Filereader(String filename){
        this.filename=filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }
    public ArrayList<String> readFile(){ // it returns an array list
        // stores location of file
        BufferedReader br = null; // class that reads the csv file
        String line="";
        try {
            br = new BufferedReader(new FileReader(filename));// reads the file
            br.readLine();// to skip first line // this reads the full csv file line
            while ((line=br.readLine())!=null){ // to prevent repetition
                filestorer.add(line);
            }// this adds the csv file info in the array list

            // readline() reads one full line from the file
            // it is stored inside line
            // when there are no more line the loop stops
            // split means after each comma take the string and put it in and index
            // in one loop
        } catch (Exception e) {
            e.printStackTrace(); // to say what went wrong
        }
        return filestorer;
    }
}










