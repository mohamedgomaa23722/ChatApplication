package gov.iti.model;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {
    private String senderPhoneNumber;
    private String ReceiverPhoneNumber;
    private String message;
    private File attachment;
    
    public Message( String senderPhoneNumber, String ReceiverPhoneNumber, String message, File attachment) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.ReceiverPhoneNumber = ReceiverPhoneNumber;
        this.message = message;
        this.attachment = attachment;
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

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public String getReceiverPhoneNumber() {
        return ReceiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        ReceiverPhoneNumber = receiverPhoneNumber;
    }

    
}
