package gov.iti.presentation.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.SendingFilesService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class SendingFilesController implements Initializable {

    @FXML
    VBox filesArea;

    @FXML
    Button chooseFileBtn;

    @FXML
    Button sendFileBtn;

    List<File> filesList;

    //TODO:change reciever number 
    String reciever="01512345678";

    String labelStyle="-fx-border-style: solid; -fx-border-color: blue; -fx-border-radius: 5px; -fx-font-size: 15px; -fx-padding: 2 2 2 2; -fx-marign: 10 10 10 10;"; 

    //File[] filesList=null;

    //List<String> recieversList;

    @FXML
    public void chooseFiles() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose file");
        //filesList=fileChooser.showOpenMultipleDialog(null);
        //fileChooser.setMultiSelectionEnabled(true);
        
        //fileChooser.showOpenDialog(null);
        //filesList = fileChooser.getSelectedFiles();
        
        
        //filesList=fileChooser.showOpenDialog(fileChooser)
        File file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
            filesList.add(file);
            display(file);
        }
         
        
    }

    @FXML
    public void sendFilesToServer() {
        System.out.println(filesList);
        //recieversList.clear();
        //recieversList.add("01512345678");
        //go to the contact list
        // call sending service
        List<Boolean> fileResults=SendingFilesService.getSendingFilesService().sendFiles(filesList,reciever);
        filesList.clear();
        //filesArea.getChildren().clear();
        filesArea.getChildren().removeAll(filesArea.getChildren());
        System.out.println(fileResults);
    }

    void display(File file) {
        String fileName=file.getName();
        Label fileNameLbl = new Label(fileName);
        Insets inset2= new Insets(0, 10, 0, 20);
        fileNameLbl.setStyle(labelStyle);
        filesArea.setMargin(fileNameLbl, inset2);
        filesArea.getChildren().add(fileNameLbl);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

        filesList=new ArrayList<>();

        //recieversList=new ArrayList<>();
    }
    
}
