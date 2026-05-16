package com.example.demo;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddAccount {
    public static String addAccount(String firstName, String username, String password, ArrayList<String> files){
        boolean con= false;
        String result=UserValidations.inputValidations(username,"username");
        String result2=UserValidations.inputValidations(password,"password");
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
