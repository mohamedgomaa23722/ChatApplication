package gov.iti.dao;

import java.rmi.RemoteException;

import gov.iti.model.User;

public interface SettingInt {
    public boolean UpdateProfile(User user) throws RemoteException;

    public boolean ChangePassword(String phoneNumber, String newPassword) throws RemoteException;

    public boolean ChangeStatus(String phoneNumber, int status) throws RemoteException;
}
