package gov.iti.model;

public class User {
    private String PhoneNumber;
    private String name;
    private int age;
    private String gender;
    private byte[] image;
    private String email;
    private String bio;
    private String country;
    private int status;
    private int mode;
    
    public User(String phoneNumber, String name, int age, String gender, byte[] image, String email, String bio,
            String country, int status, int mode) {
        PhoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.bio = bio;
        this.country = country;
        this.status = status;
        this.mode = mode;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    
}
