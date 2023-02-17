package gov.iti.presentation.dtos;

import java.util.List;

import gov.iti.model.Invitation;
import gov.iti.model.User;
import gov.iti.presentation.utils.ChatManager;
import gov.iti.model.Group;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private BooleanProperty isChangeImageProp;
    private String password;

    private ObservableList<Invitation> invitations;
    private ObservableList<User> contacts;
    private ObservableList<Group> groups;

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
        isChangeImageProp = new SimpleBooleanProperty(true);
        invitations = FXCollections.observableArrayList();
        contacts = FXCollections.observableArrayList();
        groups = FXCollections.observableArrayList();
    }

    public static CurrentUser getCurrentUser() {
        return instance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        System.out.println("Updated User");
        PhoneNumber.set(user.getPhoneNumber());
        name.set(user.getName());
        age.set(user.getAge());
        gender.set(user.getGender());
        image = user.getImage();
        email.set(user.getEmail());
        bio.set(user.getBio());
        status.set(user.getStatus());
        country.set(user.getCountry());
        if (image != user.getImage()) {
            System.out.println("Image Updated");
            isChangeImageProp.set(true);
        }
    }

    public User getUser() {
        return new User(getPhoneNumber().get(),
                getName().get(),
                getAge().get(),
                getStatus().get(),
                0,
                getImage(),
                getEmail().get(),
                getCountry().get(),
                getBio().get(),
                getGender().get());
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
        this.age.set(age);
        ;
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
        isChangeImageProp.set(false);
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

    public BooleanProperty getImageProp() {
        return isChangeImageProp;
    }

    public void setImageProp(Boolean status) {
        this.isChangeImageProp.set(status);
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations.addAll(invitations);
    }

    public void setContacts(List<User> contacts) {
        this.contacts.addAll(contacts);
    }

    public void setgroups(List<Group> groups) {
        this.groups.addAll(groups);
    }

    public void addInvitations(Invitation invitation) {
        this.invitations.add(invitation);
    }

    public void addContact(User contact) {
        this.contacts.add(contact);
    }

    public void addGroup(Group group) {
        ChatManager.getInstance().addGroup(Integer.toString(group.getGroupId()));
        Platform.runLater(() -> this.groups.add(group));
    }

    public ObservableList<Invitation> getInvitations() {
        return invitations;
    }

    public ObservableList<User> getContacts() {
        return contacts;
    }

    public ObservableList<Group> getGroups() {
        return groups;
    }

    public void clearAll() {
        invitations.clear();
        groups.clear();
        contacts.clear();
    }

    public boolean removeGroup(Group group) {
        return groups.remove(group);
    }
}
