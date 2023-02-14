package gov.iti.presentation.dtos;

import gov.iti.model.Invitation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ReceivedInvitation {
    private ObjectProperty<Invitation> invitationProp;

    public ReceivedInvitation(){
        invitationProp = new SimpleObjectProperty<>();
    }

    public ObjectProperty<Invitation> getInvitationProp() {
        return invitationProp;
    }

    public void setInvitationProp(Invitation invitation) {
        this.invitationProp.set(invitation);
    }
}
