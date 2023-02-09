package gov.iti.presentation.controller.settingsController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.business.services.SceneManager;
import gov.iti.business.services.SettingsService;
import gov.iti.model.User;
import gov.iti.presentation.validation.UserValidator;
import gov.iti.Utilities;
import gov.iti.Utilities;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

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
        comboBoxCountry.setItems(FXCollections.observableArrayList(UserValidator.country_list));
        // newName.textProperty().bind(SceneManager.currentUser.getName());
    }

    @FXML
    void updateProfile(ActionEvent event) {
        User updatedUser = new User(SceneManager.currentUser);
        // if (validateAll()) {
            updatedUser.setName(newName.getText());
            updatedUser.setEmail(newEmail.getText());
            updatedUser.setBio(newBio.getText());
            updatedUser.setCountry(comboBoxCountry.getValue());
        // }

        if (settingsService.updateProfile(updatedUser))
            SceneManager.currentUser = updatedUser;
    }

    public boolean validateAll() {
        if (!Utilities.validateName(newName.getText())) {
            System.out.println("not valid user name ");
            return false;
        } else {
            System.out.println("valid name");
        }
        if (!Utilities.validateEmail(newEmail.getText())) {
            System.out.println("not valid user name ");
            return false;
        } else {
            System.out.println("valid email");
        }
        if (comboBoxCountry.getSelectionModel().isEmpty()) {
            System.out.println("not valid country ");
            return false;
        } else {
            System.out.println("valid Country");
        }
        return true;
    }
}
