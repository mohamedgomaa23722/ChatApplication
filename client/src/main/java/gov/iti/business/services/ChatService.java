package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

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
    
}