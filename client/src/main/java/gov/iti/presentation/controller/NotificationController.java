package gov.iti.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.InvitationService;
import gov.iti.model.Invitation;
import gov.iti.presentation.controller.subItemController.InvitationItemController;
import gov.iti.presentation.utils.ModelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        List<Invitation> invitations = InvitationService.getInstance().getInvitations();
        ObservableList<Invitation> invitaitonObservableList = FXCollections.observableArrayList(invitations);

        if (invitations != null) {
            notificationList.setItems(invitaitonObservableList);
            notificationList.setCellFactory(p -> new InvitationCell());
        }
        ModelFactory.getInstance().getReceivedInvitation().getInvitationProp().addListener((observer, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Not null: ");
                invitaitonObservableList.add(newValue);
            } else {
                System.out.println(" null: ");
            }
        });
    }

}

class InvitationCell extends ListCell<Invitation> {
    @Override
    public void updateItem(Invitation item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                InvitationItemController controller = new InvitationItemController(item);
                loader.setController(controller);
                Parent view = loader
                        .load(getClass().getClassLoader().getResource("InvitationItemFXML.fxml").openStream());
                this.setGraphic(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}