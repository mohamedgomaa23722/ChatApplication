package gov.iti.business.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gov.iti.presentation.dtos.LoggedUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private String name;
    private String imagePath;
    String sceneName;
    LoggedUser loggedUser;
    private SceneManager() {
    }

    public static SceneManager getSceneManagerInstance() {
        if (instance == null)
            instance = new SceneManager();
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage Already been initialized");
        }
        primaryStage = stage;
    }

    public void switchToPhoneLoginScreen() {
        sceneName = "LoginPhone";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToPasswdLoginScreen(LoggedUser user) {
        sceneName = "LoginPasswd";
        loggedUser=user;
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToSignUpScreen() {
        sceneName = "SignUp";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }


    public void loadView(String name) {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey(name)) {
            try {
                System.out.println("Created New Scene");
                String fxmlName=name+"Fxml.fxml"; 
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root=fxmlLoader.load(getClass().getClassLoader().getResource(fxmlName).openStream());
                Scene loginScene = new Scene(root);
                scenes.put(name, loginScene);
                primaryStage.setScene(loginScene);
            } catch (IOException e) {
                System.out.println("IO Exception: Couldn't load FXML file");
                e.printStackTrace();
            }
        } else {
            System.out.println("Loaded Existing Scene");
            primaryStage.setScene(scenes.get(name));
        }
        System.out.println("loaded " + name);
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

}