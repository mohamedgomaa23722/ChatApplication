package gov.iti.presentation.dtos;

import gov.iti.model.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurrentUser {
    private final static CurrentUser instance = new CurrentUser();
    private StringProperty PhoneNumber;
    private StringProperty name;
    private StringProperty gender;
    private byte[] image;
    private StringProperty email;
    private StringProperty bio;
    private StringProperty country;
    private IntegerProperty status;
    private IntegerProperty age;
    private String password;

    private CurrentUser() {
        password = "";
        status = new SimpleIntegerProperty();
        age = new SimpleIntegerProperty();
        PhoneNumber = new SimpleStringProperty();
        name = new SimpleStringProperty();
        gender = new SimpleStringProperty();
        email = new SimpleStringProperty();
        bio = new SimpleStringProperty();
        country = new SimpleStringProperty();
    }

    public static CurrentUser getCurrentUser(){
        return instance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        PhoneNumber.set(user.getPhoneNumber());
        name.set(user.getName());
        age.set(user.getAge());
        gender.set(user.getGender());
        image = user.getImage();
        email.set(user.getEmail());
        bio.set(user.getBio());
        status.set(user.getStatus());
        System.out.println("Updates");
    }

    public User getUser() {
        return new User(getPhoneNumber().get(),
        getName().get(), 
        getAge().get(),
        getGender().get(),
        getImage(),
        getEmail().get(),
        getBio().get(),
        getCountry().get(),
        getStatus().get(),
        0);
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    public StringProperty getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber.set(phoneNumber);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public IntegerProperty getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);;
    }

    public StringProperty getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }

    public StringProperty getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public IntegerProperty getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    
}
