package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.dtos.LoggedUser;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class LoginPhoneController implements Initializable{

    UserValidator userValidator;

    LoggedUser user;

    String phoneNumber;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";

    @FXML
    TextField phoneTextField;

    @FXML
    public void getUserPhone() {

        boolean isPhoneValid;

        phoneNumber=phoneTextField.getText();

        isPhoneValid=userValidator.validateUserPhoneNumber(phoneNumber);

        // if(isPhoneValid) {
            // go to password sign in

            CurrentUser.getCurrentUser().setPhoneNumber(phoneNumber);
            SceneManager.getSceneManagerInstance().switchToPasswdLoginScreen();
        // } else {
        //     // show error message
        //     // stay in this page
        //     phoneTextField.setStyle(error);
        // }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userValidator=UserValidator.getUserValidator();
        phoneTextField.setOnMouseClicked(e->phoneTextField.setStyle(ideal));
    }

    @FXML
    public void handelSignUp(){
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }
    
}