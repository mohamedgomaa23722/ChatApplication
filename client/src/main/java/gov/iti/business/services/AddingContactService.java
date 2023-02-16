package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.dao.ServerDao;
import gov.iti.presistance.connection.ClientServerConnection;

public class AddingContactService {

    ServerDao chatReg;
    private static AddingContactService addingContactservice = new AddingContactService();

    public static AddingContactService getAddingNewContactService() {
        return addingContactservice;
    }

    private AddingContactService() {
        chatReg=ClientServerConnection.getConnectionInstance().getServerDao();
    }

    public List<Integer> addNewContact(String sender, List <String> contactList) {
        System.out.println("service sending invitation");
        System.out.println(contactList);
        // call  server
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().addNewContact(sender,contactList);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}