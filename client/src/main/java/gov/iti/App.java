package gov.iti;

import gov.iti.business.services.ChatService;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    String phoneNumber;

    String encryptedPassword;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = SceneManager.getSceneManagerInstance();
        sceneManager.initStage(primaryStage);
        sceneManager.switchToConnectionScreen();
        //sceneManager.switchToaddContactScreen();
        /* 
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root=fxmlLoader.load(getClass().getClassLoader().getResource("senderPage.fxml").openStream());
        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        */
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ChatService.getInstance().SignOut(CurrentUser.getCurrentUser().getPhoneNumber().get());
    }
}
