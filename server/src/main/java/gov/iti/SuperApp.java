package gov.iti;

import java.io.IOException;
import java.sql.SQLException;

public class SuperApp {
        public static void main(String[] args) {

            try {
                App.main(args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
