package gov.iti.presentation.dto;

import java.util.List;

import javafx.scene.image.Image;

public class Group extends Chat {
    private String groupName;
    private List<Contact> groupContacts;

    public Group(String chatId, String groupName, Image image) {
        super(chatId, true,image);
        this.groupName = groupName;
    }
    
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public List<Contact> getGroupContacts() {
        return groupContacts;
    }
    public void setGroupContacts(List<Contact> groupContacts) {
        this.groupContacts = groupContacts;
    }

    
}
