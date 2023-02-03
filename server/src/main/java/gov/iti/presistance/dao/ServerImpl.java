package gov.iti.presistance.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import gov.iti.presentation.dto.Group;
import gov.iti.presistance.DataBase.ConnectionManager;
import gov.iti.dao.ServerDao;
import gov.iti.model.User;

public class ServerImpl extends UnicastRemoteObject implements ServerDao{

    private Connection connection;

    public ServerImpl() throws RemoteException, SQLException {
        super();
        connection = ConnectionManager.getInstance().getStatement();
    }

    @Override
    public boolean login(String phoneNumber, String password) throws RemoteException, SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select phoneNumber, password from user where phoneNumber = ? AND password = ?");
        preparedStatement.setString(1, phoneNumber);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean register(User user, String Password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into user(PhoneNumber, Name, age, status, mode, image, password, email, country, bio, gender) values(?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getPhoneNumber());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setInt(4, user.getStatus());
        preparedStatement.setInt(5, user.getMode());
        preparedStatement.setBytes(6, user.getImage());
        preparedStatement.setString(7, Password);
        preparedStatement.setString(8, user.getEmail());
        preparedStatement.setString(9, user.getCountry());
        preparedStatement.setString(10, user.getBio());
        preparedStatement.setString(11, user.getGender());
        return preparedStatement.execute();
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
    public boolean updateStatues(int userId) throws RemoteException, SQLException {
        return false;
    }

    @Override
    public boolean updateMode(int userId) throws RemoteException, SQLException {
        return false;
    }

    @Override
    public boolean UpdateProfile(User user) {
        return false;
    }

    @Override
    public boolean ChangePassword(String phoneNumber, String newPassword) {
        return false;
    }

    @Override
    public boolean ChangeStatus(String phoneNumber, int status) {
        return false;
    }
    
}
