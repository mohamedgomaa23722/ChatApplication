package gov.iti.Presentation.dto;

import java.util.List;

public class Group {
    private int groupId;
    private String groupName;
    private List<Contact> groupContacts;
    
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
