package gov.iti.presentation.controller.settingsController;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.business.services.SettingsService;
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

    SettingsService settingsService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsService = new SettingsService();
    }

    @FXML
    private void changeStatus(){
        int status = 1;
        if(statusGroup.getSelectedToggle().equals(availableRadioButton)) {
            status = 1;
        } else if(statusGroup.getSelectedToggle().equals(busyRadioButton)) {
            status = 2;
        } else {
            status = 3;
        }
        //TODO: GET PHONE NUMBER
        settingsService.changeStatus(null, status);
    }
}
