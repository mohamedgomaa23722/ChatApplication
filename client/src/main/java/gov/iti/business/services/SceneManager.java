package gov.iti.business.services;


public class SceneManager {

    private static SceneManager instance;
    private String name;
    private String imagePath;

    private SceneManager() {
    }

    public static SceneManager getInent() {
        if (instance == null)
            instance = new SceneManager();
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