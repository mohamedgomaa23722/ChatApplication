package gov.iti.presentation.controller.settingsController;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.business.services.SettingsService;
import gov.iti.presentation.dtos.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ProfileSettingsController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private ImageView buttonAddImage;
    @FXML
    private ImageView buttonRemoveImage;
    @FXML
    private TextField newName;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newCountry;
    @FXML
    private TextField newBio;
    @FXML
    private Button buttonUpdate;

    @FXML
    private ComboBox<String> comboBoxCountry;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    private CurrentUser currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // comboBoxCountry.setItems(FXCollections.observableArrayList(UserValidator.country_list));
         currentUser = CurrentUser.getCurrentUser();
        newBio.setText(currentUser.getUser().getBio());
        newCountry.setText(currentUser.getUser().getCountry());
        newEmail.setText(currentUser.getUser().getEmail());
        newName.setText(currentUser.getUser().getName());
        System.out.println(currentUser.getUser().getImage().length);
    }

    @FXML
    void updateProfile(ActionEvent event) throws RemoteException {
        currentUser.getUser().setName(newName.getText());
        currentUser.getUser().setEmail(newEmail.getText());
        currentUser.getUser().setBio(newBio.getText());
        currentUser.getUser().setCountry("noooooooooooo");

        SettingsService.getInstance().updateProfile(currentUser.getUser());
    }
}
