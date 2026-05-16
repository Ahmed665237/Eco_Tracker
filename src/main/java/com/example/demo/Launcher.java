package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        FileReaderService fr = new FileReaderService("src//users.csv");
        ArrayList<String> files = fr.readFile();

        System.out.println("enter username");
        String username = input.nextLine();

        System.out.println("enter password");
        String password = input.nextLine();

        String passwordResult = UserValidations.inputValidations(password, "password");
        String usernameResult = UserValidations.inputValidations(username, "username");

        System.out.println(usernameResult);
        System.out.println(passwordResult);

        String loginResult = UserValidations.checkUser(username, password, files);

        if (passwordResult == null && usernameResult == null) {
            System.out.println(loginResult);
        }

        System.out.println("do you want to add an account");
        int i = input.nextInt();
        input.nextLine();

        if (i == 1) {
            System.out.println("enter your name");
            String name = input.nextLine();

            System.out.println("enter your username");
            String newUsername = input.nextLine();

            System.out.println("enter your password");
            String newPassword = input.nextLine();

            String result = AddAccount.addAccount(name, newUsername, newPassword, files);
            System.out.println(result);
        }

        if (loginResult == null) {
            System.out.println("Choose category:");
            System.out.println("1. Transport");
            System.out.println("2. Electricity");
            System.out.println("3. Water");
            System.out.println("4. Food");
            System.out.println("5. Waste");

            int choice = input.nextInt();
            input.nextLine();

            Activity activity = null;

            if (choice == 1) {
                System.out.println("Vehicle type: Car / Bus / Metro / Motorcycle / Taxi / Train / Electric Car / Bike/Walk");
                String type = input.nextLine();

                System.out.println("Distance in km:");
                double amount = input.nextDouble();

                activity = ActivityFactory.createActivity("Transport", LocalDate.now(), type, amount, false);
            } else if (choice == 2) {
                System.out.println("Device type: AC/Heater / Tech Devices / Kitchen Appliances / Fan");
                String type = input.nextLine();

                System.out.println("Hours used:");
                double amount = input.nextDouble();

                activity = ActivityFactory.createActivity("Electricity", LocalDate.now(), type, amount, false);
            } else if (choice == 3) {
                System.out.println("Enter type: Shower / Laundry / Dishwashing / Watering Plants / Car Washing");
                String type = input.nextLine();

                System.out.println("Enter duration in minutes:");
                double amount = input.nextDouble();

                activity = ActivityFactory.createActivity("Water", LocalDate.now(), type, amount, false);
            } else if (choice == 4) {
                System.out.println("Enter meal: Beef / Chicken / Fish / Vegetables / Rice / Dairy / Fast Food");
                String type = input.nextLine();

                System.out.println("Enter number of servings:");
                double amount = input.nextDouble();

                activity = ActivityFactory.createActivity("Food", LocalDate.now(), type, amount, false);
            } else if (choice == 5) {
                System.out.println("Enter waste type: Plastic Bottle / Plastic Bag / Paper / Glass / Battery / Electronics");
                String type = input.nextLine();

                System.out.println("Enter quantity:");
                double amount = input.nextDouble();

                System.out.println("Recycled? true/false:");
                boolean recycled = input.nextBoolean();

                activity = ActivityFactory.createActivity("Waste", LocalDate.now(), type, amount, recycled);
            }

            if (activity != null) {
                System.out.println("CO2 = " + activity.calculateCO2());
            }
        }
    }
}