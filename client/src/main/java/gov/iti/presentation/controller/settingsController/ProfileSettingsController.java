package gov.iti.presentation.controller.settingsController;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.business.services.SceneManager;
import gov.iti.business.services.SettingsService;
import gov.iti.model.User;
import gov.iti.presentation.validation.UserValidator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ProfileSettingsController implements Initializable {

    @FXML
    private ImageView buttonAddImage;

    @FXML
    private ImageView buttonRemoveImage;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Circle circle;

    @FXML
    private TextField newBio;

    @FXML
    private TextField newCountry;

    @FXML
    private TextField newEmail;

    @FXML
    private TextField newName;

    @FXML
    private ComboBox<String> comboBoxCountry;

    SettingsService settingsService;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsService = new SettingsService(); 
        // comboBoxCountry.setItems(FXCollections.observableArrayList(UserValidator.country_list));
    }

    @FXML
    void updateProfile(ActionEvent event) {
        User newUser = new User(SceneManager.currentUser);

        newUser.setName(newName.getText());
        newUser.setEmail(newEmail.getText());
        newUser.setBio(newBio.getText());
        newUser.setCountry("noooooooooooo");

        settingsService.updateProfile(newUser);
    }
}
