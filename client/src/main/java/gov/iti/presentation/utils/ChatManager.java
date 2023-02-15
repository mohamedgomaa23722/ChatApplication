package gov.iti.presentation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.iti.model.Message;
import gov.iti.presentation.dtos.CurrentUser;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ChatManager {
    private final Map<String, VBox> messages;
    private final static ChatManager instance = new ChatManager();

    private ChatManager(){
        messages = new HashMap<>();
    }

    public static ChatManager getInstance() {
        return instance;
    }

    public void addContacts(){
        CurrentUser.getCurrentUser().getContacts().forEach((user) -> {
            messages.put(user.getPhoneNumber(), new VBox());
        });
    }

    public void addGroups(){
        CurrentUser.getCurrentUser().getGroups().forEach((user) -> {
            messages.put(Integer.toString(user.getGroupId()), new VBox());
        });
    }

    public void addContanct(String key){
        messages.put(key, new VBox());
    }
    public void addGroup(String key){
        messages.put(key, new VBox());
    }

    public void addMessage(String Key, Parent parent){
        messages.get(Key).getChildren().add(parent);
    }

    public void clearMap(){
        messages.clear();
    }

    public VBox getMessages(String key) {
        return messages.get(key);
    }
}
