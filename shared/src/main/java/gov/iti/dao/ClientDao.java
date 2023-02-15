package gov.iti.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.model.Group;
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.model.User;

public interface ClientDao extends Remote {
    
    public void recievedContactMessage(Message message) throws RemoteException;

    public void recievedGroupMessage(Message message) throws RemoteException;

    public void UpdateOnContact(User user) throws RemoteException;

    public void recievedContactInvitation(Invitation invitation) throws RemoteException;

    public boolean downLoadFile(byte[] buffer, int count, String fileName) throws RemoteException;
    
    public void notifyUserChanges(User user) throws RemoteException;

    public void notifyCreatingGroup(Group group) throws RemoteException ;
    

}
