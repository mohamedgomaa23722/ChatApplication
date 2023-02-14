package gov.iti.presentation.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.business.services.SendingFilesService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    Button chooseContactBtn;

    List<File> filesList;

    String reciever="01512345678";

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
        filesArea.getChildren().clear();
        //filesArea.getChildren().removeAll(filesArea);
        System.out.println(fileResults);
    }

    void display(File file) {
        String fileName=file.getName();
        Label fileNameLbl = new Label(fileName);
        filesArea.getChildren().add(fileNameLbl);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

        filesList=new ArrayList<>();

        //recieversList=new ArrayList<>();
    }
    
}
