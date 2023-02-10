package gov.iti.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Group;
import gov.iti.model.User;

public interface ServerDao extends SettingInt{
    
    public User login(ClientDao client, String PhoneNumber, String Password) throws RemoteException, SQLException;

    public boolean register(ClientDao client, User user, String Password) throws RemoteException, SQLException;

    public List<User> getContact(int userId) throws RemoteException, SQLException;

    public List<Group> getGroup(int userId) throws RemoteException, SQLException;

    public boolean sendInvitation(User sender, User reciever) throws RemoteException, SQLException;

    public void signOut(User user) throws RemoteException, SQLException;

    public List<Integer> addNewContact(String sender, List<String> contactList) throws RemoteException, SQLException;
}
