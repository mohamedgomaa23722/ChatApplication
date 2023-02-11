package gov.iti.model;

import java.io.Serializable;

public class Invitation implements Serializable{
    private int id;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;

    public Invitation(int id, String senderPhoneNumber, String receiverPhoneNumber) {
        this.id = id;
        this.senderPhoneNumber = senderPhoneNumber;
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }



    @Override
    public String toString() {
        return "sender name" + senderPhoneNumber + "\n recieverName" + receiverPhoneNumber;
    }
}
