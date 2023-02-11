package gov.iti.business.services;

import java.rmi.RemoteException;

import gov.iti.dao.SettingInt;
import gov.iti.model.User;
import gov.iti.presistance.connection.ClientServerConnection;

public class SettingsService implements SettingInt{

    private  static SettingsService instance;

    private SettingsService(){
    }

    public static SettingsService getInstance(){
        if(instance == null)
            instance = new SettingsService();
        return instance;    
    }

    @Override
    public boolean updateProfile(User user) throws RemoteException {
        return ClientServerConnection.getConnectionInstance().getServerDao().updateProfile(user);
    }

    @Override
    public boolean changePassword(String phoneNumber,String oldPassword, String newPassword) {
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().changePassword(phoneNumber,oldPassword,newPassword);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeStatus(String phoneNumber, int status) throws RemoteException {
        return ClientServerConnection.getConnectionInstance().getServerDao().changeStatus(phoneNumber, status);
    }
    
}
