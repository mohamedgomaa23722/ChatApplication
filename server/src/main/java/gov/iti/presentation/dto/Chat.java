package gov.iti.presentation.dto;

import javafx.scene.image.Image;

public class Chat{
    private String chatId;
    private boolean isGroup;
    private Image image;

    public Chat(String chatId, boolean isGroup, Image image) {
        this.chatId = chatId;
        this.isGroup = isGroup;
        this.image = image;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    
}
