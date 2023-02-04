package gov.iti.dao;

import java.rmi.RemoteException;

import gov.iti.model.User;

public interface SettingInt {
    public boolean updateProfile(User user) throws RemoteException;

    public boolean changePassword(String phoneNumber, String newPassword) throws RemoteException;

    public boolean changeStatus(String phoneNumber, int status) throws RemoteException;
}
