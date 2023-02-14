package gov.iti.presentation.dtos;

import java.io.File;

public class Message {
    private int messageId;
    private String senderPhone;
    private String message;
    private File file;

    public Message(int messageId, String senderPhone, String message, File file) {
        this.messageId = messageId;
        this.senderPhone = senderPhone;
        this.message = message;
        this.file = file;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public File getMessageType() {
        return file;
    }

    public void setMessageType(File file) {
        this.file = file;
    }

}
