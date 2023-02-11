package gov.iti.presistance.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.iti.presistance.UsersInfo;
import gov.iti.presistance.DataBase.ConnectionManager;
import java.util.*;
import gov.iti.Utilities;
import gov.iti.dao.ClientDao;
import gov.iti.dao.ServerDao;
import gov.iti.model.Group;
import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.model.UserContact;

public class ServerImpl extends UnicastRemoteObject implements ServerDao {

    Map<String, ClientDao> clients = new HashMap<>();

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

            UsersInfo.updateList();

            return UserFactory.createUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(ClientDao client, User user, String Password) {
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
            boolean result = preparedStatement.executeUpdate() > 0 ;

            UsersInfo.updateList();

            return result ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getContact(String userPhoneNumber) {
        return null;
    }

    @Override
    public List<gov.iti.model.Group> getGroup(String userPhoneNumber) {
        return null;
    }

    @Override
    public boolean updateProfile(User user) {
        System.out.println("serverImpl updating user profile at DataBase ");
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update user set Name = ? , image = ? , email = ? , country = ? , bio = ? where PhoneNumber = ?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setBytes(2, user.getImage());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setString(5, user.getBio());
            preparedStatement.setString(6, user.getPhoneNumber());
            boolean result = preparedStatement.executeUpdate() > 0 ;

            UsersInfo.updateList();

            return result;
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
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("update user set status = ? where PhoneNumber = ?")) {
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, phoneNumber);
            boolean result = preparedStatement.executeUpdate() > 0 ;

            UsersInfo.updateList();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendInvitation(String senderPhoneNumber, String recieverPhoneNumber)
            throws RemoteException, SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO invitation(senderPhone, receiverPhone) values(?,?)", Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, senderPhoneNumber);
            preparedStatement.setString(2, recieverPhoneNumber);
            preparedStatement.executeUpdate();

            if (clients.containsKey(recieverPhoneNumber)) {
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    resultSet.next();
                    clients.get(recieverPhoneNumber)
                    .recievedContactInvitation(new Invitation(resultSet.getInt(1), senderPhoneNumber, recieverPhoneNumber));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void signOut(String phoneNumber) throws RemoteException, SQLException {
        clients.remove(phoneNumber);
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
    public List<Integer> addNewContact(String sender, List<String> contactList) throws RemoteException, SQLException {
        invitedContactList.clear();
        /*List <String> contact = new ArrayList<>(); // search if this contact register or not
        contact.add("01111567897");
        contact.add("01111567898");
        contact.add("01512345687"); */

        List <Integer> invitationStatus = new ArrayList<>(); // 0 not exist // 2 friend // 3 sucess

        // don't forget handle aleardy send invitation 
       /*  for (String contactNo:contactList) { //String

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
        }*/

            //resultSet.beforeFirst();
            //isExist.add(isContactRegisteration(resultSet,contact)); // check this number exist or not
            // check if this number friend or not
            // save in the database invitation
            for (String contactPhoneNumber : contactList) {
                sendInvitation(sender, contactPhoneNumber);
            }
            // send invitation to users which is online
        return invitationStatus;
    }
    public boolean creatGroup(String groupName) {
        System.out.println("create group");
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("insert into chatgroup  (group_name) VALUES(?)")) {
            preparedStatement.setString(1, groupName);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getGroupLastId() {
        System.out.println(" getGroupLastId");
        try (Statement statement = connection.createStatement()) {
           ResultSet rs=statement.executeQuery("select max(id) from chatGroup");
           rs.next();
            return rs.getInt("max(id)");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean addGroupMember(int groupId, String memberPhoneNumber) {
        System.out.println(" addGroupMember");
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("insert into contactgroup  (group_id ,contact_id) VALUES(?,?)")) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.setString(2, memberPhoneNumber);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserContact> selectUserContacts(String userPhoneNumber) {
        System.out.println("selectUserContacts");
        List<UserContact> contactList = new ArrayList<UserContact>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "SELECT phoneNumber ,name from user where phonenumber in (SELECT contact_id from usercontacts where user_id=?)")) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                contactList.add(new UserContact(resultSet.getString(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
    public List<Group> selectGroups(String userPhoneNumber){
        System.out.println("selectGroups");
        List<Group> GroupList = new ArrayList<Group>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "SELECT * from chatgroup where id in (SELECT group_id from contactgroup where contact_id=?)")) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                GroupList.add(new Group(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GroupList;
    }

}
