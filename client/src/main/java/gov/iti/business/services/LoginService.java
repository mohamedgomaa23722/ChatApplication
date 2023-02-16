package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.dao.ServerDao;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presistance.ClientImpl;
import gov.iti.presistance.connection.ClientServerConnection;

public class LoginService {

    static LoginService loginservice = new LoginService();

    public static LoginService getLoginService() {
        return loginservice;
    }

    private LoginService() {
    }

    public User loginUser() {
        String Password = CurrentUser.getCurrentUser().getPassword();
        String phoneNumber = CurrentUser.getCurrentUser().getPhoneNumber().get();
        System.out.println(phoneNumber + " : " + Password);
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().login(new ClientImpl(),phoneNumber, Password);
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return null;
    }
}