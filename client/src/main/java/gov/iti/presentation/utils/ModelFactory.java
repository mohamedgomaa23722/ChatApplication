package gov.iti.presentation.utils;

import gov.iti.presentation.dtos.ReceivedInvitation;

public class ModelFactory {
    private static final ModelFactory instance = new ModelFactory();

    private static ReceivedInvitation invitationProperty;

    private ModelFactory() {}

    public static ModelFactory getInstance() {
        return instance;
    }

    public ReceivedInvitation getReceivedInvitation(){
        if(invitationProperty == null)
            invitationProperty = new ReceivedInvitation();
        return invitationProperty;    
    }
}
