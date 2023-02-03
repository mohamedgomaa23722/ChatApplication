package gov.iti.presentation.controller.SubItemController;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dto.Chat;
import gov.iti.presentation.dto.Contact;
import gov.iti.presentation.dto.Group;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    private Chat chat;

    public ContactItemController(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (chat instanceof Group) {
            Group group = ((Group) chat);
            Item_Name.setText(group.getGroupName());
        } else {
            Contact contact = (Contact) chat;
            if (contact != null) {
                Item_Image.setFill(new ImagePattern(contact.getImage()));
                if (contact.getStatus() == 0) {
                    Item_Image.setStroke(Color.GRAY);
                    Item_Image.setStrokeWidth(2);
                } else {
                    Item_Image.setStroke(Color.web("#00FF66"));
                    Item_Image.setStrokeWidth(2);
                }

                Item_Name.setText(contact.getName());
            }
        }
    }
}
