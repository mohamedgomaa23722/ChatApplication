package gov.iti;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import gov.iti.model.User;
import gov.iti.presistance.dao.ServerImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws SQLException, IOException {
        
        ClientServerConnection.getConnectionInstance();
        //ServerImpl serverImpl = new ServerImpl();
        //Test Registeration
        File file = new File("D:\\textEditor\\travis_scott.jpg");
        byte[] image = new byte[(int)file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(image);
        fileInputStream.close();
       
        /*serverImpl.register(new User("567", "khkhkhkhkhkhhk", 25, "m", image, "a@yahoo.com", "asgasgasg", "egypt", 0, 0), "5268219");
        serverImpl.register(new User("578", "khkhkhkhkhkhhk", 25, "m", image, "a@yahoo.com", "asgasgasg", "egypt", 0, 0), "5268219");
        serverImpl.register(new User("579", "khkhkhkhkhkhhk", 25, "m", image, "a@yahoo.com", "asgasgasg", "egypt", 0, 0), "5268219");
*/
        boolean result3 = serverImpl.changeStatus("567", 1);

        System.out.println(" : " + result3);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Application");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("homePage.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("AppStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
