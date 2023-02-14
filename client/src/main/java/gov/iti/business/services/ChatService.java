package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class ChatService {
    private static final ChatService instance = new ChatService();
    private ObjectProperty<Message> receivedMessage = new SimpleObjectProperty<>(null);

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
    public void receivedMessage(Message message) {
        Platform.runLater(() -> {
            this.receivedMessage.set(message);
        });
    }
    
}