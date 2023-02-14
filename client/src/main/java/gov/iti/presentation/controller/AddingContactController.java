package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.AddingContactService;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.UserValidator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddingContactController implements Initializable {

    @FXML
    TextField addNewContactTextField;

    @FXML
    ImageView plus;

    @FXML
    VBox vbox;

    @FXML
    Button invitationBtn;

    @FXML
    Label phoneError;

    UserValidator validator;

    List<String> invitedContacts; //

    List<User> userContacts;

    List<String> userContactPhones;

    List<Integer> invitationStatus;

    List<Label> statusLabels;

    boolean isFirstContact = true;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 10px; -fx-font-family: Arial; -fx-text-align: center;";
    String contactStyle = "-fx-border-style: solid; -fx-border-color: blue; -fx-border-radius: 25px; -fx-font-size: 20px; -fx-padding: 0 2 2 2; -fx-marign: 10 10 10 10;";
    String contactErrorStyle = "-fx-border-style: solid; -fx-border-color: red; -fx-border-radius: 25px; -fx-font-size: 20px; -fx-padding: 0 2 2 2; -fx-marign: 0 0 10 0;";

    static BooleanProperty isAddingContactPageOpen = new SimpleBooleanProperty(true);

    @FXML
    public void addContact() {

        if (!addNewContactTextField.getText().isBlank()) {
            phoneError.setText("");
            String phoneNumber = addNewContactTextField.getText().trim();
            if (validator.validateUserPhoneNumber(phoneNumber)) {
                if (isFirstContact) {
                    isFirstContact = false;
                    userContacts = CurrentUser.getCurrentUser().getContacts();
                    for (User contact : userContacts) {
                        userContactPhones.add(contact.getPhoneNumber());
                    }
                }

                if (CurrentUser.getCurrentUser().getPhoneNumber().getValue().equals(phoneNumber)) {
                    showError("can't add your number", phoneError);
                } else if (userContactPhones.contains(phoneNumber)) {
                    showError("Already Friends ", phoneError);
                } else {
                    displayContact(phoneNumber);
                }
            } else {
                showError("Enter valid number", phoneError);
            }
        }
    }

    @FXML
    public void showError(String message, Label vBox) {
        vBox.setVisible(true);
        vBox.setText(message);
        vBox.setStyle(loggedError);
    }

    @FXML
    public void sendInvitation() {
        isFirstContact = true;
        System.out.println(invitedContacts);
        invitationStatus = AddingContactService.getAddingNewContactService()
                .addNewContact(CurrentUser.getCurrentUser().getPhoneNumber().get(), invitedContacts);
        System.out.println(invitationStatus);
        showInvitationStatus();
    }

    void showInvitationStatus() {
        System.out.println("showInvitationStatus");
        for (int i = 0; i < invitationStatus.size(); i++) {
            if (invitationStatus.get(i) == 0) {
                System.out.println("Not Register");
                statusLabels.get(i).setText("Not Register");
                statusLabels.get(i).setStyle("-fx-text-fill: red;");
            } else if (invitationStatus.get(i) == 1) {
                System.out.println("Invite Successfully");
                statusLabels.get(i).setText("Invite Successfully");
                statusLabels.get(i).setStyle("-fx-text-fill: green;");
            } else {
                System.out.println("Already Invited");
                statusLabels.get(i).setText("Already Invited");
                statusLabels.get(i).setStyle("-fx-text-fill: blue;");
            }
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        validator = UserValidator.getUserValidator();
        invitedContacts = new ArrayList<>();
        statusLabels = new ArrayList<>();
        invitationStatus = new ArrayList<>();
        userContactPhones = new ArrayList<>();

        isAddingContactPageOpen.addListener((o, oldVal, newVal) -> {
            System.out.println("change");
            if (newVal.toString() == "false") {
                System.out.println("close adding page");
                vbox.getChildren().removeAll(vbox.getChildren());
                invitedContacts.clear();
                statusLabels.clear();
            }
        });

    }

    void displayContact(String phoneNumber) {
        addNewContactTextField.clear();
        Label phoneLbl = new Label(phoneNumber);
        Label statusLbl = new Label("Waiting");
        statusLbl.setStyle("-fx-text-fill: #e4b620;");

        HBox hbox = new HBox(phoneLbl);
        statusLbl.setLayoutX(10);
        Insets inset2 = new Insets(0, 10, 0, 20);

        hbox.setMargin(statusLbl, inset2);
        hbox.getChildren().add(statusLbl);

        hbox.setAlignment(Pos.CENTER_LEFT);

        hbox.setStyle(contactStyle);

        hbox.prefWidth(200);
        Insets inset = new Insets(0, 0, 10, 0);
        vbox.setMargin(hbox, inset);
        vbox.getChildren().add(hbox);
        statusLabels.add(statusLbl);
        invitedContacts.add(phoneNumber);
    }

    public static void setIsAddingContactPageOpen(boolean isOpen) {
        isAddingContactPageOpen.set(isOpen);
    }

    public static boolean isAddingPageOpen() {
        return isAddingContactPageOpen.get();
    }
}
// wating
// response
// 0 --> you can't add your number
// 1 --> you can't add existing contact
// 2 --> you can't add unregister contact
// 3 --> added successfully