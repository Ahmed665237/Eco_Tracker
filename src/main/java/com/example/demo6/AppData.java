package com.example.demo6;

public class AppData { //A class that gets the data from the backend to be later used in multiple screens
    public static user CurrentUser ; //the current user using the app
    public static EcoCalculator calculator = new EcoCalculator() ; //creating an pbject of class ecocalculator
    // to be able to invoke the gethighestcategory and recommendationsGenerator method

    //Variables to store the methods results:
    public static double totalCO2  ;
    public static String name ;
    public static double Score  ; //total score
    public static String HighestCategory  ;
    public static String LowestCategory  ;
    public static String Recommendation  ;

    //method to calculate and get values for these variables
    public static void GetALL () {
        totalCO2 = CurrentUser.CalculateTotalC02() ;
        Score = calculator.CalculateScore(CurrentUser) ;
        HighestCategory = calculator.getHighestCategory(CurrentUser);
        LowestCategory = calculator.getLowestCategory(CurrentUser);
        Recommendation = calculator.recommendationsGenerator(CurrentUser);
        name = CurrentUser.getName() ;
    }



}


