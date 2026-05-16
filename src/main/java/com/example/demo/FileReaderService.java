package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
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
}
