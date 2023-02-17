package gov.iti.presentation.controller.subItemController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import java.awt.Desktop;


import gov.iti.business.services.ContactsService;
import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AttachmentController implements Initializable {
    @FXML
    private VBox fileContainer;
    @FXML
    private Label fileName;
    @FXML
    private Label SenderName;
    @FXML
    private Label fileSize;
    @FXML
    private VBox messageBox;

    private Message message;
    private boolean status;

    public AttachmentController(Message message, boolean status) {
        this.message = message;
        this.status = status;
    }
    User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (status) {
            CurrentUser.getCurrentUser().getContacts().forEach((u) -> {
                if (u.getPhoneNumber().equals(message.getSenderPhoneNumber())) {
                    user = u;
                }
            });
            if (user == null) {
                user = ContactsService.getcontactsService().getUser(message.getSenderPhoneNumber());
            }
        } else {
            user = CurrentUser.getCurrentUser().getUser();
        }

        fileName.setText(message.getAttachment().getFileName());
        fileSize.setText(message.getAttachment().getFileSize());

        SenderName.setText(user.getName());

        if (status) {
            messageBox.setAlignment(Pos.CENTER_LEFT);

        } else {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    @FXML
    public void openFile() {
        try {
            System.out.println("clciked on open");
            // constructor of file class having file as argument
            File file = new File(message.getAttachment().getFilePath());
            if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
            {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) // checks file exists or not
                desktop.open(file); // opens the specified file

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
