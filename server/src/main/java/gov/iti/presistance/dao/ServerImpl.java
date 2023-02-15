package gov.iti.presistance.dao;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import gov.iti.presistance.UsersInfo;
import gov.iti.presistance.DataBase.ConnectionManager;
import java.util.*;
import gov.iti.Utilities;
import gov.iti.dao.ClientDao;
import gov.iti.dao.ServerDao;
import gov.iti.model.Group;
import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.dto.Contact;

public class ServerImpl extends InvitationImp implements ServerDao, Serializable {

    protected static Map<String, ClientDao> clients = new HashMap<>();

    private Connection connection;


    public ServerImpl() throws RemoteException, SQLException {
        super();
        connection = ConnectionManager.getInstance().getStatement();
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

            Blob blob = new SerialBlob(user.getImage());
            preparedStatement.setBlob(6, blob);

            preparedStatement.setString(7, Utilities.Hash(Password));
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.setString(10, user.getBio());
            preparedStatement.setString(11, user.getGender());
            clients.put(user.getPhoneNumber(), client);
            boolean result = preparedStatement.executeUpdate() > 0;
            if(result)
                changeStatus(user.getPhoneNumber(), 1);
            UsersInfo.updateList();
            return result;
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
            boolean result = preparedStatement.executeUpdate() > 0;

            UsersInfo.updateList();

            return result;
        } catch (SQLException e) {
            System.out.println("update Failed ");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(String phoneNumber,String oldPassword ,  String newPassword) {
        System.out.println("change password called");
        try(PreparedStatement preparedStatement = connection.prepareStatement("update user set password = ? where PhoneNumber = ? and password = ?")){
            preparedStatement.setString(1,Utilities.Hash(newPassword));
            preparedStatement.setString(2,phoneNumber);
            preparedStatement.setString(3,Utilities.Hash(oldPassword));
            return preparedStatement.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean changeStatus(String phoneNumber, int status) {
        System.out.println(" changeStatus called");
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("update user set status = ? where PhoneNumber = ?")) {
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, phoneNumber);
            boolean result = preparedStatement.executeUpdate() > 0;

            UsersInfo.updateList();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void signOut(String phoneNumber) throws RemoteException, SQLException {
        clients.remove(phoneNumber);
    }

    @Override
    public List<Integer> addNewContact(String sender, List<String> contactList) throws RemoteException, SQLException {
        List <User> users = UsersInfo.getAllUsersfromDB();  // all registration users
        List <Integer> invitationStatus = new ArrayList<>(); // 0 not exist // 1 sucess // 2 already invited
        List <String> contacts=new ArrayList<>();            // all registration users phonenumber
        for (User user : users) {
            contacts.add(user.getPhoneNumber());
        }

        for(String contactPhoneNumber: contactList) {

            if(!contacts.contains(contactPhoneNumber)) {
                invitationStatus.add(0);
                System.out.println("not register");
            } else {
                boolean isSending=sendInvitation(sender, contactPhoneNumber);
                if(isSending) {
                    invitationStatus.add(1); 
                    System.out.println("invited successfully");
                } else {
                    invitationStatus.add(2); 
                    System.out.println("aleardy sending invitation");
                }
            }
        }
        return invitationStatus;
    }

    public boolean creatGroup(Group group) {
        System.out.println("create group");
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("insert into chatgroup  (group_name,image) VALUES(?,?)")) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setBytes(2,group.getImage());
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
        System.out.println("addGroupMember");
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
    public List<User> selectUserContacts(String userPhoneNumber) {
        List<User> contactList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "SELECT user.* FROM user user, usercontacts contact where user.PhoneNumber = contact.contact_id And  contact.user_id = ?")) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            contactList.addAll(UserFactory.createUserList(resultSet));
            System.out.println("selectUserContacts" + contactList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
    @Override
    public synchronized boolean sendFile(byte[] buffer, int count, String reciever, String fileName) throws RemoteException {
        // TODO Auto-generated method stub
        
        ClientDao recieverClient = clients.get(reciever);
        System.out.println("sending file name: "+fileName+" count "+count);
        //for (int i = 0; i < count; i++)
            //System.out.print((char) buffer[i]);
        
        return recieverClient.downLoadFile(buffer, count, fileName);
    }
    public List<Group> selectGroups(String userPhoneNumber){
        System.out.println("selectGroups");
        List<Group> GroupList = new ArrayList<Group>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "select id ,group_name ,chatgroup.image from user,chatgroup ,contactgroup where id = group_id and contact_id=phoneNumber and phoneNumber=?")) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                GroupList.add(new Group(resultSet.getInt(1), resultSet.getString(2),resultSet.getBytes(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GroupList;
    }

    public List<String> selectGroupMembers(int GroupId){
        System.out.println("selectMembers");
        List<String> groupMemberList = new ArrayList<String>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "select contact_id from contactgroup where group_id = ?")) {
            preparedStatement.setInt(1, GroupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                groupMemberList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMemberList;
    }

    @Override
    public void tellOthers(Message message, int GroupId) throws RemoteException, SQLException {
        List<String> groupMemberList= selectGroupMembers(GroupId);
        for(String memberPhoneNumber:  groupMemberList){
            if(clients.containsKey(memberPhoneNumber)){
                clients.get(memberPhoneNumber).recievedGroupMessage(message);
            }
        }
        
    }
    @Override
    public void SendContactMessage(Message message) throws RemoteException, SQLException {
        System.out.println("private");
        if(clients.containsKey(message.getReceiverPhoneNumber())){
            clients.get(message.getReceiverPhoneNumber()).recievedContactMessage(message);
        } else {
            System.out.println("client is not register" + message.getReceiverPhoneNumber());
        }
    }

    @Override
    public void SendGroupMessage(Message message) throws RemoteException, SQLException {
        System.out.println("send to group");
        //message.setSenderPhoneNumber(message.getReceiverPhoneNumber());
        selectGroupMembers( Integer.parseInt( message.getReceiverPhoneNumber())).forEach((contact) ->{
            System.out.println("client is "+clients);
            if(clients.containsKey(contact))
            try {
                if(!contact.equals(message.getSenderPhoneNumber()))
                clients.get(contact).recievedContactMessage(message);
                //else System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        
    }

    @Override
    public void notifyChanges(User user) {
        selectUserContacts(user.getPhoneNumber()).forEach((contact) ->{
            if(clients.containsKey(contact.getPhoneNumber()))
            try {
                clients.get(contact.getPhoneNumber()).notifyUserChanges(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }); 
    }
    public User selectUser(String phoneNumber){
        User user=null;
        try (PreparedStatement preparedStatement = connection
        .prepareStatement(
                "select * from User where PhoneNumber = ?")) {
            preparedStatement.setString(1,phoneNumber);
    ResultSet resultSet = preparedStatement.executeQuery();
        user=UserFactory.createUser(resultSet);
} catch (SQLException e) {
    e.printStackTrace();
}
return user;
    }
    public void notifyCreatingGroup(Group group) {
        selectGroupMembers(group.getGroupId()).forEach((contact) ->{
            if(clients.containsKey(contact))
            try {
                System.out.println(contact);
                clients.get(contact).notifyCreatingGroup(group);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });     
    }
}
