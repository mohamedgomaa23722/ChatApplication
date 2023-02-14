package gov.iti.presistance;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gov.iti.business.services.ChatService;
import gov.iti.business.services.InvitationService;
import gov.iti.dao.ClientDao;
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.dtos.Chat;

public class ClientImpl extends UnicastRemoteObject implements ClientDao{

    public ClientImpl() throws RemoteException {
    }

    @Override
    public void recievedContactMessage(Message message) throws RemoteException {
        ChatService.getInstance().receivedMessage(message);
        System.out.println(message.getMessage());
    }

    @Override
    public void recievedGroupMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedContactInvitation(Invitation invitation) throws RemoteException {
        InvitationService.getInstance().receiveInvitation(invitation);
    }

    @Override
    public void UpdateOnContact(User user) throws RemoteException {
        ChatService.getInstance().UpdateContanctList(user);
    }

    @Override
    public void notifyUserChanges(User user) throws RemoteException {
        System.out.println("change has been made in " + user.getPhoneNumber());
        ChatService.getInstance().notifyContactChange(user);
    }
    
}
