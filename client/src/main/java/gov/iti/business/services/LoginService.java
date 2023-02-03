package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.dao.ServerDao;
import gov.iti.presentation.dtos.LoggedUser;
import gov.iti.presistance.connection.ClientServerConnection;

public class LoginService {

    LoggedUser loggedUser;
    ServerDao chatReg;
    static LoginService loginservice = new LoginService();

    public static LoginService getLoginService() {
        return loginservice;
    }

    private LoginService() {
        chatReg=ClientServerConnection.getConnectionInstance().getRegistry();
    }

    public boolean loginUser(LoggedUser loggedUser) {
        try {
            return chatReg.login(loggedUser.getPhone(), loggedUser.getPasswd());
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return false;
    }
}