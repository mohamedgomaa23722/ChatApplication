package gov.iti.presistance.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Client;

import gov.iti.dao.InvitationInt;
import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.presistance.DataBase.ConnectionManager;

public class InvitationImp extends UnicastRemoteObject implements InvitationInt {

    private Connection connection;

    public InvitationImp() throws RemoteException, SQLException {
        super();
        connection = ConnectionManager.getInstance().getStatement();
    }

    @Override
    public boolean sendInvitation(String senderPhoneNumber, String recieverPhoneNumber)
            throws RemoteException, SQLException {

        if (isExistInvitaiton(senderPhoneNumber, recieverPhoneNumber))
            return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO invitation(senderPhone, receiverPhone) values(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, senderPhoneNumber);
            preparedStatement.setString(2, recieverPhoneNumber);
            preparedStatement.executeUpdate();

            if (ServerImpl.clients.containsKey(recieverPhoneNumber)) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    resultSet.next();
                    ServerImpl.clients.get(recieverPhoneNumber)
                            .recievedContactInvitation(
                                    new Invitation(resultSet.getInt(1), senderPhoneNumber, recieverPhoneNumber));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }             
               return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Invitation> getInvitations(String userPhoneNumber) throws RemoteException, SQLException {
        List<Invitation> invitations = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("select * From invitation where receiverPhone = ?")) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                invitations.add(new Invitation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            return invitations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User acceptInvitation(Invitation invitation) throws RemoteException, SQLException {
        // TODO : add contact to database
        addToContact(invitation.getSenderPhoneNumber(), invitation.getReceiverPhoneNumber());
        rejectInvitation(invitation.getId());
        User receiver =getUserByID(invitation.getReceiverPhoneNumber());
        User sender =getUserByID(invitation.getSenderPhoneNumber());

        if(ServerImpl.clients != null && !ServerImpl.clients.isEmpty() && ServerImpl.clients.containsKey(invitation.getSenderPhoneNumber()))
            ServerImpl.clients.get(invitation.getSenderPhoneNumber()).UpdateOnContact(receiver);
        return sender;    
    }

    @Override
    public void rejectInvitation(int id) throws RemoteException, SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete From invitation where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isExistInvitaiton(String senderPhoneNumber, String recieverPhoneNumber)
            throws RemoteException, SQLException {
        // TODO Auto-generated method stub
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("Select id from invitation where senderPhone = ? AND receiverPhone = ?")) {
            preparedStatement.setString(1, senderPhoneNumber);
            preparedStatement.setString(2, recieverPhoneNumber);

            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addToContact(String FirstUserPhone, String SecondUserPhone){
        try (PreparedStatement statement = connection.prepareStatement("Insert into usercontacts(user_id, contact_id) values(?,?)")){
            statement.setString(1, FirstUserPhone);
            statement.setString(2, SecondUserPhone);
            statement.executeUpdate();

            statement.setString(1, SecondUserPhone);
            statement.setString(2, FirstUserPhone);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    private User getUserByID(String phoneNumber){
        try (PreparedStatement statement = connection.prepareStatement("select * from user where PhoneNumber = ?")){
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            return UserFactory.createUser(resultSet);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
