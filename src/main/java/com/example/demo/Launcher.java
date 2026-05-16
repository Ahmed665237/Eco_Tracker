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
                String amountInput = input.nextLine();
                String amountResult = UserValidations.numberValidation(amountInput, "distance");
                if (amountResult != null) {
                    System.out.println(amountResult);
                    return;
                }
                double amount = Double.parseDouble(amountInput);

                activity = ActivityFactory.createActivity("Transport", LocalDate.now(), type, amount, false);
            } else if (choice == 2) {
                System.out.println("Device type: AC/Heater / Tech Devices / Kitchen Appliances / Fan");
                String type = input.nextLine();

                System.out.println("Hours used:");
                String amountInput = input.nextLine();
                String amountResult = UserValidations.numberValidation(amountInput, "hours used");
                if (amountResult != null) {
                    return;
                }
                double amount = Double.parseDouble(amountInput);

                activity = ActivityFactory.createActivity("Electricity", LocalDate.now(), type, amount, false);
            } else if (choice == 3) {
                System.out.println("Enter type: Shower / Laundry / Dishwashing / Watering Plants / Car Washing");
                String type = input.nextLine();

                System.out.println("Enter duration in minutes:");
                String amountInput = input.nextLine();
                String amountResult = UserValidations.numberValidation(amountInput, "duration");
                if (amountResult != null) {
                    return;
                }
                double amount = Double.parseDouble(amountInput);

                activity = ActivityFactory.createActivity("Water", LocalDate.now(), type, amount, false);
            } else if (choice == 4) {
                System.out.println("Enter meal: Beef / Chicken / Fish / Vegetables / Rice / Dairy / Fast Food");
                String type = input.nextLine();

                System.out.println("Enter number of servings:");
                String amountInput = input.nextLine();
                String amountResult = UserValidations.numberValidation(amountInput, "servings");
                if (amountResult != null) {
                    return;
                }
                double amount = Double.parseDouble(amountInput);

                activity = ActivityFactory.createActivity("Food", LocalDate.now(), type, amount, false);
            } else if (choice == 5) {
                System.out.println("Enter waste type: Plastic Bottle / Plastic Bag / Paper / Glass / Battery / Electronics");
                String type = input.nextLine();

                System.out.println("Enter quantity:");
                String amountInput = input.nextLine();
                String amountResult = UserValidations.numberValidation(amountInput, "quantity");
                if (amountResult != null) {
                    return;
                }
                double amount = Double.parseDouble(amountInput);
                /* the double amount = Double.parseDouble(amountInput); is used because if user inputs string
                * then he will crash the program so this means we will use the same try and catch block
                * in all the lines well lest turn it to stirng goes in the number validations method and reuturns
                * it to double again*/
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
