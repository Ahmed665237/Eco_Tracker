package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderService {
    private String filename;
    private ArrayList<String> filestorer=new ArrayList<>();
    public FileReaderService(String filename){
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

    public static String saveCO2(String username, int choice, double co2, ArrayList<String> co2Files){
        int categoryIndex=choice+1;
        for(int i=0;i<co2Files.size();i++){
            String [] f=co2Files.get(i).split(",");
            if(f[1].equals(username)){
                double categoryCO2=Double.parseDouble(f[categoryIndex])+co2;
                double totalCO2=Double.parseDouble(f[7])+co2;
                f[categoryIndex]=String.valueOf(categoryCO2);
                f[7]=String.valueOf(totalCO2);
                String newLine=f[0]+","+f[1]+","+f[2]+","+f[3]+","+f[4]+","+f[5]+","+f[6]+","+f[7];
                co2Files.set(i,newLine);
            }
        }
        try {
            FileWriter writer = new FileWriter("src//main//java//com//example//demo//C02Calculated.csv");
            writer.write("name,username,transportationCO2,electricityCO2,waterCO2,foodCO2,wasteCO2,totalCO2\n");
            for (int i=0;i<co2Files.size();i++) {
                writer.write(co2Files.get(i)+"\n");
            }
            writer.close();
        } catch (IOException e) {
            return "error saving CO2 to file";
        }
        return null;
    }
}
