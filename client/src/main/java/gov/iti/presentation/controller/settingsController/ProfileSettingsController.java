package gov.iti.presentation.controller.settingsController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.business.services.SettingsService;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxCountry.setItems(FXCollections.observableArrayList(Utilities.country_list));

        CurrentUser currentUser = CurrentUser.getCurrentUser();
        newBio.textProperty().bindBidirectional(currentUser.getBio());
        newName.textProperty().bindBidirectional(currentUser.getName());
        newEmail.textProperty().bindBidirectional(currentUser.getEmail());
        comboBoxCountry.setValue(currentUser.getCountry().get());
        // Image img = new Image(new ByteArrayInputStream(currentUser.getImage()));
        Image img = Utilities.fromBytesToImage(currentUser.getImage());
        System.out.println("-----------------");
        System.out.println(currentUser.getImage());
        System.out.println("-----------------");
        // circle.setFill(new ImagePattern(img));

    }

    @FXML
    void updateProfile(ActionEvent event) throws RemoteException {
        User updatedUser = CurrentUser.getCurrentUser().getUser();
        // if (validateAll()) {
        updatedUser.setName(newName.getText());
        updatedUser.setEmail(newEmail.getText());
        updatedUser.setBio(newBio.getText());
        updatedUser.setCountry(comboBoxCountry.getValue());
        // }

        if (SettingsService.getInstance().updateProfile(updatedUser))
            CurrentUser.getCurrentUser().setUser(updatedUser);
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
