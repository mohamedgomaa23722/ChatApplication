package gov.iti.presentation.controller.SubItemController;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.presentation.dto.Message;
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
    
    private Message message;
    private boolean isReceived;

    public MessageItemController(Message message, boolean isReceived) {
        this.message = message;
        this.isReceived = isReceived;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLabel.setText(message.getMessage());
        userNameLabel.setText(message.getSenderPhone());
        userImageCircle.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("test.jpg").toExternalForm())));

        if(isReceived){
            messageBox.setAlignment(Pos.CENTER_LEFT);
            setMessageStyle("recivedMessageBox","reciverName", "recivedMessageLabel", "recivedMessageTimeLabel");

        }else{
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            setMessageStyle("sendMessageBox","senderName", "sendMessageLabel", "sendMessageTimeLabel");
        }
    }
    

    private void setMessageStyle(String containerStyle, String nameStle, String messageStyle, String messageTimeStyle) {
        messageContainer.getStyleClass().add(containerStyle);
        userNameLabel.getStyleClass().add(nameStle);
        messageLabel.getStyleClass().add(messageStyle);
        messageTimeLabel.getStyleClass().add(messageTimeStyle);
    }
}
