package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ChatService {
    private static final ChatService instance = new ChatService();
    private ObjectProperty<Invitation> invitation = new SimpleObjectProperty<>();

    private ChatService(){}

    public static ChatService getInstance(){
        return instance;
    }


    public void receiveInvitation(Invitation invitation) {
        setInvitation(invitation);
    } 

    public void sendInvitation(User user) throws RemoteException, SQLException{
        ClientServerConnection.getConnectionInstance().getServerDao().sendInvitation(user, user);

    }

    public void SignOut(User user) throws RemoteException, SQLException{
        ClientServerConnection.getConnectionInstance().getServerDao().signOut(user);
    }

    public ObjectProperty<Invitation> getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        Platform.runLater(() -> {
        this.invitation.set(invitation);
        });
    }

    
}