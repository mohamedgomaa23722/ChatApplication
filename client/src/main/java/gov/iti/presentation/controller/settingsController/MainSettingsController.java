package gov.iti.presentation.controller.settingsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainSettingsController implements Initializable {
    @FXML
    HBox changeStatusBox;

    @FXML
    HBox updateProfileBox;

    @FXML
    HBox changePasswordBox;

    @FXML
    VBox settingContainer;
    @FXML
    GridPane settingGrid;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showStatusSetting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStatusSetting() throws IOException{
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource("changeStatusPage.fxml"));
        settingContainer.getChildren().removeAll(settingContainer.getChildren());
        settingContainer.getChildren().add(p);
    }

    @FXML
    private void showProfileSetting() throws IOException{
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource("changeProfilePage.fxml"));
        settingContainer.getChildren().removeAll(settingContainer.getChildren());
        settingContainer.getChildren().add(p);
        System.out.println("show Profile Setting");
    }

    @FXML
    private void showPasswordSetting() throws IOException{
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource("changePasswordPage.fxml"));
        settingContainer.getChildren().removeAll(settingContainer.getChildren());
        settingContainer.getChildren().add(p);
        System.out.println("show Password Setting");
    }


}
