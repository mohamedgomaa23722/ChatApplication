/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gov.iti.presentation.controller;

import java.net.URL;

import java.util.ResourceBundle;

import gov.iti.business.services.GroupService;
import gov.iti.model.Group;
import gov.iti.model.User;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presentation.utils.UserValidator;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateGroupController implements Initializable {

    Group group;
    ObservableList<User> selectedItems;
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
        // System.out.println("jksdnvjnkdsnv");
        ListView<User> userContactsViewList = new ListView<User>(CurrentUser.getCurrentUser().getContacts());

        userContactsViewList.setCellFactory(p -> new itemCell());
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
            if (selectedItems != null && selectedItems.size() > 1) {
                int GroupNum = GroupService.getGroupService().creatGroupService(groupName);
                for (var selected : selectedItems) {
                    GroupService.getGroupService().addGroupMemberService(GroupNum, selected.getPhoneNumber());
                }
            } else {
                error.setText("Group name have at least 2 members");
                error.setVisible(true);
            }
        } else {
            error.setText("Group name must contain from 2 to 25 charcter");
            error.setVisible(true);
        }

    }

    @FXML
    private void handelError() {
        error.setVisible(false);
    }
}

class itemCell extends ListCell<User> {
    @Override
    public void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            VBox vBox = new VBox();
            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #F84D43;");

            Label phoneLabel = new Label(item.getPhoneNumber());
            phoneLabel.setFont(new Font("Arial", 12));
            phoneLabel.setTextFill(Color.web("gray"));

            vBox.getChildren().addAll(nameLabel, phoneLabel);
            this.setGraphic(vBox);
        } else {
            this.setGraphic(null);
        }
    }
}
