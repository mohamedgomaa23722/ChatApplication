package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.dao.ServerDao;
import gov.iti.model.User;
import gov.iti.presistance.ClientImpl;
import gov.iti.presistance.connection.ClientServerConnection;

public class RegisterService {

    ServerDao chatReg;
    static RegisterService registerservice = new RegisterService();

    public static RegisterService getRegisterService() {
        return registerservice;
    }

    private RegisterService() {
        chatReg=ClientServerConnection.getConnectionInstance().getServerDao();
    }

    public boolean registerNewUser(User user, String Password) {
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().register(new ClientImpl(), user, Password);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}