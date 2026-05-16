package com.example.demo;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String city;
    private String name;
    private String lifeStyleType;
    private ArrayList<Activity> activities;

    public User(
            String username,
            String password,
            String name,
            String city,
            String lifeStyleType,
            ArrayList<Activity> activities
    ) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lifeStyleType = lifeStyleType;
        this.city = city;
        this.activities = activities;
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

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getLifeStyleType() {
        return lifeStyleType;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public double calculateTotalCO2() {
        double total = 0;

        for (Activity activity : activities) {
            total += activity.calculateCO2();
        }

        return total;
    }
}
