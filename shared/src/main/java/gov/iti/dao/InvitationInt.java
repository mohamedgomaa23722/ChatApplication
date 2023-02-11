package gov.iti.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Invitation;

public interface InvitationInt extends Remote {
    public boolean sendInvitation(String senderPhoneNumber, String recieverPhoneNumber) throws RemoteException, SQLException;

    public List<Invitation> getInvitations(String userPhoneNumber) throws RemoteException, SQLException; 

    public void acceptInvitation(Invitation invitation) throws RemoteException, SQLException;

    public void rejectInvitation(int id) throws RemoteException, SQLException;

    public boolean isExistInvitaiton(String senderPhoneNumber, String recieverPhoneNumber) throws RemoteException, SQLException;

}
