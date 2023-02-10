package gov.iti.presistance.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.midi.Receiver;

import com.mysql.cj.xdevapi.Client;

import gov.iti.presistance.DataBase.ConnectionManager;
import java.util.*;
import gov.iti.Utilities;
import gov.iti.dao.ClientDao;
import gov.iti.dao.ServerDao;
import gov.iti.model.Invitation;
import gov.iti.model.User;

public class ServerImpl extends UnicastRemoteObject implements ServerDao {

    Map<String,ClientDao> clients = new HashMap<>();

    private Connection connection;

    List <String> invitedContactList;

    public ServerImpl() throws RemoteException, SQLException {
        super();
        connection = ConnectionManager.getInstance().getStatement();
        invitedContactList=new ArrayList<>();
    }

    @Override
    public User login(ClientDao client, String phoneNumber, String password) throws RemoteException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("select * from user where phoneNumber = ? AND password = ?")) {
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, Utilities.Hash(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            clients.put(phoneNumber, client);
            return UserFactory.createUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(ClientDao client, User user, String Password){
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into user(PhoneNumber, Name, age, status, mode, image, password, email, country, bio, gender) values(?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, user.getStatus());
            preparedStatement.setInt(5, user.getMode());
            preparedStatement.setBytes(6, user.getImage());
            preparedStatement.setString(7, Utilities.Hash(Password));
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.setString(10, user.getBio());
            preparedStatement.setString(11, user.getGender());
            clients.put(user.getPhoneNumber(), client);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  
    }

    @Override
    public List<User> getContact(int userId) {
        return null;
    }

    @Override
    public List<gov.iti.model.Group> getGroup(int userId) {
        return null;
    }

    @Override
    public boolean updateProfile(User user) {
        System.out.println("serverImpl updating user profile at DataBase ");
        try (PreparedStatement preparedStatement = connection.prepareStatement("update user set Name = ? , image = ? , email = ? , country = ? , bio = ? where PhoneNumber = ?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setBytes(2, user.getImage());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setString(5, user.getBio());
            preparedStatement.setString(6, user.getPhoneNumber());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("update Failed ");
             e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(String phoneNumber, String newPassword) {
        return false;
    }

    @Override
    public boolean changeStatus(String phoneNumber, int status) {
        System.out.println(" changeStatus called");
        try (PreparedStatement preparedStatement = connection.prepareStatement("update user set status = ? where PhoneNumber = ?")) {
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, phoneNumber);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendInvitation(User sender, User reciever) throws RemoteException, SQLException {
        // TODO Add invitation to database
        //TODO send invitation to target receiver
        User recTest = new User("01111567897","Gomaa mohamed",25,"m",null,null,null,null,1,0);
        Invitation invitation = new Invitation(1, sender, recTest);
        if(clients.containsKey(reciever.getPhoneNumber())){
            clients.get(reciever.getPhoneNumber()).recievedContactInvitation(invitation);
            return true;
        }
        return false;

    }

    @Override
    public void signOut(User user) throws RemoteException, SQLException{
        clients.remove(user.getPhoneNumber());
    }

    @Override
    public List<Integer> addNewContact(String sender, List<String> contactList) throws RemoteException, SQLException {
        invitedContactList.clear();
        List <String> contact = new ArrayList<>(); // search if this contact register or not
        contact.add("01012546874");
        contact.add("01112546874");
        contact.add("01212546874"); 
        contact.add("01012345678");
        List <String> friends = new ArrayList<>(); // search if aleardy friends
        friends.add("01212546874");
        List <Integer> invitationStatus = new ArrayList<>(); // 0 not exist // 2 friend // 3 sucess

        // don't forget handle aleardy send invitation 
        for (String contactNo:contactList) { //String

            if(!contact.contains(contactNo)) {
                invitationStatus.add(0);
                System.out.println("not register");
            } else if (friends.contains(contactNo)) {
                invitationStatus.add(2);
                System.out.println("already friend");
            } else {
                invitationStatus.add(3);
                invitedContactList.add(contactNo);
                System.out.println("sucessfully");
            }
        }

            //resultSet.beforeFirst();
            //isExist.add(isContactRegisteration(resultSet,contact)); // check this number exist or not
            // check if this number friend or not
            // save in the database invitation
            // send invitation to users which is online
        return invitationStatus;
    }

}
