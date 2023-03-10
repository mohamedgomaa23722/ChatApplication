package gov.iti.presentation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.presentation.controller.SubItemController.ContactItemController;
import gov.iti.presentation.controller.SubItemController.MessageItemController;
import gov.iti.presentation.dto.Chat;
import gov.iti.presentation.dto.Contact;
import gov.iti.presentation.dto.Group;
import gov.iti.presentation.dto.Message;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
    private ListView<Chat> contact_list;
    @FXML
    private Text contact_title1;
    @FXML
    private ImageView add_group;
    @FXML
    private ListView<Chat> group_list;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView bottomImage;
    @FXML
    private ImageView attach_file;
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scrollPane;
    /**
     * Initializes the controller class.
     */
    int message = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        message_edx.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                addMessage(new Message(message++, "01068053092", message_edx.getText(), null), false);
            } else if (e.getCode() == KeyCode.ALT) {
                addMessage(new Message(message++, "01068053092", message_edx.getText(), null), true);
            }
        });

        List<Contact> contacts = new ArrayList<>();
        for (int index = 0; index < 14; index++) {
            contacts.add(new Contact(String.valueOf(index), "gomaa" + index, index, "m", "",
                    new Image(getClass().getClassLoader().getResource("test.jpg").toExternalForm()), null, null, 1));
        }

        ObservableList<Chat> observableList = FXCollections.observableArrayList(contacts);
        contact_list.setItems(observableList);
        contact_list.setCellFactory(p -> new FolderListCell());

        List<Group> groups = new ArrayList<>();
        for (int index = 0; index < 14; index++) {
            groups.add(new Group(String.valueOf(index), "Team" + index,
                    new Image(getClass().getClassLoader().getResource("test.jpg").toExternalForm())));
        }

        ObservableList<Chat> observableList1 = FXCollections.observableArrayList(groups);
        group_list.setItems(observableList1);
        group_list.setCellFactory(p -> new FolderListCell());
    }

    private void addMessage(Message message, boolean status) {
        try {
            FXMLLoader loader = new FXMLLoader();
            MessageItemController messageBox = new MessageItemController(message, status);
            loader.setController(messageBox);
            VBox v = loader.load(getClass().getClassLoader().getResource("SendMessageItem.fxml").openStream());
            chatBox.getChildren().add(v);
            scrollPane.vvalueProperty().bind(chatBox.heightProperty());
            message_edx.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class FolderListCell extends ListCell<Chat> {
    @Override
    public void updateItem(Chat item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                ContactItemController controller = new ContactItemController(item);
                loader.setController(controller);
                HBox view = loader.load(getClass().getClassLoader().getResource("ChatItem.fxml").openStream());
                this.setGraphic(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
