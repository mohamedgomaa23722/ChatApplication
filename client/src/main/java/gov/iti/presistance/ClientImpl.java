package gov.iti.presistance;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

import gov.iti.business.services.InvitationService;
import gov.iti.dao.ClientDao;
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ClientImpl extends UnicastRemoteObject implements ClientDao {

    public ClientImpl() throws RemoteException {
    }

    @Override
    public void recievedContactMessage(Message message) throws RemoteException {

    }

    @Override
    public void recievedGroupMessage(Message message) throws RemoteException {

    }

    @Override
    public void recievedContactInvitation(Invitation invitation) throws RemoteException {
        InvitationService.getInstance().receiveInvitation(invitation);
    }

    @Override
    public void serverDown() throws RemoteException {
        System.out.println("server Down .. ");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("ops");
                alert.setHeaderText("Server is Down , Do You want to reconnect");
                alert.setContentText("OK => RECONNECT\nCancel => Close The Program");
                Optional<ButtonType> result = alert.showAndWait();

                System.out.println("ater showing the alert");

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (!ClientServerConnection.reConnect())
                        try {
                            serverDown();
                        } catch (RemoteException e) {
                            System.out.println("Error in calling server Down again in Client .... ");
                            e.printStackTrace();
                        }
                } else {
                    Platform.exit();
                }
            }
        });
    }

    @Override
    public void isClientOnline() throws RemoteException {
        // TODO Auto-generated method stub
    }

}
