package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserValidator;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;

public class ConnectionPageController implements Initializable{

    UserValidator userValidator;

    String ip;

    @FXML
    TextField phoneTextField;


    @FXML
    public void connect() {

        ip=phoneTextField.getText().trim();

        ClientServerConnection.ipAddress=ip;
        
        SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // phoneTextField.setText("01111111113");
        userValidator=UserValidator.getUserValidator();
         
    }

}