package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.LoggedUser;
import gov.iti.presentation.validation.UserValidator;
import gov.iti.business.services.SceneManager;
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

        if(isPhoneValid) {
            // go to password sign in
            user = new LoggedUser();
            user.setPhone(phoneNumber);
            SceneManager.getSceneManagerInstance().switchToPasswdLoginScreen(user);
        } else {
            // show error message
            // stay in this page
            phoneTextField.setStyle(error);
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        userValidator=UserValidator.getUserValidator();
        phoneTextField.setOnMouseClicked(e->phoneTextField.setStyle(ideal));
    }

    @FXML
    public void handelSignUp(){
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }
    
}