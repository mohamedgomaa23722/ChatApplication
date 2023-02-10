package gov.iti.presistance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import gov.iti.model.User;

public class UserFactory {
    public static User createUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            user = new User(resultSet.getString("PhoneNumber"),
                    resultSet.getString("Name"),
                    resultSet.getInt("age"),
                    resultSet.getInt("status"),
                    resultSet.getInt("mode"),
                    resultSet.getBytes("image"),
                    resultSet.getString("email"),
                    resultSet.getString("country"),
                    resultSet.getString("bio"),
                    resultSet.getString("gender")
                    );
        }
        resultSet.close();
        return user;
    }
}
