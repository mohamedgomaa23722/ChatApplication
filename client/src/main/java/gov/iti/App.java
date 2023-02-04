package gov.iti;

import gov.iti.business.services.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        
        /*primaryStage.setTitle("Chat Application");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chatPage.fxml"));
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(getClass().getClassLoader().getResource("chatStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        SceneManager sceneManager = SceneManager.getSceneManagerInstance();
        sceneManager.initStage(primaryStage);
        sceneManager.switchToPhoneLoginScreen();
        primaryStage.show();
    }
}
