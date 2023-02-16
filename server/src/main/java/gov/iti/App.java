package gov.iti;

import java.io.IOException;
import java.sql.SQLException;

import gov.iti.presistance.connection.ClientServerConnection;
import gov.iti.presistance.dao.ServerImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws SQLException, IOException {

        ClientServerConnection.getConnectionInstance();
        //ServerImpl.filterOnlineUsers();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Application");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("server.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
