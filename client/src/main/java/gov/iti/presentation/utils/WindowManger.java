package gov.iti.presentation.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gov.iti.presentation.controller.AddingContactController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class WindowManger {
    private static final WindowManger instance = new WindowManger();
    private Map<String, Parent> views = new HashMap<>();
    private VBox parentContainer;
    private VBox viewContainer;

    private WindowManger(){

    } 

    public static WindowManger getInstance() {
        return instance;
    }

    public void initializeView(VBox parentContainer, VBox viewContainer) {
        this.parentContainer = parentContainer;
        this.viewContainer = viewContainer;
    }

    public void openSettingWindow() {
        loadView("mainSettingPage");
    }

    public void openNotificationWindow() {
        loadView("notificationPage");
    }

    public void openAddContactWindow() {
        if(!AddingContactController.isAddingPageOpen()) {
            //close it
            AddingContactController.setIsAddingContactPageOpen(true);
        }
        loadView("AddContactPageFxml");
    }

    public void openCreatGroupWindow() {
        loadView("createGroupFxml"); 
    }

    public void loadView(String viewName){
        openWindow();
        if(!views.containsKey(viewName)){
            //create view 
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(viewName + ".fxml"));
                views.put(viewName, root);
                viewContainer.getChildren().add(views.get(viewName));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //get view from map and add it to container
            viewContainer.getChildren().add(views.get(viewName));
        }
    }
    public void closeWindow() {
        if(AddingContactController.isAddingPageOpen()) {
            //close it
            AddingContactController.setIsAddingContactPageOpen(false);
        }
        viewContainer.getChildren().removeAll(viewContainer.getChildren());
        parentContainer.setVisible(false);
    }

    public void openWindow() {
        parentContainer.setVisible(true);
    }
}
