package gov.iti.model;

import java.io.Serializable;

public class Invitation implements Serializable{
    private int id;
    private User sender;
    private User receiver;

    public Invitation(int id, User sender, User receiver) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "sender name" + sender.getName() + "\n recieverName" + receiver.getName();
    }
}
