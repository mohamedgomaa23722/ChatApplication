package gov.iti;

import java.io.IOException;
import java.net.Inet4Address;
import java.sql.SQLException;

import gov.iti.presistance.connection.ClientServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws SQLException, IOException {
        
        ClientServerConnection.getConnectionInstance();
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        
        // try (Scanner scanner = new Scanner(System.in)) {
        //     while(true){
        //         if(scanner.nextLine() == "c"){
        //             break;
        //         }
        //     }
        // }
        
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Application");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("server.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getClassLoader().getResource("AppStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
