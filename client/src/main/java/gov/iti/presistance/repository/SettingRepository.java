package gov.iti.presistance.repository;

import gov.iti.dao.SettingInt;
import gov.iti.model.User;

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
    public boolean changeStatus(String phoneNumber, int status) {
        return false;
    }
}
