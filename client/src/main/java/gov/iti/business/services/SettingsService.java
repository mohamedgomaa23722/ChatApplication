package gov.iti.business.services;

import java.rmi.RemoteException;

import gov.iti.dao.SettingInt;
import gov.iti.model.User;
import gov.iti.presistance.repository.SettingRepository;

public class SettingsService implements SettingInt{

    private SettingRepository settingRepository;

    public SettingsService(){
        settingRepository = new SettingRepository();
    }

    @Override
    public boolean updateProfile(User user) {
        try {
            return settingRepository.updateProfile(user);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    @Override
    public boolean changePassword(String phoneNumber, String newPassword) {
        return false;
        
    }

    @Override
    public boolean changeStatus(String phoneNumber, int status) {
        try {
            return settingRepository.changeStatus(phoneNumber, status);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
}
