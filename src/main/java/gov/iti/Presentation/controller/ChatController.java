package gov.iti.Presentation.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.Presentation.dto.Message;
import gov.iti.Presentation.utils.MessageItem;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ChatController implements Initializable {

    @FXML
    private HBox top_bar;
    @FXML
    private Circle contact_image;
    @FXML
    private Label contact_name;
    @FXML
    private Circle contact_circle_status;
    @FXML
    private Label contact_status;
    @FXML
    private ScrollPane scrollBar;
    @FXML
    private HBox message_edx_container;
    @FXML
    private TextField message_edx;
    @FXML
    private ImageView notification;
    @FXML
    private ImageView settings;
    @FXML
    private TextField search_edx;
    @FXML
    private Text contact_title;
    @FXML
    private ImageView add_contact;
    @FXML
    private ListView<?> contact_list;
    @FXML
    private Text contact_title1;
    @FXML
    private ImageView add_group;
    @FXML
    private ListView<?> group_list;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView bottomImage;
    @FXML
    private ImageView attach_file;
    @FXML
    private VBox chatBox;
    /**
     * Initializes the controller class.
     */
    int  message = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Message> observable = FXCollections.<Message>emptyObservableList();

        message_edx.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                addMessage(new Message(message++, "01068053092", message_edx.getText(), null),MessageItem.SEND_MESSAGE);
            }
        });
        

    }    

    private void addMessage(Message message, int status){
        MessageItem messageBox = new MessageItem(message, status);
        chatBox.getChildren().add(messageBox);
        message_edx.clear();
    }

}

