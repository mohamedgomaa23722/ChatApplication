package gov.iti.presentation.controller.settingsController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

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
    private Label successfullMessage;

    @FXML
    private ComboBox<String> comboBoxCountry;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxCountry.setItems(FXCollections.observableArrayList(Utilities.country_list));
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        circle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(currentUser.getImage()))));
        newBio.textProperty().bindBidirectional(currentUser.getBio());
        newName.textProperty().bindBidirectional(currentUser.getName());
        newEmail.textProperty().bindBidirectional(currentUser.getEmail());
        System.out.println(currentUser.getCountry().get());
        comboBoxCountry.setValue(currentUser.getCountry().get());
        setImage(currentUser.getImage());
    }

    @FXML
    void updateProfile(ActionEvent event) throws RemoteException {
        User updatedUser = CurrentUser.getCurrentUser().getUser();
        // if (validateAll()) {
        updatedUser.setName(newName.getText().trim());
        updatedUser.setEmail(newEmail.getText().trim());
        updatedUser.setBio(newBio.getText().trim());
        updatedUser.setCountry(comboBoxCountry.getValue());

        if (SettingsService.getInstance().updateProfile(updatedUser))
            CurrentUser.getCurrentUser().setUser(updatedUser);

        successfullMessage.setText("Updated Successfully");           
    }

    @FXML
    private void UpdateImage() throws FileNotFoundException{
        FileChooser fileChooser = new  FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            byte[] image = new byte[(int) file.length()];
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(image);
                CurrentUser.getCurrentUser().setImage(image);
                setImage(image);
                
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setImage(byte[] imageBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis);
        if (image != null)
            circle.setFill(new ImagePattern(image));

        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public boolean validateAll() {
        if (!Utilities.validateName(newName.getText().trim())) {
            System.out.println("not valid user name ");
            return false;
        } else {
            System.out.println("valid name");
        }
        if (!Utilities.validateEmail(newEmail.getText().trim())) {
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
