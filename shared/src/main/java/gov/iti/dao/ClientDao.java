package gov.iti.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.model.Invitation;
import gov.iti.model.Message;

public interface ClientDao extends Remote {
    
    public void recievedContactMessage(Message message) throws RemoteException;

    public void recievedGroupMessage(Message message) throws RemoteException;

    public void recievedContactInvitation(Invitation invitation) throws RemoteException;
    
}
