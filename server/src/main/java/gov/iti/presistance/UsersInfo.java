package gov.iti.presistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gov.iti.presentation.controller.ServerController;

import java.sql.Statement;

import gov.iti.model.User;
import gov.iti.presistance.DataBase.ConnectionManager;
import javafx.application.Platform;

import static gov.iti.presistance.dao.ServerImpl.clients;

public class UsersInfo {

    public static ArrayList<User> usersList = new ArrayList<User>();
    public static int numOfOnline = clients.size();
    public static int numOfOffline = usersList.size() - numOfOnline;
    public static int numOfonlineMale = 0;
    public static int maleUsers = 0;
    public static Map<String, Map<String, Long>> statistics;
    static boolean flag = false;

    public static List<User> getAllUsersfromDB() {
        ResultSet result = FromQueryToResultSeT("select * from user");
        try {
            while (result.next()) {
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
        updateFields();
        updateView();
    }

    public static boolean isRegistered(User user) {
        String phone = user.getPhoneNumber();
        for (User existedUser : usersList)
            if (existedUser.getPhoneNumber().equals(phone))
                return true;
        return false;
    }

    private static void updateFields() {
        numOfOnline = clients.size();
        numOfOffline = usersList.size() - numOfOnline;
    }

    public static void updateView() {

        maleUsers = (int) usersList.stream()
                .filter(p -> p.getGender().equals("m")).count();

        numOfonlineMale = (int) clients.keySet().stream()
                .filter(p -> getUserByphone(p).getGender().equals("m")).count();
                
        statistics = usersList.stream()
                .collect(
                        Collectors.groupingBy(User::getCountry,
                                Collectors.groupingBy(User::getGender,
                                        Collectors.counting())));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ServerController.instance.updateCharts();
            }
        });
    }

    public static User getUserByphone(String phone) {
        for (User user : usersList) {
            if (user.getPhoneNumber().equals(phone))
                return user;
        }
        return null;
    }
}