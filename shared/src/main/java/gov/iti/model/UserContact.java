package gov.iti.model;

import java.io.Serializable;

public class UserContact implements Serializable{
    private String PhoneNumber;
    private String name;
    
    public UserContact(String phoneNumber, String name) {
        PhoneNumber = phoneNumber;
        this.name = name;
       }

    public UserContact(UserContact user) {
        this.PhoneNumber = user.getPhoneNumber();
        this.name = user.getName();

    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    
}
