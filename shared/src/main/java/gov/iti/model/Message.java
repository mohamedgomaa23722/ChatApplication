package gov.iti.model;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {
    private int messageID;
    private User sender;
    private String message;
    private File attachment;
    
    public Message(int messageID, User sender, String message, File attachment) {
        this.messageID = messageID;
        this.sender = sender;
        this.message = message;
        this.attachment = attachment;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }
}
