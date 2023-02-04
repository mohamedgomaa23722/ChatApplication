package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.LoggedUser;
import gov.iti.presentation.validation.UserValidator;
import gov.iti.business.services.LoginService;
import gov.iti.business.services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

public class LoginPasswdController implements Initializable{

    UserValidator userValidator;

    String passwd;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";

    @FXML
    PasswordField passwdTextField;

    @FXML
    public void getUserPasswd() {

        boolean isPasswdValid;

        passwd=passwdTextField.getText();

        isPasswdValid=userValidator.validateUserPassWd(passwd);

        if(isPasswdValid) {
            // go to password sign in
            LoggedUser loggedUser=SceneManager.getSceneManagerInstance().getLoggedUser();
            loggedUser.setPasswd(passwd);
            if(LoginService.getLoginService().loginUser(loggedUser)) {
                //go to chat
                System.out.println("login sucessful");
            } else {
                // go to sign in page
                // show message some thing is wrong phone or password
                System.out.println("login failed");
                SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
            }
            
            // tranfer this object to server
            // not null load chat screen
            // null go to login phone screen

        } else {
            // password not valid
            passwdTextField.setStyle(error);
            System.out.println("password not valid");
        } 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        userValidator=UserValidator.getUserValidator();
        passwdTextField.setOnMouseClicked(e->passwdTextField.setStyle(ideal));
    }

    @FXML
    public void handelSignUp(){
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }
    
}