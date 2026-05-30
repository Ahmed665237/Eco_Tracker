package com.example.demo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
// real deal
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class Launcher extends Application{
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 700;
    private static Stage mainStage;
    private static String currentAccountName; // where we will store the number for each user

    @FXML
    private TextField usernameField; // the username you enter  which you login
    @FXML
    private PasswordField passwordField; // this is the password field in login
    @FXML
    private TextField visiblePasswordField;
    // when you open the eye in login this will make what you wrote appear
    @FXML
    private Label loginmessage; // message to recieve after clicking login
    @FXML
    private TextField newNameField; // txt field where you type your name
    @FXML
    private TextField newUsernameField; // the new username of the new account you will create
    @FXML
    private PasswordField newPasswordField;// the new password of the new account you will create
    @FXML
    private Label addAccountMessage; // message that will appear after pressing addaccount button
    @FXML
    private TextField visibleNewPasswordField;
    // this is also the text field in login that will appear by pressing the eye
    // @FSXML is put above variables and they are named
    // based on their fxml id
    @FXML
    @Override
    public  void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/welcome.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }// this loads the welcome scene
    @FXML
    public void initialize() { // method runs before screen is created
        if (visiblePasswordField != null && passwordField != null) {
            // the if statement checks if these fields are only that scene to prevent error
            visiblePasswordField.setVisible(false); // this hides normal txt field
            visiblePasswordField.setManaged(false); // tells java to not treat the hidden part as a layout
            visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
            // this part is for the login part
        }
        // this begins by showing the password field to reach txt field->click the eye
        if (visibleNewPasswordField != null && newPasswordField != null) {
            visibleNewPasswordField.setVisible(false);
            visibleNewPasswordField.setManaged(false);
            visibleNewPasswordField.textProperty().bindBidirectional(newPasswordField.textProperty());
            // this part is for the add account
        }
        /*this makes what is written in the password field to be written in the txt field
        * textproperty  is what  is inside the text or password field and bindbidriectional
        * is what is like saying write what is being written in the passwordfield*/
    }

    @FXML
    public void togglePasswordVisibility(ActionEvent event) {
        // method runs when clicking on the eye
        boolean showPassword = !visiblePasswordField.isVisible();
        // changes its old state when clicked its either true or false
        visiblePasswordField.setVisible(showPassword);
        visiblePasswordField.setManaged(showPassword);
        passwordField.setVisible(!showPassword);
        passwordField.setManaged(!showPassword);
        // one has to show and the other disapears
    }
    @FXML
    public void toggleNewPasswordVisibility(ActionEvent event) {
        boolean showPassword = !visibleNewPasswordField.isVisible();
        visibleNewPasswordField.setVisible(showPassword);
        visibleNewPasswordField.setManaged(showPassword);
        newPasswordField.setVisible(!showPassword);
        newPasswordField.setManaged(!showPassword);
    }
