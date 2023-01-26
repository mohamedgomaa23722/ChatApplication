package gov.iti.utils;


public class Intent {

    private static Intent instance;
    private String name;
    private String imagePath;

    private Intent() {
    }

    public static Intent getInent() {
        if (instance == null)
            instance = new Intent();
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}