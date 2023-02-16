package gov.iti.presentation.controller.subItemController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.model.Group;
import gov.iti.model.User;

import gov.iti.presentation.utils.Status;
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
    
    private Group group;

    @FXML
    private Circle newMessageIcon;

    public ContactItemController(User user) {
        this.user = user;
    }
    public ContactItemController(Group group) {
        this.group = group;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (user != null) {
            Item_Image.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getImage()))));
            if (user.getStatus() == 0) {
                changeStatusColors(Status.Offline);
            } else if (user.getStatus() == 1)
                changeStatusColors(Status.online);
            else if (user.getStatus() == 2)
                changeStatusColors(Status.busy);
            else
                changeStatusColors(Status.away);

            Item_Name.setText(user.getName());
            newMessageIcon.setVisible(user.isHasNewMessage());

        }
        if (group != null) {
            Item_Image.setFill(new ImagePattern(new Image(new ByteArrayInputStream(group.getImage()))));
            Item_Name.setText(group.getGroupName());
            newMessageIcon.setVisible(group.isHasNewMessage());
        }

    }

    private void changeStatusColors(Status status) {
        Item_Image.setStroke(Color.web(status.color));
        Item_Image.setStrokeWidth(3);

    }
}
