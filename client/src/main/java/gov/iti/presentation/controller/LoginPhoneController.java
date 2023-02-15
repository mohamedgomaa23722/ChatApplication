package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserInfo;
import gov.iti.presentation.utils.UserValidator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginPhoneController implements Initializable{

    UserValidator userValidator;

    String phoneNumber;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: Arial;";

    static BooleanProperty isFaildLogin = new SimpleBooleanProperty(false);

    @FXML
    TextField phoneTextField;

    @FXML
    Label wrongLogedLbl;


    @FXML
    public void getUserPhone() {

        boolean isPhoneValid;

        phoneNumber=phoneTextField.getText().trim();

        isPhoneValid=userValidator.validateUserPhoneNumber(phoneNumber);

         if(isPhoneValid) {
            // go to password sign in
            wrongLogedLbl.setText("");
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
        phoneTextField.setText("01111111113");
        userValidator=UserValidator.getUserValidator();
        phoneTextField.setOnMouseClicked(e->phoneTextField.setStyle(ideal));
        isFaildLogin.addListener((o,oldVal,newVal)->{
            System.out.println("change");
                 if(newVal.toString()=="true") {
                    System.out.println("password incorrect");
                    displyErrorLogin();
                 } else {
                    wrongLogedLbl.setVisible(false);
                    System.out.println("write");
                 }
          });
        String phone = UserInfo.getUserInfo().getSavedUserPhoneNumber();
        if(phone != null ) {
            phoneTextField.setText(phone);
        } 
    }

    @FXML
    public void handelSignUp(){
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }

    @FXML
    public void displyErrorLogin() {
        wrongLogedLbl.setVisible(true);
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

    
    static void setFail(boolean faild) {
        isFaildLogin.set(faild);
    }
}