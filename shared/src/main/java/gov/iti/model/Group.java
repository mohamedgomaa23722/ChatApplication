package gov.iti.model;

import java.io.Serializable;

public class Group implements Serializable{
    private int groupId;
    private String groupName ;
    private byte []image;
    private boolean hasNewMessage = false;
    public Group(int groupId, String groupName, byte []image) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.image = image;
    }
    public Group(String groupName, byte[] imge) {
        this.groupName = groupName;
        this.image = imge;
    }
   
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
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public boolean isHasNewMessage() {
        return hasNewMessage;
    }
    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    
}
