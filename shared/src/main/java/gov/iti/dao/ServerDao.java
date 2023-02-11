package gov.iti.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Group;
import gov.iti.model.Invitation;
import gov.iti.model.User;

public interface ServerDao extends SettingInt{
    
    public User login(ClientDao client, String PhoneNumber, String Password) throws RemoteException, SQLException;

    public boolean register(ClientDao client, User user, String Password) throws RemoteException, SQLException;

    public List<User> getContact(String userPhoneNumber) throws RemoteException, SQLException;

    public List<Group> getGroup(String userPhoneNumber) throws RemoteException, SQLException;

    public boolean sendInvitation(String senderPhoneNumber, String recieverPhoneNumber) throws RemoteException, SQLException;

    public void signOut(String PhoneNumbe) throws RemoteException, SQLException;

    public List<Invitation> getInvitations(String userPhoneNumber) throws RemoteException, SQLException; 
}
