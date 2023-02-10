package gov.iti.presentation.controller.settingsController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.business.services.SettingsService;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class StatusSettingController implements Initializable{
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private RadioButton availableRadioButton;
    @FXML
    private RadioButton busyRadioButton;
    @FXML
    private RadioButton awayRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Set current status
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        System.out.println(currentUser.getStatus().get());
        currentUser.getStatus().addListener((o, old, newValue)->{
            System.out.println(old.intValue() +" : "+ newValue.intValue());
            switch(newValue.intValue()){
                case 1:
                availableRadioButton.setSelected(true);
                System.out.println("availableRadioButton");
                break;
                case 2:
                busyRadioButton.setSelected(true);
                System.out.println("busyRadioButton");
                break;
                case 3:
                awayRadioButton.setSelected(true);
                break;
            }
         });
    }

    @FXML
    private void changeStatus() throws RemoteException{
        int status = 1;
        if(statusGroup.getSelectedToggle().equals(availableRadioButton)) {
            status = 1;
        } else if(statusGroup.getSelectedToggle().equals(busyRadioButton)) {
            status = 2;
        } else {
            status = 3;
        }
        String phoneNumber = CurrentUser.getCurrentUser().getUser().getPhoneNumber();
        SettingsService.getInstance().changeStatus(phoneNumber, status);
    }
}
