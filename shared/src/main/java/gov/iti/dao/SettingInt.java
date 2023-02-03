package gov.iti.dao;

import gov.iti.model.User;

public interface SettingInt {
    public boolean UpdateProfile(User user);
    public boolean ChangePassword(String phoneNumber, String newPassword);
    public boolean ChangeStatus(String phoneNumber, int status);
  
}
