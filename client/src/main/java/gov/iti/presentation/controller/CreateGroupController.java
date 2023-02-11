/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gov.iti.presentation.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.ContactsService;
import gov.iti.business.services.GroupService;
import gov.iti.model.Group;
import gov.iti.model.UserContact;
import gov.iti.presentation.utils.UserValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CreateGroupController implements Initializable {

    Group group;
    ObservableList<String> selectedItems;
    @FXML
    VBox listOfContactsView;
    @FXML
    Button createGroupButton;
    @FXML
    TextField groupNameField;
    @FXML
    Label error;
    public CreateGroupController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("jksdnvjnkdsnv");
        List<UserContact> userContacts = ContactsService.getcontactsService().getContacts("01286338362");
        List<String> userContactsMerged = new ArrayList<String>();
        for (var contact : userContacts) {
            userContactsMerged.add(contact.getPhoneNumber() + "   " + contact.getName());
        }

        ObservableList<String> list = FXCollections.<String>observableArrayList(userContactsMerged);
        ListView<String> userContactsViewList = new ListView<String>(list);
        /*
         * userContactsViewList.getSelectionModel().selectedItemProperty().addListener(
         * new ChangeListener<String>() {
         * 
         * @Override
         * public void changed(ObservableValue<? extends String> observable, String
         * oldValue, String newValue) {
         * System.out.print(
         * userContactsViewList.getSelectionModel().getSelectedItems());
         * //userContactsViewList.setStyle("-fx-control-inner-background: blue;");
         * 
         * }
         * });
         */
        userContactsViewList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userContactsViewList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedItems = userContactsViewList.getSelectionModel().getSelectedItems();
                error.setVisible(false);
            }
        });
        userContactsViewList.setOrientation(Orientation.VERTICAL);
        listOfContactsView.getChildren().add(userContactsViewList);
    }

    @FXML
    public void creatGroupHandler() {
        String groupName = groupNameField.getText().trim();
        if (UserValidator.getUserValidator().validateGroupName(groupName)) {
            if (selectedItems!=null && selectedItems.size() > 1) {
                int GroupNum=GroupService.getGroupService().creatGroupService(groupName);
                for (var selected : selectedItems) {
                    GroupService.getGroupService().addGroupMemberService(GroupNum, selected.replaceAll("  ", " ").split(" ")[0].trim());
                }
            }
            else{
                error.setText("Group name have at least 2 members");
                error.setVisible(true);
            }
        }
        else{
            error.setText("Group name must contain from 2 to 25 charcter");
            error.setVisible(true);
        }

    }
    @FXML
    private void handelError(){
        error.setVisible(false);
    }
}
