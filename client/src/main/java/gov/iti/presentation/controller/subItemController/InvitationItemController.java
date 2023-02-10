package gov.iti.presentation.controller.subItemController;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.model.Invitation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class InvitationItemController implements Initializable {
    @FXML
    private Label senderName;
    private Invitation invitation;

    
    public InvitationItemController(Invitation invitation) {
        this.invitation = invitation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            //TODO : set sender name
            senderName.setText(invitation.getSender().getName());
    }

    @FXML
    private void accept() {
        //TODO : accept inviation
    }

    @FXML
    private void reject() {
        //TODO : reject inviation

    }
    
}
