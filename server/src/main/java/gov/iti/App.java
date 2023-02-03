package gov.iti;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.sql.SQLException;

import gov.iti.presistance.connection.ClientServerConnection;
import gov.iti.presistance.dao.ServerImpl;
import gov.iti.model.User;
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
        //boolean result = serverImpl.register(new User("203040", "khkhkhkhkhkhhk", 25, "m", image, "a@yahoo.com", "asgasgasg", "egypt", 0, 0), "5268219");
        
        //boolean result3 = serverImpl.login("015678987", "2626");

        //System.out.println(" : " +result3);
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
