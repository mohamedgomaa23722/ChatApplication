package gov.iti.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.model.Invitation;
import gov.iti.presentation.controller.subItemController.InvitationItemController;
import gov.iti.presentation.dtos.CurrentUser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class NotificationController implements Initializable {

    @FXML
    private ListView<Invitation> notificationList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationList.setItems(CurrentUser.getCurrentUser().getInvitations());
        notificationList.setCellFactory(p-> new InvitationCell());
    }
    //01111111111

}

class InvitationCell extends ListCell<Invitation> {
    @Override
    public void updateItem(Invitation item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            try {
                System.out.println("update list" + item.getId());
                FXMLLoader loader = new FXMLLoader();
                InvitationItemController controller = new InvitationItemController(item);
                loader.setController(controller);
                Parent view = loader.load(getClass().getClassLoader().getResource("InvitationItemFXML.fxml").openStream());
                this.setGraphic(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.setGraphic(null);
        }
    }

}