package gov.iti.Presentation.dto;

public class Chat {
    private int chatId;
    private boolean isGroup;
    
    public Chat(int chatId, boolean isGroup) {
        this.chatId = chatId;
        this.isGroup = isGroup;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }


    
    


}
