package gov.iti.presistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import gov.iti.model.User;
import gov.iti.presistance.DataBase.ConnectionManager;

public class UsersInfo {
    public static int numOfOnline = 0 ;
    // public static int numOfOffline ;
    public static int FemaleUsers = 0  ;
    // public static int maleUsers = 0 ;

    public static ArrayList<User> usersList = new ArrayList<User>();

    public static List<User> getAllUsersfromDB() {
        ResultSet result = FromQueryToResultSeT("select * from user");
        try {
            while (result.next()) {
                // byte[] fakeImage = new byte[18];
                usersList.add(new User(result.getString(1), result.getString(2), result.getInt(3), result.getInt(4),
                        result.getInt(5), result.getBytes(6), result.getString(8), result.getString(9),
                        result.getString(10), result.getString(11)));
            }
        } catch (SQLException error) {
            System.out.println("failed on adding to users list ");
            return null;
        }
        return usersList;
    }

    private static ResultSet FromQueryToResultSeT(String query) {
        try {
            Statement statement = ConnectionManager.connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException e) {
            System.out.println("query Failed DBuser");
            return null;
        }
    }

    public static void updateList() {

        usersList.clear();
        getAllUsersfromDB();
        System.out.println("Numer of users = " + usersList.size());
    }

    public static boolean isRegistered(User user) {
        String phone = user.getPhoneNumber();
        for (User existedUser : usersList)
            if (existedUser.getPhoneNumber().equals(phone))
                return true;
        return false;
    }

    // public static boolean isOnline(User user) {
    //     // 
    // }


}