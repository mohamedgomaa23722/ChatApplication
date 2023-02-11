package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presistance.connection.ClientServerConnection;


public class ChatService {
    private static final ChatService instance = new ChatService();

    private ChatService(){}

    public static ChatService getInstance(){
        return instance;
    }

    public void SignOut(String PhoneNumber) throws RemoteException, SQLException{
        ClientServerConnection.getConnectionInstance().getServerDao().signOut(PhoneNumber);
    }

    public void UpdateContanctList(User user){
        System.out.println("new user contact has added to your list");
        CurrentUser.getCurrentUser().addContact(user);
    }
    
}