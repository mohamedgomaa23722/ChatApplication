package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.model.Group;
import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.ChatManager;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class ChatService {
    private static final ChatService instance = new ChatService();

    private ObjectProperty<Message> receivedMessage = new SimpleObjectProperty<>(null);

    private ChatService() {
    }

    public static ChatService getInstance() {
        return instance;
    }

    public void SignOut(String PhoneNumber) throws RemoteException, SQLException {
        ClientServerConnection.getConnectionInstance().getServerDao().signOut(PhoneNumber);
        SettingsService.getInstance().changeStatus(PhoneNumber, 0);
    }

    public void UpdateContanctList(User user) {
        System.out.println("new user contact has added to your list");
        Platform.runLater(() -> CurrentUser.getCurrentUser().addContact(user));
        ChatManager.getInstance().addContanct(user.getPhoneNumber());
    }

    public void sendMessage(Message message, int mode) {
        if (mode == 1) {
            // individual chat
            try {
                ClientServerConnection.getConnectionInstance().getServerDao().SendContactMessage(message);
                System.out.println("Client service Receiver : " + message.getReceiverPhoneNumber());
            } catch (RemoteException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                ClientServerConnection.getConnectionInstance().getServerDao().SendGroupMessage(message);
            } catch (RemoteException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                //SSystem.out.println("Client service Receiver : " + message.getReceiverPhoneNumber());
        }
    }

    public void receivedMessage(Message message) {
        Platform.runLater(() -> {
            this.receivedMessage.set(message);
        });
    }

    public ObjectProperty<Message> getMessage() {
        return receivedMessage;
    }

    public void notifyContactChange(User user) {
        Platform.runLater(() -> {
            int index = 0;
            var contactList = CurrentUser.getCurrentUser().getContacts();
            for (User contact : contactList) {
                if (contact.getPhoneNumber().equals(user.getPhoneNumber()))
                    break;
                index++;
            }
            contactList.remove(index);
            CurrentUser.getCurrentUser().getContacts().add(index, user);
        });

    }
    public void notifyGroupsChange(Group group) {
        Platform.runLater(() -> {
            CurrentUser.getCurrentUser().addGroup(group);
        });

    }
}