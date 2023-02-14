package gov.iti.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Group;
import gov.iti.model.User;


public interface ServerDao extends SettingInt, InvitationInt{
    
    public User login(ClientDao client, String PhoneNumber, String Password) throws RemoteException, SQLException;

    public boolean register(ClientDao client, User user, String Password) throws RemoteException, SQLException;

    public List<User> getContact(String userPhoneNumber) throws RemoteException, SQLException;

    public List<Group> getGroup(String userPhoneNumber) throws RemoteException, SQLException;

    public void signOut(String PhoneNumbe) throws RemoteException, SQLException;

    public List<Integer> addNewContact(String sender, List<String> contactList) throws RemoteException, SQLException;



    public int getGroupLastId()throws RemoteException, SQLException;

    public boolean creatGroup(String groupName) throws RemoteException, SQLException;

    public boolean addGroupMember(int groupId, String memberPhoneNumber)throws RemoteException, SQLException;

    public List<User> selectUserContacts(String userPhoneNumber) throws RemoteException, SQLException;

    public boolean sendFile(byte[] buffer, int count, String reciever, String fileName) throws RemoteException;

}
