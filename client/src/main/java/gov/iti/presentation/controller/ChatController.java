package gov.iti.presentation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.processing.SupportedOptions;

import gov.iti.business.services.ChatService;
import gov.iti.model.Group;
import gov.iti.presentation.controller.subItemController.ContactItemController;
import gov.iti.presentation.controller.subItemController.MessageItemController;
import gov.iti.presentation.dtos.Chat;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.model.Message;
import gov.iti.model.User;
import gov.iti.presentation.utils.ChatManager;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.Status;
import gov.iti.presentation.utils.WindowManger;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ChatController<E> implements Initializable {

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
    private ListView<User> contact_list;
    @FXML
    private Text contact_title1;
    @FXML
    private ImageView add_group;
    @FXML
    private ListView<Group> group_list;
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
    @FXML
    private VBox windowContainer;
    @FXML
    private VBox viewContainer;
    @FXML
    private VBox empty_chat;
    @FXML
    private ImageView empty_contact;
    @FXML
    private ImageView empty_Group;
    /**
     * Initializes the controller class.
     */
    int chatMode = 0;
    User receiverUSer;
    private Group currentGroupChat;
    /**
     * Initializes the controller class.
     */
    int message = 0;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChatVisiablity(false);

        message_edx.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                Message sMessage=null;
                if(chatMode==1){
                sMessage = new Message(CurrentUser.getCurrentUser().getPhoneNumber().get(),
                        receiverUSer.getPhoneNumber(), message_edx.getText(), null);
                }
                else{
                    sMessage = new Message(CurrentUser.getCurrentUser().getPhoneNumber().get(),
                    Integer.toString(currentGroupChat.getGroupId()), message_edx.getText(), null);
                }
                addMessage(sMessage, false);
                ChatService.getInstance().sendMessage(sMessage, chatMode);
            }
        });

        Platform.runLater(() -> {
            contact_list.setItems(CurrentUser.getCurrentUser().getContacts());
            contact_list.setCellFactory(p -> new ContactCell());
        });
         
        Platform.runLater(() -> {
            group_list.setItems(CurrentUser.getCurrentUser().getGroups());
            group_list.setCellFactory(p -> new GroupCell());
        });
         group_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chatBox.getChildren().removeAll(chatBox.getChildren());
                setChatVisiablity(true);
                chatMode = 0;
                currentGroupChat = group_list.getSelectionModel().getSelectedItem();
                System.out.println(currentGroupChat);
                contact_name.setText(currentGroupChat.getGroupName());
                contact_image.setFill(new ImagePattern(new Image(new ByteArrayInputStream(currentGroupChat.getImage()))));
                chatBox.getChildren().add(ChatManager.getInstance().getMessages(Integer.toString(currentGroupChat.getGroupId())));
            }
        });
   

        CurrentUser.getCurrentUser().getContacts().addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> c) {
                if (c.getList().size() > 0) {
                    empty_contact.setVisible(false);
                } else {
                    empty_contact.setVisible(true);
                }
            }

        });
        CurrentUser.getCurrentUser().getGroups().addListener(new ListChangeListener<Group>() {
           
            @Override
            public void onChanged(Change<? extends Group> c) {
                System.out.println(CurrentUser.getCurrentUser().getGroups());
                if (c.getList().size() > 0) {
                    empty_Group.setVisible(false);
                } else {
                    empty_Group.setVisible(true);
                }
            }

        });

        contact_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chatBox.getChildren().removeAll(chatBox.getChildren());
                setChatVisiablity(true);
                chatMode = 1;
                receiverUSer = contact_list.getSelectionModel().getSelectedItem();
                contact_name.setText(receiverUSer.getName());
                changeStatusbar(receiverUSer.getStatus());
                contact_image.setFill(new ImagePattern(new Image(new ByteArrayInputStream(receiverUSer.getImage()))));
                chatBox.getChildren().add(ChatManager.getInstance().getMessages(receiverUSer.getPhoneNumber()));
            }
        });

        ChatService.getInstance().getMessage().addListener((o, oldMessage, newMessage) -> {
            if (newMessage != null) {
                addMessage(newMessage, true);
            }
        });

        WindowManger.getInstance().initializeView(windowContainer, viewContainer);
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
            System.out.println(status);
            if(message.getReceiverPhoneNumber().charAt(0)!=0){
                ChatManager.getInstance().addMessage(message.getReceiverPhoneNumber(), v);
            }
        else{
            if (status )
                ChatManager.getInstance().addMessage(message.getSenderPhoneNumber(), v);
            else {
                ChatManager.getInstance().addMessage(message.getReceiverPhoneNumber(), v);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSetting() throws IOException {
        WindowManger.getInstance().openSettingWindow();
    }

    @FXML
    private void closeWindow() {
        WindowManger.getInstance().closeWindow();
    }

    @FXML
    private void signOut() {
        setChatVisiablity(false);
        try {
            ChatService.getInstance().SignOut(CurrentUser.getCurrentUser().getPhoneNumber().get());
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }
        // TODO : REMOVE SAVED FILE
        SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
        CurrentUser.getCurrentUser().clearAll();
        ChatManager.getInstance().clearMap();
    }

    @FXML
    private void openNotification() throws RemoteException, SQLException {
        WindowManger.getInstance().openNotificationWindow();
    }

    @FXML
    private void addContact() {
        WindowManger.getInstance().openAddContactWindow();
    }

    @FXML
    private void handelCreateGroup() {
        WindowManger.getInstance().openCreatGroupWindow();
    }

    private void changeStatusbar(int status) {
        if (status == 0)
            changeStatusColors(Status.Offline);
        else if (status == 1)
            changeStatusColors(Status.online);
        else if (status == 2)
            changeStatusColors(Status.busy);
        else
            changeStatusColors(Status.away);
    }

    private void setChatVisiablity(boolean isvisible) {
        top_bar.setVisible(isvisible);
        contact_image.setVisible(isvisible);
        contact_circle_status.setVisible(isvisible);
        contact_image.setVisible(isvisible);
        message_edx_container.setVisible(isvisible);
        chatBox.setVisible(isvisible);
        empty_chat.setVisible(!isvisible);

    }

    private void changeStatusColors(Status status) {
        contact_circle_status.setFill(Color.web(status.color));
        contact_status.setText(status.text);
        contact_status.setTextFill(Color.web(status.color));
    }

}



class ContactCell extends ListCell<User> {
    @Override
    public void updateItem(User item, boolean empty) {
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
    }}
    class GroupCell extends ListCell<Group> {
        @Override
        public void updateItem(Group item, boolean empty) {
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
            else {
                this.setGraphic(null);
            }
        }
    }

