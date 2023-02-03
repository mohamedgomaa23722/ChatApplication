package gov.iti.presistance.repository;

import gov.iti.dao.SettingInt;
import gov.iti.model.User;

public class SettingRepository implements SettingInt{


    @Override
    public boolean UpdateProfile(User user) {
        return false;
    }

    @Override
    public boolean ChangePassword(String phoneNumber, String newPassword) {
        return false;
    }

    @Override
    public boolean ChangeStatus(String phoneNumber, int status) {
        return false;
    }
}
