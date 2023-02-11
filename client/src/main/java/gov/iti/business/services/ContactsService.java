
package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.dao.ServerDao;
import gov.iti.model.UserContact;
import gov.iti.presistance.connection.ClientServerConnection;

public class ContactsService {

    ServerDao chatReg;
    static ContactsService contactsService = new ContactsService();

    public static ContactsService getcontactsService() {
        return contactsService;
    }

    private ContactsService() {
        chatReg=ClientServerConnection.getConnectionInstance().getServerDao();
    }

    public List<UserContact> getContacts(String UserPhoneNumber) {
        try {
            return chatReg.selectUserContacts(UserPhoneNumber);
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return new ArrayList<UserContact>();
    }
    
    
}