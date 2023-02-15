package gov.iti.presentation.dtos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gov.iti.model.Attachment;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CurrentSelectedChat {

    private static final CurrentSelectedChat currentSelectedChat = new CurrentSelectedChat();
    private List<Attachment> attachments;
    private BooleanProperty isSendingFiles;
    private String receiverNumber;
    
    private CurrentSelectedChat() {
        receiverNumber = "";
        attachments = new ArrayList<>();
        isSendingFiles = new SimpleBooleanProperty(false);
    }

    public static CurrentSelectedChat getCurrentselectedchat() {
        return currentSelectedChat;
    }

    public void addAttachment(File file) {
        attachments.add(new Attachment(file.getName(), "", Long.toString(file.getTotalSpace())));
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public BooleanProperty getIsSendingFilesProp() {
        return isSendingFiles;
    }

    public void setIsSendingFilesProp(boolean isSendingFiles) {
        this.isSendingFiles.set(isSendingFiles);;
    }

    public void clearAttachments(){
        attachments.clear();
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public BooleanProperty getIsSendingFiles() {
        return isSendingFiles;
    }

    public void setIsSendingFiles(BooleanProperty isSendingFiles) {
        this.isSendingFiles = isSendingFiles;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }
    
    
}
