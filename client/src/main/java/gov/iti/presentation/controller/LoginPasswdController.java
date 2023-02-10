package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserValidator;
import gov.iti.business.services.LoginService;
import gov.iti.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginPasswdController implements Initializable{

    UserValidator userValidator;

    String passwd;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: Arial;";

    @FXML
    PasswordField passwdTextField;

    @FXML
    Label wrongPassLbl; 

    @FXML
    public void getUserPasswd() {

        boolean isPasswdValid;

        passwd=passwdTextField.getText();

        isPasswdValid=userValidator.validateUserPassWd(passwd);

        if(isPasswdValid) {
            // go to password sign in
            CurrentUser.getCurrentUser().setPassword(passwd);
            User user;
            if(( user = LoginService.getLoginService().loginUser()) != null) {
                //go to chat
                System.out.println("login sucessful  : " + user.getStatus() + " : "+ user.getPhoneNumber());
                CurrentUser.getCurrentUser().setUser(user);
                SceneManager.getSceneManagerInstance().switchToChatScreen();
            } else {
                // go to sign in page
                // show message some thing is wrong phone or password
                System.out.println("login failed" + passwd);
                //SceneManager.getSceneManagerInstance().setLoginFaild(true);
                
                SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
            }
            
            // tranfer this object to server
            // not null load chat screen
            // null go to login phone screen 

        } else {
            // password not valid
            passwdTextField.setStyle(error);
            System.out.println("password not valid");
            showError("Enter Valid Password 8-10 characters \n characters,numbers and special character", wrongPassLbl);
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

    public void showError(String message, Label vBox){
        vBox.setVisible(true);
        vBox.setText(message);
        vBox.setStyle(loggedError);
    }
    
}