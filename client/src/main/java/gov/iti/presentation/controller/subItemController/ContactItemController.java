package gov.iti.presentation.controller.subItemController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.model.User;
import gov.iti.presentation.dtos.Chat;
import gov.iti.presentation.dtos.Contact;
import gov.iti.presentation.dtos.Group;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ContactItemController implements Initializable {

    @FXML
    private Circle Item_Image;

    @FXML
    private Label Item_Name;

    @FXML
    private Label Item_LastMessage;

    private User user;

    public ContactItemController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            if (user != null) {
                Item_Image.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getImage()))));
                if (user.getStatus() == 0) {
                    Item_Image.setStroke(Color.GRAY);
                    Item_Image.setStrokeWidth(2);
                } else {
                    Item_Image.setStroke(Color.web("#00FF66"));
                    Item_Image.setStrokeWidth(2);
                }

                Item_Name.setText(user.getName());
            }
        }
}
