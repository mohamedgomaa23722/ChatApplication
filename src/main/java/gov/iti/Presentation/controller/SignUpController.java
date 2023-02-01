package gov.iti.jets.register1;

import java.io.File;

import java.net.MalformedURLException;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class RegisterController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField birthDayField;

    @FXML
    private TextField textArea;

    @FXML
    private ImageView phoneImg;

    @FXML
    private TextField phoneNumber;
    @FXML
    private ImageView imgContainer;
    @FXML
    private Circle circa;
    @FXML
    private Button signUp;
   
    Stage stage;

    public RegisterController(Stage primaryStage) {
        stage = primaryStage;
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        styleFrame();
        actionHandlingIntialization();
    }

    @FXML
    public void handleImageContainer(MouseEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png"));

        fileChooser.setTitle("open");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                URL url = file.toURI().toURL();

                circa.setFill(new ImagePattern(new Image(url.toString())));
                imgContainer.setVisible(false);
                circa.setOnMouseClicked(e1 -> handleImageContainer(e1));

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void styleFrame() {
        imgContainer.setImage(new Image(getClass().getResource("/image.png").toString()));
        imgContainer.setOnMouseClicked(e -> handleImageContainer(e));
    
    }

    public boolean validateUserNameNumber() {
        boolean flag=true;
        String strNum = new String(phoneNumber.getText());
        strNum = strNum.trim();
        strNum.replaceAll("//s+", "");
        System.out.println(strNum);
        if (strNum == null) {
            flag=false; 
        }
        if(strNum.length()>0){
       try {
             System.out.println(strNum);
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            flag= false;
        }
        if(!flag){
            phoneNumber.clear();
            phoneNumber.setText(strNum.substring(0, strNum.length() -1));
            phoneNumber.positionCaret(strNum.length());
        }
        if(strNum.length()>11){
            phoneNumber.clear();
            phoneNumber.setText(strNum.substring(0, 11));
            phoneNumber.positionCaret(strNum.length());
        }
        }
        return flag;
        
    }

    public void actionHandlingIntialization() {
        signUp.setOnAction(e -> signUpAction());
        phoneNumber.setOnKeyTyped(e->validateUserNameNumber());
        phoneNumber.setOnKeyReleased(e->validateUserNameNumber());
        //confirmField.setOnMouseDragExited(e->validatePassword());
        confirmField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
    if (! isNowFocused) {
        // text field has lost focus...
        validatePassword();
    }
});
    }
    private void validatePassword(){
      if(!passwordField.getText().equals(confirmField.getText())) {
          passwordField.clear();
          confirmField.clear();
      } 
    }
    
    private void signUpAction() {
        validatePassword();
    }
    
//    @FXML
//    protected void handleEnteringphoneField() {
//
//        phoneNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    phoneImg.setVisible(false);
//
//                }
//                else{
//                    phoneImg.setVisible(true);
//
//                }
//            }
//
//        });
//
//        nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    personImg.setVisible(false);
//
//                }
//                else{
//                    personImg.setVisible(true);
//
//                }
//            }
//        });
//        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    lockImg.setVisible(false);
//
//                }
//                else{
//                    lockImg.setVisible(true);
//
//                }
//            }
//        });
//        confirmField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    lockImg2.setVisible(false);
//
//                }
//                else{
//                    lockImg2.setVisible(true);
//
//                }
//            }
//        });
//
//        birthDayField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    calenderImg.setVisible(false);
//
//                }
//                else{
//                    calenderImg.setVisible(true);
//
//                }
//            }
//        });
//        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    mailImg.setVisible(false);
//
//                }
//                else{
//                    mailImg.setVisible(true);
//
//                }
//            }
//        });
//        combo.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    flagImg.setVisible(false);
//
//                }
//                else{
//                    flagImg.setVisible(true);
//
//                }
//            }
//        });
//
//    }
}
