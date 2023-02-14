package gov.iti.business.services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Platform;

public class InvitationService {
    private static final InvitationService instance = new InvitationService();
 
    private InvitationService(){}

    public static InvitationService getInstance(){
        return instance;
    }

    public List<Invitation> getInvitations(){
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().getInvitations(CurrentUser.getCurrentUser().getPhoneNumber().get());
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void receiveInvitation(Invitation invitation) {
        System.out.println("receiveInvitation");
        Platform.runLater(() -> {
            CurrentUser.getCurrentUser().addInvitations(invitation);
        });
    } 

    public User acceptInvitation(Invitation invitation) {
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().acceptInvitation(invitation);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void rejectInvitation(int id) {
        try {
            ClientServerConnection.getConnectionInstance().getServerDao().rejectInvitation(id);
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }
    }
}
