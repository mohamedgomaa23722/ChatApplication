package gov.iti.presistance.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.model.User;

public class UserFactory {
    public static User createUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            Blob blob = resultSet.getBlob("image");
            byte [] bytes = blob.getBytes(1l, (int)blob.length());
            user = new User(resultSet.getString("PhoneNumber"),
                    resultSet.getString("Name"),
                    Integer.parseInt(resultSet.getString("age")),
                    resultSet.getInt("status"),
                    resultSet.getInt("mode"),
                    bytes,
                    resultSet.getString("email"),
                    resultSet.getString("country"),
                    resultSet.getString("bio"),
                    resultSet.getString("gender"));
        }
        resultSet.close();
        return user;
    }

    public static List<User> createUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            Blob blob = resultSet.getBlob("image");
            byte [] bytes = new byte[(int)blob.length()];
            try {
                blob.getBinaryStream().read(bytes);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println(bytes.length);
            users.add(new User(resultSet.getString("PhoneNumber"),
                    resultSet.getString("Name"),
                    resultSet.getInt("age"),
                    resultSet.getInt("status"),
                    resultSet.getInt("mode"),
                    bytes,
                    resultSet.getString("email"),
                    resultSet.getString("country"),
                    resultSet.getString("bio"),
                    resultSet.getString("gender")));
        }
        resultSet.close();
        return users;
    }
}
