package gov.iti.presentation.controller.subItemController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.dtos.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MessageItemController implements Initializable {
    @FXML
    private VBox messageBox;

    @FXML
    private VBox messageContainer;

    @FXML
    private Label messageTimeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Circle userImageCircle;

    private gov.iti.model.Message message;
    private boolean isReceived;

    public MessageItemController(gov.iti.model.Message message, boolean isReceived) {
        this.message = message;
        this.isReceived = isReceived;
    }

    User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (isReceived) {
            System.out.println(
                    "Receiver : " + message.getReceiverPhoneNumber() + "\nSender : " + message.getSenderPhoneNumber()
                            + "\nCurrent User : " + CurrentUser.getCurrentUser().getPhoneNumber().get());

            CurrentUser.getCurrentUser().getContacts().forEach((u) -> {
                if (u.getPhoneNumber().equals(message.getSenderPhoneNumber())) {
                    user = u;
                    System.out.println("find user");
                }
            });
        } else {
            user = CurrentUser.getCurrentUser().getUser();
        }
        messageLabel.setText(message.getMessage());
        userNameLabel.setText(message.getSenderPhoneNumber());
        userImageCircle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getImage()))));

        if (isReceived) {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            setMessageStyle("recivedMessageBox", "reciverName", "recivedMessageLabel", "recivedMessageTimeLabel");

        } else {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            setMessageStyle("sendMessageBox", "senderName", "sendMessageLabel", "sendMessageTimeLabel");
        }
    }

    private void setMessageStyle(String containerStyle, String nameStle, String messageStyle, String messageTimeStyle) {
        messageContainer.getStyleClass().add(containerStyle);
        userNameLabel.getStyleClass().add(nameStle);
        messageLabel.getStyleClass().add(messageStyle);
        messageTimeLabel.getStyleClass().add(messageTimeStyle);
    }
}
