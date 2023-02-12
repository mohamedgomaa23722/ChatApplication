package gov.iti.presentation.controller.subItemController;

import java.net.URL;
import java.util.ResourceBundle;

import javax.management.Notification;

import gov.iti.business.services.InvitationService;
import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import javafx.application.Platform;
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
    private void acceptInvitation() {
        System.out.println("accept invitaion");
        User user = InvitationService.getInstance().acceptInvitation(invitation);
        CurrentUser.getCurrentUser().addContact(user);
        CurrentUser.getCurrentUser().getInvitations().remove(invitation);
    }

    @FXML
    private void reject() {
        System.out.println("reject invitaion");
        InvitationService.getInstance().rejectInvitation(invitation.getId());
        CurrentUser.getCurrentUser().getInvitations().remove(invitation);
    }

}