// same method for to check password visibilty but in add account
    public void Welcome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/SceneBuilder.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void loginButton(ActionEvent event){
        loginmessage.setText("");

        FileReaderService fr = new FileReaderService("src//users.csv");
        ArrayList<String> files = fr.readFile();
        // this is where files are read and put in an array list
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        String passwordResult = UserValidations.inputValidations(password, "password");
        // called method: inputValidations(...) from UserValidations.java
        String usernameResult = UserValidations.inputValidations(username, "username");
        // called method: inputValidations(...) from UserValidations.java

        if(passwordResult!=null&&usernameResult!=null){
            loginmessage.setText(usernameResult+"\n"+passwordResult);
            loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
            usernameField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10");
            passwordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            visiblePasswordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            return;
        }
        if(usernameResult!=null){
            passwordField.setStyle("-fx-border-color:  #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            visiblePasswordField.setStyle("-fx-border-color: #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            loginmessage.setText(usernameResult);
            loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
            usernameField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10");
            return;
        }
        if(passwordResult!=null){
            usernameField.setStyle("-fx-border-color:  #DADADA ;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10");
            loginmessage.setText(passwordResult);
            loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
            passwordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            visiblePasswordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            return;
        }

        String loginResult = UserValidations.checkUser(username, password, files);
        // called method: checkUser(...) from UserValidations.java

        if(loginResult!=null){
            usernameField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10");
            passwordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            visiblePasswordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            loginmessage.setText(loginResult);
            loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }
        usernameField.setStyle("-fx-border-color:  #DADADA ;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10");
        passwordField.setStyle("-fx-border-color:  #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
        visiblePasswordField.setStyle("-fx-border-color: #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
        loginmessage.setText("Logged in successfully!");
        loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        try {
            showActivityScreen(getNameFromUsername(username, files));
        } catch (Exception e) {
            loginmessage.setText("could not open activity screen");
            loginmessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
        }
    }
    /* this method happens upon clicking the button when
    * it is clicked based on user input the static mathods are made
    * on the username and password to validate and the if statements display
    * the message based on user input */
    @FXML
    
    public void addAccountButton(ActionEvent event){ // this will be studied starting from here
        FileReaderService fr = new FileReaderService("src//users.csv");
        ArrayList<String> files = fr.readFile();

        String name = newNameField.getText();
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        String result = AddAccount.addAccount(name, username, password, files);
        // called method: addAccount(...) from AddAccount.java

        if(result!=null){
            addAccountMessage.setText(result);
            addAccountMessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
            newNameField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            newUsernameField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            newPasswordField.setStyle("-fx-border-color: red;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
            return;
        }
        // addAccountMessage is a label
        newNameField.setStyle("-fx-border-color:#DADADA ;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
        newUsernameField.setStyle("-fx-border-color: #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
        newPasswordField.setStyle("-fx-border-color: #DADADA;-fx-border-radius:10;-fx-background-radius: 10;-fx-padding: 10 ");
        addAccountMessage.setText("account created");
        addAccountMessage.setStyle("-fx-font-size: 13; -fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        try {
            showActivityScreen(name);
        } catch (Exception e) {
            addAccountMessage.setText("account created but activity screen could not open");
            addAccountMessage.setStyle("-fx-font-size: 13; -fx-text-fill: red; -fx-font-weight: bold;");
        }
    }// my part till activity screen

    private void showActivityScreen(String accountName) throws IOException {
        currentAccountName = accountName;
        showActivityScreen();
        // this method shows the screen
    }

    public static void showActivityScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/resources/org/example/primary (3).fxml").toUri().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        // this is a fn which loads the screen after the login or add account
        // shrouq

        Launcher launcher = new Launcher();
        // create new launcher in order to call conncecCo2saving
        ArrayList<Object> screenParts = new ArrayList<>();
        // created array list to  store the screen parts
        screenParts.add(fxmlLoader.getNamespace().get("transportVBox"));
        screenParts.add(fxmlLoader.getNamespace().get("ElectricityVBox"));
        screenParts.add(fxmlLoader.getNamespace().get("WaterVBox"));
        screenParts.add(fxmlLoader.getNamespace().get("FoodVBox"));
        screenParts.add(fxmlLoader.getNamespace().get("WasteVBox"));
        screenParts.add(fxmlLoader.getNamespace().get("OtherVBox"));
        // this adds the boxes on the screen
        screenParts.add(fxmlLoader.getNamespace().get("calculateButton"));
        screenParts.add(fxmlLoader.getNamespace().get("resultCO2Label"));
        screenParts.add(fxmlLoader.getNamespace().get("nextPageButton"));
        // the buttons are aslo added in the array list
        /*when the user selects a category  then presses the calculate button
        * the program reads the result from resultco2 label then it saves the result
        * under the account name*/
        launcher.connectCO2Saving(screenParts, currentAccountName);
        /*this is used when calc button is clicked and current accont name to know which
        * account to save under */
        // called method: connectCO2Saving(...) from Launcher.java

        mainStage.setScene(scene);
        mainStage.show();
    }

    private void connectCO2Saving(ArrayList<Object> screenParts, String accountName) {
        // Primary3Controller.java has the category methods and calculate method for this FXML.
        // called methods from Primary3Controller.java through the FXML:
        // transportClicked(), electricityClicked(), waterClicked(), foodClicked(), wasteClicked(), otherClicked(), handleCalculate()
        // Launcher keeps this small extra part because Primary3Controller does not save the result to C02Calculated.csv.
        final String[] selectedCategory = {null};
        // creates a place to store which category user clicked
        saveSelectedCategory((VBox) screenParts.get(0), "Transport", selectedCategory);
        saveSelectedCategory((VBox) screenParts.get(1), "Electricity", selectedCategory);
        saveSelectedCategory((VBox) screenParts.get(2), "Water", selectedCategory);
        saveSelectedCategory((VBox) screenParts.get(3), "Food", selectedCategory);
        saveSelectedCategory((VBox) screenParts.get(4), "Waste", selectedCategory);
        saveSelectedCategory((VBox) screenParts.get(5), "Other", selectedCategory);
        // all is put in the array whether it is selected or not
        Button calculateButton = (Button) screenParts.get(6);
        Label resultCO2Label = (Label) screenParts.get(7);
        Button nextPageButton = (Button) screenParts.get(8);
        // this adds also what is saved when button is clicked
        calculateButton.addEventHandler(ActionEvent.ACTION, actionEvent -> {
            // called method: handleCalculate() from Primary3Controller.java through the FXML onAction.
            // Launcher only adds the save-to-CSV part after that written controller method calculates the CO2.
            try {
                if (selectedCategory[0] == null) {
                    return;
                    // if user didnt select anything nothing happens
                }
                if (selectedCategory[0].equals("Other")) {
                    return;
                    // stop as it will not be saved in csv file
                }

                double co2 = Double.parseDouble(resultCO2Label.getText());
                // get the result on the screen from the label which is the get txt method
                // to store it in the csv file
                FileReaderService co2Reader = new FileReaderService("src//main//java//com//example//demo//C02Calculated.csv");
                String saveResult = FileReaderService.saveCO2(accountName, getCategoryChoice(selectedCategory[0]), co2, co2Reader.readFile());

                if (saveResult != null) {
                    showActivityAlert(saveResult);
                    // checks if there is an error message
                }
            } catch (NumberFormatException e) {
                // if there is then it throws a pop up
                showActivityAlert("Please enter a valid number in the value field.");
            } catch (Exception e) {
                showActivityAlert("Something went wrong: " + e.getMessage());
            }
        });

        nextPageButton.setOnAction(actionEvent -> {
            // Primary3Controller.java has handleNextPage(), but it opens /org/example/nextPage.fxml.
            // Launcher uses showResultsScreen() here because this project flow needs Results.fxml after the activity screen.
            try {
                currentAccountName = accountName;
                mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                showResultsScreen();
                // called method: showResultsScreen() from Launcher.java
            } catch (Exception e) {
                showActivityAlert("Could not open results screen.");
            }
        });
    }// shrouqs part in gui +linking of add account with her screen
    // my part in saving the results and updating it in the csv file

    public static void showResultsScreen() throws IOException {
        Launcher launcher = new Launcher();
        launcher.updateResultData(currentAccountName);
        // it updates the info of the account before showing it to the result screen

        // HelloApplicationDemoRepeated.java has switchScene(...).
        // It is not called here because that class stores its own private Stage, and Launcher is the app currently running.
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/resources/com/example/demo6/Results.fxml").toUri().toURL());
        Parent root = fxmlLoader.load();
        Button nextButton = (Button) fxmlLoader.getNamespace().get("tip");
        // this calls the tip button
        Button homeButton = (Button) fxmlLoader.getNamespace().get("home");
        // this calss the home button

        nextButton.setText("Next");
        // this changes the tip button to next and the place is also changed in results.fxml
        nextButton.setOnAction(event -> { // attaches code to run when button is clicked
            // ResController.java has toTip(...), but it calls HelloApplicationDemoRepeated.switchScene(...).
            // Launcher calls showTipScreen() so the same running Launcher stage is used.
            try {
                showTipScreen();
                // called method: showTipScreen() from Launcher.java
                // this opens the reccomended tips
            } catch (Exception e) {
                launcher.showActivityAlert("Could not open tip screen.");
            }
        });
        homeButton.setOnAction(event -> { //this makes code run when button is clicked
            // ResController.java has GoToHome(...), but it calls HelloApplicationDemoRepeated.switchScene(...).
            // Launcher calls showActivityScreen() so the same running Launcher stage is used.
            try {
                showActivityScreen();
                // called method: showActivityScreen() from Launcher.java
                // when home button is clicked it brings us back to the activity page
            } catch (Exception e) {
                launcher.showActivityAlert("Could not go back home.");
                // from the method of showing alert messages
            }
        });

        Scene scene = new Scene(root, SCENE_WIDTH, 900);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void showTipScreen() throws IOException {
        Launcher launcher = new Launcher();
        launcher.updateResultData(currentAccountName);
        // called method: updateResultData(...) from Launcher.java

        // recController.java has toResults(...) and toHome(...).
        // Those methods call HelloApplicationDemoRepeated.switchScene(...), so Launcher overrides these buttons to keep the same running Stage.
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/resources/com/example/demo6/recommendations.fxml").toUri().toURL());
        Parent root = fxmlLoader.load();
        Button backToResultsButton = (Button) fxmlLoader.getNamespace().get("back2results");
        Button homeButton = (Button) fxmlLoader.getNamespace().get("back");

        backToResultsButton.setOnAction(event -> {
            // recController.java has toResults(...), but Launcher uses showResultsScreen() to keep the same stage.
            try {
                showResultsScreen();
                // called method: showResultsScreen() from Launcher.java
            } catch (Exception e) {
                launcher.showActivityAlert("Could not open results screen.");
            }
        });
        homeButton.setOnAction(event -> {
            // recController.java has toHome(...), but Launcher uses showActivityScreen() to keep the same stage.
            try {
                showActivityScreen();
                // called method: showActivityScreen() from Launcher.java
            } catch (Exception e) {
                launcher.showActivityAlert("Could not go back home.");
            }
        });

        Scene scene = new Scene(root, SCENE_WIDTH, 900);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void updateResultData(String accountName) {
        String line = findCO2Line(accountName);
        // called method: findCO2Line(...) from Launcher.java
        if (line == null) {
            AppData.name = accountName;
            AppData.totalCO2 = 0;
            AppData.Score = 0;
            AppData.HighestCategory = "None";
            AppData.LowestCategory = "None";
            AppData.Recommendation = "Start adding activities to get your first tip.";
            return;
        }

        AppData.CurrentUser = makeUserFromCO2Line(accountName, line);
        // called method: makeUserFromCO2Line(...) from Launcher.java
        AppData.GetALL();
        // called method: GetALL() from AppData.java
        // GetALL() calls these written methods:
        // CalculateTotalC02() from User.java
        // CalculateScore(...), getHighestCategory(...), getLowestCategory(...), recommendationsGenerator(...) from EcoCalculator.java
    }

    private User makeUserFromCO2Line(String accountName, String line) {
        // User.java and AppData.GetALL() are used here to reuse the written total, score, highest, lowest, and tip methods.
        // The repeated activity files now use the original Activity.java base class.
        String[] data = line.split(",");
        double transport = getNumber(data, 1);
        double electricity = getNumber(data, 2);
        double water = getNumber(data, 3);
        double food = getNumber(data, 4);
        double waste = getNumber(data, 5);

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(new SavedActivity("Transport", transport));
        activities.add(new SavedActivity("Electricity", electricity));
        activities.add(new SavedActivity("Water", water));
        activities.add(new SavedActivity("Food", food));
        activities.add(new SavedActivity("Waste", waste));

        return new User("", "", accountName, "", "", activities);
    }

    private String findCO2Line(String accountName) {
        FileReaderService co2Reader = new FileReaderService("src//main//java//com//example//demo//C02Calculated.csv");
        ArrayList<String> lines = co2Reader.readFile();
        // called method: readFile() from FileReaderService.java
        for (String line : lines) {
            String[] data = line.split(",");
            if (data.length > 0 && data[0].equals(accountName)) {
                return line;
            }
        }
        return null;
    }

    private double getNumber(String[] data, int index) {
        if (data.length <= index) {
            return 0;
        }
        try {
            return Double.parseDouble(data[index]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void saveSelectedCategory(VBox card, String category, String[] selectedCategory) {
        card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectedCategory[0] = category);
    }

    private int getCategoryChoice(String category) {
        return switch (category) {
            case "Transport" -> 1;
            case "Electricity" -> 2;
            case "Water" -> 3;
            case "Food" -> 4;
            case "Waste" -> 5;
            default -> 0;
        };
    }

    private String getNameFromUsername(String username, ArrayList<String> files) {
        for (String line : files) {
            String[] userData = line.split(",");
            if (userData.length >= 2 && userData[1].equals(username)) {
                return userData[0];
            }
        }
        return username;
    }

    private void showActivityAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // method thad makes alert messages written in catch

    private static class SavedActivity extends Activity {
        // This small class is only used to hold the CO2 values already saved in C02Calculated.csv.
        // The normal activity calculation methods need raw inputs like type and amount, but the results screen only has saved CO2 totals.
        private final double co2;

        public SavedActivity(String category, double co2) {
            super(LocalDate.now(), category);
            this.co2 = co2;
        }

        @Override
        public double calculateCO2() {
            return co2;
        }
    }// malaks part in gui and connecting her screen with shrouq
    // my part in updating the csv file
    @FXML
    public void showAddAccount(MouseEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/AddAccount.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    // this is where when i press the the signup it takes me to the account

    @FXML
    public void showLogin(MouseEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/SceneBuilder.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    // this takes me back to the login page when i click the label

}// this is my part last two methods
