package gov.iti.presistance.repository;

import java.rmi.RemoteException;

import gov.iti.dao.SettingInt;
import gov.iti.model.User;
import gov.iti.presistance.connection.ClientServerConnection;

public class SettingRepository implements SettingInt{

    @Override
    public boolean updateProfile(User user) {
        return false;
    }

    @Override
    public boolean changePassword(String phoneNumber, String newPassword) {
        return false;
    }

    @Override
    public boolean changeStatus(String phoneNumber, int status) throws RemoteException {
        return ClientServerConnection.getConnectionInstance().getRegistry().changeStatus(phoneNumber, status);
    }
}
