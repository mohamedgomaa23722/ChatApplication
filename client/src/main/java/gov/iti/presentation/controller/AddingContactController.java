package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.AddingContactService;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.UserValidator;
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

    List <String> contact;

    List <Integer> invitationStatus;

    List <Label> statusLabels;

    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    String loggedError = "-fx-text-fill: black; -fx-font-size: 10px; -fx-font-family: Arial; -fx-text-align: center;";
    String contactStyle="-fx-border-style: solid; -fx-border-color: blue; -fx-border-radius: 25px; -fx-font-size: 20px; -fx-padding: 0 2 2 2; -fx-marign: 10 10 10 10;"; 
    String contactErrorStyle="-fx-border-style: solid; -fx-border-color: red; -fx-border-radius: 25px; -fx-font-size: 20px; -fx-padding: 0 2 2 2; -fx-marign: 0 0 10 0;"; 

    public void addContact() {
        if(!addNewContactTextField.getText().isBlank()) {
            phoneError.setText("");
            String phoneNumber=addNewContactTextField.getText();
            if(validator.validateUserPhoneNumber(phoneNumber)) {
                //clear text field
                // add to list
            // handle friend case 
            System.out.println(CurrentUser.getCurrentUser().getPhoneNumber().getValue());
            System.out.println(phoneNumber);
            if(CurrentUser.getCurrentUser().getPhoneNumber().getValue().equals(phoneNumber)) {
                showError("can't add your number", phoneError);
            } else {
                addNewContactTextField.clear();
                Label lbl = new Label(phoneNumber);
                Label lbl2 = new Label("Waiting");
                //lbl2.setAlignment(Pos.CENTER_RIGHT);
                
                HBox hbox = new HBox(lbl);
                lbl2.setLayoutX(10);
                Insets inset2= new Insets(0, 10, 0, 0);
                
                hbox.setMargin(lbl2, inset2);
                hbox.getChildren().add(lbl2);
                
                hbox.setAlignment(Pos.CENTER_LEFT);

                
                //HBox hBox2 = new HBox(lbl2);
                //hBox2.setAlignment(Pos.BOTTOM_RIGHT);
                /* 
                hbox.prefHeight(150);
                hbox.prefWidth(394);
                hbox.setLayoutY(50);
                hbox.setLayoutX(60);
                lbl.setLayoutY(50);
                */
                hbox.setStyle(contactStyle);
                //hbox.setLayoutY(50);
                hbox.prefWidth(200);
                Insets inset= new Insets(0, 0, 10, 0);
                vbox.setMargin(hbox, inset);
                vbox.getChildren().add(hbox);
                //vbox.getChildren().add(hBox2);
                statusLabels.add(lbl2);
                contact.add(phoneNumber);
                }
            } else {
                //
                showError("Enter valid number", phoneError);
            }

        }
        
    }

    @FXML
    public void showError(String message, Label vBox){
        vBox.setVisible(true);
        vBox.setText(message);
        vBox.setStyle(loggedError);
    }

    @FXML
    public void sendInvitation() {
        System.out.println(contact);
        invitationStatus=AddingContactService.getAddingNewContactService().addNewContact(CurrentUser.getCurrentUser().getPhoneNumber().get()
        , contact);
        showInvitationStatus();
    }

    void showInvitationStatus() {
        for(int i=0;i<invitationStatus.size();i++) {
            if(invitationStatus.get(i)==0) {
                statusLabels.get(i).setText("Not Register");
            } else if(invitationStatus.get(i)==2) {
                statusLabels.get(i).setText("Already Friend");
            } else {
                statusLabels.get(i).setText("Invite Successfully");
            }
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        validator=UserValidator.getUserValidator();
        contact=new ArrayList<>();
        statusLabels = new ArrayList<>();
        invitationStatus = new ArrayList<>();
    }
    
}
// wating
// response 
// 0 --> you can't add your number
// 1 --> you can't add existing contact
// 2 --> you can't add unregister contact
// 3 --> added successfully