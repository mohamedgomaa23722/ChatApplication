package gov.iti.presentation.controller.settingsController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.Utilities;
import gov.iti.business.services.SettingsService;
import gov.iti.presentation.dtos.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

public class passwordSettingsController implements Initializable {

    @FXML
    private PasswordField oldPassField;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField confirmNewPass;

    @FXML
    private Button submitBt;

    @FXML
    private Label oldPasswordFieldErrorLabel;

    @FXML
    private Label newPasswordFieldErrorLabel;

    @FXML
    private Label confirmPasswordFieldErrorLabel;

    @FXML 
    private Label changePasswordSuccess;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        oldPassField.setOnMouseClicked(e -> oldPassField.setStyle(ideal));
        confirmNewPass.setOnMouseClicked(e -> confirmNewPass.setStyle(ideal));
        newPass.setOnMouseClicked(e -> newPass.setStyle(ideal));

    }

    public boolean validateMatchedPassword() {
        if (Utilities.isMatchedPassword(newPass.getText(), confirmNewPass.getText())) {
            return true;
        }
        return false;
    }
    public boolean validatePassword() {
        if (Utilities.validateUserPassWd(newPass.getText())) {
            return true;
        }
        return false;
    }
     

    boolean isValid;
    boolean match;
    // if (!validatePassword()) {
    // isValid = false;
    // oldPassField.setStyle(error);
    // System.out.println("password error");
    // showError("Please Enter Password again", passwordErrorLabel);
    // showError("Please Enter Password again", cPasswordErrorLabel);

    // }

    @FXML
    void submitNewPassword() throws RemoteException {

        isValid = validatePassword();
        match = validateMatchedPassword();
        String phoneNumber = CurrentUser.getCurrentUser().getUser().getPhoneNumber();

        boolean isChanged = SettingsService.getInstance().changePassword(phoneNumber, oldPassField.getText(),
                newPass.getText());

        if (!isValid) {
            newPass.setStyle(error);
            
            System.out.println("password error");
            showError(
                    "Password must contain at least 8 charachters",
                    newPasswordFieldErrorLabel);
        }
        else if(!match){
            
            confirmNewPass.setStyle(error);
            System.out.println("confirm password error");
            showError(
                    "Password does not match entered password",
                    confirmPasswordFieldErrorLabel);
        }

        else if (!isChanged) {
            oldPassField.setStyle(error);
            System.out.println("old password error");
            showError("Wrong password!", oldPasswordFieldErrorLabel);

        } else {
            changePasswordSuccess.setText("Password Changed Successfully!");
            newPass.clear();
            oldPassField.clear();
            confirmNewPass.clear();
        }

    }

    public void showError(String message, Label vBox) {
        vBox.setVisible(true);
        vBox.setText(message);
    }
}
