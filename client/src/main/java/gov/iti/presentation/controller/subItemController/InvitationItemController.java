package gov.iti.presentation.controller.subItemController;

import java.net.URL;
import java.util.ResourceBundle;

import javax.management.Notification;

import gov.iti.business.services.InvitationService;
import gov.iti.model.Invitation;
import gov.iti.presentation.dtos.CurrentUser;
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
            senderName.setText(invitation.getSenderPhoneNumber());
    }

    @FXML
    private void accept() {
        //TODO : accept inviation
        InvitationService.getInstance().acceptInvitation(invitation);
        //TODO : delete invitation 
        CurrentUser.getCurrentUser().getInvitations().remove(invitation);
    }

    @FXML
    private void reject() {
        InvitationService.getInstance().rejectInvitation(invitation.getId());
        //TODO : delete invitation 
        CurrentUser.getCurrentUser().getInvitations().remove(invitation);
    }
    
}
