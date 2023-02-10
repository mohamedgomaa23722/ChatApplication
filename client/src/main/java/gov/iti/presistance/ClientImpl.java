package gov.iti.presistance;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gov.iti.business.services.ChatService;
import gov.iti.dao.ClientDao;
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.model.User;

public class ClientImpl extends UnicastRemoteObject implements ClientDao{

    public ClientImpl() throws RemoteException {
    }

    @Override
    public void recievedContactMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedGroupMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedContactInvitation(Invitation invitation) throws RemoteException {
        ChatService.getInstance().receiveInvitation(invitation);
    }
    
}
