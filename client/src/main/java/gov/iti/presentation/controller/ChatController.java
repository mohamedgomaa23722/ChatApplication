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
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.chatbot.*;
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
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.model.MessageStyle;
import gov.iti.model.User;
import gov.iti.presentation.utils.ChatManager;
import gov.iti.presentation.utils.SceneManager;
import gov.iti.presentation.utils.Status;
import gov.iti.presentation.utils.WindowManger;
import gov.iti.utils.TextStyle;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
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
    private HBox statusview;
    @FXML
    private VBox message_edx_container;
    @FXML
    private TextField message_edx;
    @FXML
    private ImageView notification;
    @FXML
    private ImageView settings;
    @FXML
    private ImageView chatbot;
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
    private Circle userImage;
    @FXML
    private Label UserName;
    @FXML
    private Label UserPhone;

    private boolean botOn = false;

    @FXML
    private ImageView empty_Group;
    @FXML
    ToggleButton bold;
    @FXML
    ToggleButton italic;
    @FXML
    ToggleButton regular;
    @FXML
    ToggleButton underLine;
    @FXML
    ColorPicker text_color;
    @FXML
    ColorPicker textBackground;
    @FXML
    ComboBox textFont;
    @FXML
    ComboBox fontsize;
    @FXML
    ToggleGroup select;
    /**
     * Initializes the controller class.
     */
    int chatMode = 0;
    User receiverUSer;
    private MessageStyle messageStyle;

    private Group currentGroupChat;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            setUpInfoBar();
            SetupMessageProperties();
        });

        setChatVisiablity(false);

        ChatterBotFactory factory = new ChatterBotFactory();
        ChatterBot bot2 = null;
        try {
            bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ChatterBotSession bot2session = bot2.createSession();

        Tooltip.install(chatbot, new Tooltip("Turn me on and take a rest!"));

        message_edx.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleSendMessage();

            }
        });

            contact_list.setItems(CurrentUser.getCurrentUser().getContacts());
            contact_list.setCellFactory(p -> new ContactCell());
    

        CurrentUser.getCurrentUser().getInvitations().addListener((ListChangeListener<Invitation>) u -> {
            if (u.getList().size() > 0)
                notification.setImage(
                        new Image(getClass().getClassLoader().getResource("redNotification.png").toExternalForm()));
        });
         
        Platform.runLater(() -> {
            group_list.setItems(CurrentUser.getCurrentUser().getGroups());
            group_list.setCellFactory(p -> new GroupCell());
        });

         group_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                statusview.setVisible(false);
                chatBox.getChildren().removeAll(chatBox.getChildren());
                setChatVisiablity(true);
                chatMode = 0;
                currentGroupChat = group_list.getSelectionModel().getSelectedItem();
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
                statusview.setVisible(true);
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
                if (botOn) {
                    try {
                        String msg = bot2session.think(newMessage.getMessage());
                        Message sMessage = new Message(CurrentUser.getCurrentUser().getPhoneNumber().get(),
                                newMessage.getSenderPhoneNumber(), msg, null, messageStyle, getCurrentTime());
                        addMessage(sMessage, false);
                        ChatService.getInstance().sendMessage(sMessage, 1);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
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
            if (status&&message.getReceiverPhoneNumber().charAt(0)=='0'){
                    ChatManager.getInstance().addMessage(message.getSenderPhoneNumber(), v);
            }
            else if(!message.getSenderPhoneNumber().equals(CurrentUser.getCurrentUser().getPhoneNumber())){
                ChatManager.getInstance().addMessage(message.getReceiverPhoneNumber(), v);
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
        LoginPhoneController.setFail(false);
        SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
        new Thread(() -> {
            try {
                ChatService.getInstance().SignOut(CurrentUser.getCurrentUser().getPhoneNumber().get());
            } catch (RemoteException | SQLException e) {
                e.printStackTrace();
            }
            ChatManager.getInstance().clearMap();
        }).start();
        Platform.runLater(() -> {
            CurrentUser.getCurrentUser().clearAll();
        });
    }

    @FXML
    private void openNotification() throws RemoteException, SQLException {
        notification.setImage(new Image(getClass().getClassLoader().getResource("notification.png").toExternalForm()));
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

    @FXML
    private void handelUnderLineStyle() {
        messageStyle.setUnderLine(underLine.isSelected());
    }

    @FXML
    private void handleSendMessage() {
        Message sMessage=null;
        if(chatMode==1){
        sMessage = new Message(CurrentUser.getCurrentUser().getPhoneNumber().get(),
                receiverUSer.getPhoneNumber(), message_edx.getText(), null,messageStyle, getCurrentTime());
        }
        else{
            sMessage = new Message(CurrentUser.getCurrentUser().getPhoneNumber().get(),
            Integer.toString(currentGroupChat.getGroupId()), message_edx.getText(), null,messageStyle, getCurrentTime());
        }
        addMessage(sMessage, false);
        ChatService.getInstance().sendMessage(sMessage, chatMode);
    }

    private void setUpInfoBar() {
        userImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(CurrentUser.getCurrentUser().getImage()))));
        UserName.textProperty().bindBidirectional(CurrentUser.getCurrentUser().getName());
        UserPhone.textProperty().bindBidirectional(CurrentUser.getCurrentUser().getPhoneNumber());
    }

    public void changeChatbotStatus() {
        botOn = !botOn;
        message_edx_container.setDisable(botOn);

        if (botOn) {
            chatbot.setImage(new Image("boton.png"));
        } else {
            chatbot.setImage(new Image("botoff.png"));
        }

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

    private void SetupMessageProperties() {
        messageStyle = new MessageStyle();
        textFont.setItems(FXCollections.observableArrayList(javafx.scene.text.Font.getFamilies()));
        textFont.getSelectionModel().select(5);

        List<Integer> fontsizeArray = new ArrayList<>();
        for (int i = 10; i < 40; i++) {
            fontsizeArray.add(i + 2);
        }
        fontsize.setItems(FXCollections.observableArrayList(fontsizeArray));
        fontsize.getSelectionModel().select(5);

        // Detext change on text Font for messages
        textFont.getSelectionModel().selectedItemProperty().addListener((o, oldFont, newFont) -> {
            messageStyle.setFont(newFont.toString());
        });

        // Detext change on text color for messages
        text_color.valueProperty().addListener((o, oldColor, newColor) -> {
            messageStyle.setTextColor(toHexString(newColor));
        });

        // Detext change on text Size for messages
        fontsize.getSelectionModel().selectedItemProperty().addListener((o, oldFont, newFont) -> {
            messageStyle.setTextSize((int) newFont);
        });

        // Detext change on text background color for messages
        textBackground.valueProperty().addListener((o, oldColor, newColor) -> {
            messageStyle.setTextBackColor(toHexString(newColor));
        });

        // Detext change on text text weight for messages
        select.selectedToggleProperty().addListener((o, oldToggle, newToggle) -> {
            if (newToggle == regular) {
                // regular weight message
                messageStyle.setTextStyle(TextStyle.REGULAR);
            } else if (newToggle == bold) {
                // bold weight message
                messageStyle.setTextStyle(TextStyle.BOLD);
            } else if (newToggle == italic) {
                // italic weight message
                messageStyle.setTextStyle(TextStyle.ItALIC);
            } else {
                // select Regular
                regular.setSelected(true);
                messageStyle.setTextStyle(TextStyle.REGULAR);
            }
        });
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue())
                + format(value.getOpacity()))
                .toUpperCase();
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(new Date()).toString();
    }

    @FXML
    private void sendFiles() {
        WindowManger.getInstance().openSendingFilesWindow();
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
        else {
            this.setGraphic(null);
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

