package com.example.demo;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddAccount {
    public static String addAccount(String firstName, String username, String password, ArrayList<String> files){
        FileReaderService co2Reader = new FileReaderService("src//main//java//com//example//demo//C02Calculated.csv");
        ArrayList<String> co2Files = co2Reader.readFile();

        String result=UserValidations.inputValidations(username,"username");
        String result2=UserValidations.inputValidations(password,"password");
        String result3=UserValidations.nameValidation(firstName);
        String result4=UserValidations.accountExists(username,files);
        if(result3!=null&&result!=null&&result2!=null)
            return result3+"\n"+result+"\n"+result2;
        if(result3!=null&&result!=null)
            return result3+"\n"+result;
        if(result3!=null&&result2!=null)
            return result3+"\n"+result2;
        if(result2!=null&&result!=null)
            return result+"\n"+result2;
        if(result3!=null)
            return result3;
        if(result!=null)
                return result;
        if(result2!=null)
            return result2;
        if(result4!=null)
            return result4;
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

        String co2Result=FileReaderService.addCO2Account(firstName,co2Files);
        if(co2Result!=null)
            return co2Result;
        return null;
    }
}
