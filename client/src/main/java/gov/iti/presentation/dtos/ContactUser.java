package gov.iti.presentation.dtos;

import gov.iti.model.User;

public class ContactUser {
    private User user;

    private boolean newMessage = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

}
