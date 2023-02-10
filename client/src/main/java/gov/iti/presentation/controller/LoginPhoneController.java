package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.dtos.LoggedUser;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserValidator;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginPhoneController implements Initializable{

    UserValidator userValidator;

    LoggedUser user;

    String phoneNumber;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: Arial;";

    @FXML
    TextField phoneTextField;

    @FXML
    Label wrongLogedLbl;

    BooleanProperty wrongMsg;

    public static BooleanProperty faildLogin;

    @FXML
    public void getUserPhone() {

        boolean isPhoneValid;

        phoneNumber=phoneTextField.getText();

        isPhoneValid=userValidator.validateUserPhoneNumber(phoneNumber);

        if(isPhoneValid) {
            // go to password sign in

            CurrentUser.getCurrentUser().setPhoneNumber(phoneNumber);
            SceneManager.getSceneManagerInstance().switchToPasswdLoginScreen();
        } else {
            // show error message
            // stay in this page
            showError("Enter Valid Phone Number",wrongLogedLbl);
            phoneTextField.setStyle(error);
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userValidator=UserValidator.getUserValidator();
        phoneTextField.setOnMouseClicked(e->phoneTextField.setStyle(ideal));
        wrongMsg.bind(faildLogin);
        wrongMsg.addListener(e->displyErrorLogin());
    }

    @FXML
    public void handelSignUp(){
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }

    @FXML
    public void displyErrorLogin() {
        wrongLogedLbl.setText("Incorrect UserName or Password");
        System.out.println("Incorrect UserName or Password");
        //wrongPasswdLbl.setBackground(new Image(getClass().getClassLoader().getResource("lock.png")));
        wrongLogedLbl.setStyle(loggedError);
        //visible="false"
        wrongLogedLbl.setVisible(true);
    }
    
    @FXML
    public void showError(String message, Label vBox){
        vBox.setVisible(true);
        vBox.setText(message);
        vBox.setStyle(loggedError);
    }
    
}