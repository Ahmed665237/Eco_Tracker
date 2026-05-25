package com.example.demo;

import java.util.ArrayList;

 public class UserValidations{
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
        if(ip.length()>20)
            return name+" is too long";
        if(ip.length()<4)
            return name+" is too short";
        return null;
    } // validations before checking if the account exists or not

    public static String nameValidation(String firstName){
        boolean con=false;
        if(firstName.length()==0)
            return "you have to enter chararacters in name";
        for(int i=0;i<firstName.length();i++){
            if(Character.isDigit(firstName.charAt(i)))// a real name cant contain numbers
                con=true;
        }
        if(con==true)
            return "name cannot contain numbers";
        if(firstName.length()>15)
            return "name cannot be more than 15 characters";
        return null;
    }

    public static String accountExists(String username, ArrayList<String> files){
        for(int i=0;i<files.size();i++){
            String [] f=files.get(i).split(",");
            if(f[1].equals(username))
                return "account already exists";
        }
        return null;
    }// using string seperation to reach the username only

    public static String numberValidation(String input, String name) {
        if (input.length() == 0) {
            return "you have to enter a number in " + name;
        }
        try {
            double number = Double.parseDouble(input);
            if (number <= 0) {
                return name + " must be greater than zero";
            }
            return null;
        } catch (NumberFormatException e) {
            return name + " must be a valid number";
        }
    } /*this method takes a doable turns it to string to check if it is an actual number or not
    and turns it to doable for the rest of the validatation*/

    public static String checkUser(String username, String password, ArrayList<String > files){
        int k=0; // this is used to make sure this is the password of the exact username
        boolean check=false,con=false;
        for(int i=0;i<files.size();i++){
            String [] f=files.get(i).split(",");
            if(f[1].equals(username)) {
                check = true;
                k=i; // k is used to loop till this part of the csv and not all saving time
            }
        }
        // checking if user exits in csv file
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
