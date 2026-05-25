package com.example.demo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
// real deal
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Launcher extends Application{
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
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/welcome.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
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
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
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
        String usernameResult = UserValidations.inputValidations(username, "username");

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
    }

    @FXML
    public void showAddAccount(MouseEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/AddAccount.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    // this is where when i press the the signup it takes me to the account

    @FXML
    public void showLogin(MouseEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/com/example/demo/SceneBuilder.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    // this takes me back to the login page when i click the label

}
