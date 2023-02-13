package gov.iti.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.model.User;

public interface SettingInt extends Remote {
    public boolean updateProfile(User user) throws RemoteException;

    public boolean changePassword(String phoneNumber,String oldPassword, String newPassword) throws RemoteException;

    public boolean changeStatus(String phoneNumber, int status) throws RemoteException;
}
