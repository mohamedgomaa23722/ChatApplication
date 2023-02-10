package gov.iti.presentation.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final static SceneManager instance = new SceneManager();
    private static Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    String sceneName;

    private SceneManager() {
    }

    public static SceneManager getSceneManagerInstance() {
        return instance;
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

    public void switchToPasswdLoginScreen() {
        sceneName = "LoginPasswd";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToSignUpScreen() {
        sceneName = "SignUp";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToChatScreen() {
        sceneName = "chatPage";
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



}