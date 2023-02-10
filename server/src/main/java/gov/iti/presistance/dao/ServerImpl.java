package gov.iti.presistance.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import gov.iti.presistance.DataBase.ConnectionManager;
import gov.iti.Utilities;
import gov.iti.dao.ServerDao;
import gov.iti.model.User;

public class ServerImpl extends UnicastRemoteObject implements ServerDao {

    private Connection connection;

    public ServerImpl() throws RemoteException, SQLException {
        super();
        connection = ConnectionManager.getInstance().getStatement();
    }

    @Override
    public User login(String phoneNumber, String password) throws RemoteException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("select * from user where phoneNumber = ? AND password = ?")) {
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, Utilities.Hash(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserFactory.createUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(User user, String Password){
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
            // e.printStackTrace();
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

}
