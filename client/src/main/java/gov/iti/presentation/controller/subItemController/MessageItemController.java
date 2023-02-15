package gov.iti.presentation.controller.subItemController;

import java.io.ByteArrayInputStream;
import java.net.URL;

import java.util.ResourceBundle;

import gov.iti.model.MessageStyle;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.utils.TextStyle;
import gov.iti.business.services.ContactsService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
        SetupMessageProperties();
        if (isReceived) {
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

        messageLabel.setText(message.getMessage());
        userNameLabel.setText(user.getName());

        if (user != null) {
            System.out.println("userNuotNull");
        }
        userImageCircle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getImage()))));

        if (isReceived) {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            setMessageStyle("reciverName", "recivedMessageLabel", "recivedMessageTimeLabel");

        } else {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            setMessageStyle("senderName", "sendMessageLabel", "sendMessageTimeLabel");
        }
    }

    private void setMessageStyle(String nameStle, String messageStyle, String messageTimeStyle) {
        userNameLabel.getStyleClass().add(nameStle);
        messageTimeLabel.getStyleClass().add(messageTimeStyle);
    }

    private void SetupMessageProperties() {
        MessageStyle messageStyle = message.getMessageStyle();
        messageTimeLabel.setText(message.getTime());
        messageContainer.setBackground(new Background(
                new BackgroundFill(Color.web(messageStyle.getTextBackColor()), new CornerRadii(8), Insets.EMPTY)));
        messageLabel.setTextFill(Color.web(messageStyle.getTextColor()));
        messageLabel.setUnderline(messageStyle.isUnderLine());

        if (messageStyle.getTextStyle() == TextStyle.BOLD) {
            messageLabel.setFont(
                    Font.font(message.getMessageStyle().getFont(), FontWeight.BOLD, messageStyle.getTextSize()));
        } else if (messageStyle.getTextStyle() == TextStyle.ItALIC) {
            messageLabel
                    .setFont(Font.font(message.getMessageStyle().getFont(), FontWeight.MEDIUM, FontPosture.ITALIC,
                            messageStyle.getTextSize()));
        } else {
            messageLabel.setFont(
                    Font.font(message.getMessageStyle().getFont(), FontWeight.NORMAL, FontPosture.REGULAR,
                            messageStyle.getTextSize()));
        }

    }

    public String getContainerStyle(String backGround) {
        System.out.println(backGround);
        return "{-fx-background-color: " + backGround + ";-fx-background-radius: 10; -fx-border-radius: 10;}";
    }
}