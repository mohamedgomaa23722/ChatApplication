package gov.iti.presentation.controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.ChatManager;
import gov.iti.presentation.utils.Configuration;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.UserInfo;
import gov.iti.presentation.utils.UserValidator;
import gov.iti.presistance.connection.ClientServerConnection;
import gov.iti.business.services.ContactsService;
import gov.iti.business.services.GroupService;
import gov.iti.business.services.InvitationService;
import gov.iti.business.services.LoginService;
import gov.iti.business.services.SettingsService;
import gov.iti.model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginPasswdController implements Initializable {

    UserValidator userValidator;

    String passwd;

    static boolean saved = true;

    public static StringProperty passwdValue = new SimpleStringProperty();

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: Arial;";

    @FXML
    PasswordField passwdTextField;

    @FXML
    Label wrongPassLbl;

    @FXML
    public void getUserPasswd() {

        CurrentUser.getCurrentUser().setPassword(passwdTextField.getText());

        User user;
        if (ClientServerConnection.reConnect() && (user = LoginService.getLoginService().loginUser()) != null) {
            wrongPassLbl.setText("");
            // go to chat
            System.out.println("login sucessful  : " + user.getStatus() + " : " + user.getPhoneNumber());
            CurrentUser.getCurrentUser().setUser(user);
            SceneManager.getSceneManagerInstance().switchToChatScreen();

            Platform.runLater(() -> {
                CurrentUser.getCurrentUser().setInvitations(InvitationService.getInstance().getInvitations());
                CurrentUser.getCurrentUser().setContacts(ContactsService.getcontactsService().getContacts());
                CurrentUser.getCurrentUser().setgroups(GroupService.getGroupService().getContactGroups());
                ChatManager.getInstance().addContacts();
                ChatManager.getInstance().addGroups();
            });
            new Thread(() -> {
                try {
                    SettingsService.getInstance().changeStatus(user.getPhoneNumber(), 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();

            if (!saved) {
                // save phone and passwd
                saveUserInfo(CurrentUser.getCurrentUser().getPhoneNumber().getValue(),
                        CurrentUser.getCurrentUser().getPassword());
                LoginPasswdController.passwdValue.set(CurrentUser.getCurrentUser().getPassword());
            }
        } else {
            // go to sign in page
            // show message some thing is wrong phone or password
            System.out.println("login failed" + passwd);
            LoginPhoneController.setFail(true);
            SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userValidator = UserValidator.getUserValidator();
        passwdTextField.setOnMouseClicked(e -> passwdTextField.setStyle(ideal));
        String passwd = UserInfo.getUserInfo().getSavedUserPasswd();
        if (passwd != null) {
            passwdTextField.setText(passwd);
            passwdValue.set(passwd);
            saved = true;
        } else {
            saved = false;
        }

        passwdValue.addListener((o, oldVal, newVal) -> {
            System.out.println("passwd changing in sign out");
            if (newVal.toString() == "") {
                passwdTextField.setText(passwdValue.getValue());
            }
        });
    }

    @FXML
    public void handelSignUp() {
        SceneManager.getSceneManagerInstance().switchToSignUpScreen();
    }

    public void showError(String message, Label vBox) {
        vBox.setVisible(true);
        vBox.setText(message);
        vBox.setStyle(loggedError);
    }

    void saveUserInfo(String phone, String passwd) {
        Configuration.createConfFile(phone, passwd);
    }

}