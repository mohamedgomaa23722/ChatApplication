package gov.iti.presentation.dtos;

public class Group {
    
    private Group group;
    private boolean newMessage = false;
    
    public Group(Group group, boolean newMessage) {
        this.group = group;
        this.newMessage = newMessage;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
    public boolean isNewMessage() {
        return newMessage;
    }
    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    
}
