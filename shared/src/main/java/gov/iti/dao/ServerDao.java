package gov.iti.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Group;
import gov.iti.model.User;

public interface ServerDao extends Remote, SettingInt{
    
    public boolean login(String PhoneNumber, String Password) throws RemoteException, SQLException;

    public boolean register(User user, String Password) throws RemoteException, SQLException;

    public List<User> getContact(int userId) throws RemoteException, SQLException;

    public List<Group> getGroup(int userId) throws RemoteException, SQLException;

    public boolean updateStatues(int userId) throws RemoteException, SQLException;

    public boolean updateMode(int userId) throws RemoteException, SQLException;
}
