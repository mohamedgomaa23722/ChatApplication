package gov.iti.model;

import java.io.Serializable;

public class Message implements Serializable {
    private int group_id;
    private String senderPhoneNumber;
    private String ReceiverPhoneNumber;
    private String message;
    private Attachment attachment;
    private MessageStyle messageStyle;
    private String time;

    public Message( String senderPhoneNumber, String ReceiverPhoneNumber, String message, Attachment attachment, MessageStyle messageStyle, String time) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.ReceiverPhoneNumber = ReceiverPhoneNumber;
        this.message = message;
        this.attachment = attachment;
        this.messageStyle = messageStyle;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
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

    public MessageStyle getMessageStyle() {
        return messageStyle;
    }

    public void setMessageStyle(MessageStyle messageStyle) {
        this.messageStyle = messageStyle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    
}
