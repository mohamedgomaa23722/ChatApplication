package gov.iti.presentation.controller;

import gov.iti.presentation.validation.UserValidator;
import gov.iti.business.services.RegisterService;
import gov.iti.business.services.SceneManager;
import gov.iti.model.User;

import java.io.File;

import java.net.MalformedURLException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

//import javax.swing.*;

public class SignUpController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker birthDayField;

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
    @FXML
    private ToggleGroup RB_Group;
    @FXML
    private ComboBox combo;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton male;
    String error = "-fx-border-color: red ;";
    String ideal = "-fx-border-color: #FF8780 ;";
    UserValidator userValidator;
    User registeredUser;

    public SignUpController() {
        userValidator = UserValidator.getUserValidator();
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        List<String> country_list = List.of(
                "Afghanistan",
                "Albania",
                "Algeria",
                "Andorra",
                "Angola",
                "Antigua and Barbuda",
                "Argentina",
                "Armenia",
                "Australia",
                "Austria",
                "Azerbaijan",
                "Bahamas",
                "Bahrain",
                "Bangladesh",
                "Barbados",
                "Belarus",
                "Belgium",
                "Belize",
                "Benin",
                "Bhutan",
                "Bolivia",
                "Bosnia and Herzegovina",
                "Botswana",
                "Brazil",
                "Brunei",
                "Bulgaria",
                "Burkina Faso",
                "Burundi",
                "Cambodia",
                "Cameroon",
                "Canada",
                "Cape Verde",
                "Central African Republic",
                "Chad",
                "Chile",
                "China",
                "Colombi",
                "Comoros",
                "Congo (Brazzaville)",
                "Congo",
                "Costa Rica",
                "Cote d'Ivoire",
                "Croatia",
                "Cuba",
                "Cyprus",
                "Czech Republic",
                "Denmark",
                "Djibouti",
                "Dominica",
                "Dominican Republic",
                "East Timor (Timor Timur)",
                "Ecuador",
                "Egypt",
                "El Salvador",
                "Equatorial Guinea",
                "Eritrea",
                "Estonia",
                "Ethiopia",
                "Fiji",
                "Finland",
                "France",
                "Gabon",
                "Gambia, The",
                "Georgia",
                "Germany",
                "Ghana",
                "Greece",
                "Grenada",
                "Guatemala",
                "Guinea",
                "Guinea-Bissau",
                "Guyana",
                "Haiti",
                "Honduras",
                "Hungary",
                "Iceland",
                "India",
                "Indonesia",
                "Iran",
                "Iraq",
                "Ireland",
                "Israel",
                "Italy",
                "Jamaica",
                "Japan",
                "Jordan",
                "Kazakhstan",
                "Kenya",
                "Kiribati",
                "Korea, North",
                "Korea, South",
                "Kuwait",
                "Kyrgyzstan",
                "Laos",
                "Latvia",
                "Lebanon",
                "Lesotho",
                "Liberia",
                "Libya",
                "Liechtenstein",
                "Lithuania",
                "Luxembourg",
                "Macedonia",
                "Madagascar",
                "Malawi",
                "Malaysia",
                "Maldives",
                "Mali",
                "Malta",
                "Marshall Islands",
                "Mauritania",
                "Mauritius",
                "Mexico",
                "Micronesia",
                "Moldova",
                "Monaco",
                "Mongolia",
                "Morocco",
                "Mozambique",
                "Myanmar",
                "Namibia",
                "Nauru",
                "Nepal",
                "Netherlands",
                "New Zealand",
                "Nicaragua",
                "Niger",
                "Nigeria",
                "Norway",
                "Oman",
                "Pakistan",
                "Palau",
                "Panama",
                "Papua New Guinea",
                "Paraguay",
                "Peru",
                "Philippines",
                "Poland",
                "Portugal",
                "Qatar",
                "Romania",
                "Russia",
                "Rwanda",
                "Saint Kitts and Nevis",
                "Saint Lucia",
                "Saint Vincent",
                "Samoa",
                "San Marino",
                "Sao Tome and Principe",
                "Saudi Arabia",
                "Senegal",
                "Serbia and Montenegro",
                "Seychelles",
                "Sierra Leone",
                "Singapore",
                "Slovakia",
                "Slovenia",
                "Solomon Islands",
                "Somalia",
                "South Africa",
                "Spain",
                "Sri Lanka",
                "Sudan",
                "Suriname",
                "Swaziland",
                "Sweden",
                "Switzerland",
                "Syria",
                "Taiwan",
                "Tajikistan",
                "Tanzania",
                "Thailand",
                "Togo",
                "Tonga",
                "Trinidad and Tobago",
                "Tunisia",
                "Turkey",
                "Turkmenistan",
                "Tuvalu",
                "Uganda",
                "Ukraine",
                "United Arab Emirates",
                "United Kingdom",
                "United States",
                "Uruguay",
                "Uzbekistan",
                "Vanuatu",
                "Vatican City",
                "Venezuela",
                "Vietnam",
                "Yemen",
                "Zambia",
                "Zimbabwe");
        combo.setItems(FXCollections.observableArrayList(country_list));
        styleFrame();
        actionHandlingIntialization();
    }

    @FXML
    public void handleImageContainer(MouseEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png"));

        fileChooser.setTitle("open");
        File file = fileChooser.showOpenDialog(null);
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

    public void actionHandlingIntialization() {
        signUp.setOnAction(e -> signUpAction());
        phoneNumber.setOnKeyTyped(e -> userValidator.validateUserNameNumber(phoneNumber));
        phoneNumber.setOnKeyReleased(e -> userValidator.validateUserNameNumber(phoneNumber));
        // confirmField.setOnMouseDragExited(e->validatePassword());
        /*
         * confirmField.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
         * {
         * if (!isNowFocused) {
         * userValidator.validatePassword();
         * }
         * });
         */
        combo.setOnMouseClicked(e -> combo.setStyle(ideal));
        nameField.setOnMouseClicked(e -> nameField.setStyle(ideal));
        passwordField.setOnMouseClicked(e -> passwordField.setStyle(ideal));
        confirmField.setOnMouseClicked(e -> confirmField.setStyle(ideal));
        birthDayField.setOnMouseClicked(e -> birthDayField.setStyle(ideal));
        phoneNumber.setOnMouseClicked(e -> phoneNumber.setStyle(ideal));
        emailField.setOnMouseClicked(e -> emailField.setStyle(ideal));
    }

    public boolean validatePassword() {
        if (userValidator.isMatchedPassword(passwordField.getText(),confirmField.getText()) && userValidator.validateUserPassWd(passwordField.getText())) {
            return true;
        }
        return false;
    }

    public boolean validateAllFields() {
        boolean isValid = true;
        if (!userValidator.validateEmail(emailField.getText())) {
            isValid = false;
            emailField.setStyle(error);
        }
        if (!validatePassword()) {
            isValid = false;
            passwordField.setStyle(error);
        }
        if (!userValidator.validateName(nameField.getText())) {
            isValid = false;
            nameField.setStyle(error);
        }
        if (!isValid)
           // JOptionPane.showMessageDialog(null,
                //    "your input formate wrong\n password must contain atleast 8 charchter with  \natleast one special charcter and atleast one uper-case litter\n Name must be english litters only");
        if (phoneNumber.getText().trim().isEmpty()) {
            isValid = false;
            phoneNumber.setStyle(error);

        }
        if (passwordField.getText().trim().isEmpty()) {
            isValid = false;
            passwordField.setStyle(error);
        }
        if (confirmField.getText().trim().isEmpty()) {
            isValid = false;
            confirmField.setStyle(error);
        }
        if (nameField.getText().trim().isEmpty()) {
            isValid = false;
            nameField.setStyle(error);
        }
        if (emailField.getText().trim().isEmpty()) {
            isValid = false;
            emailField.setStyle(error);
        }
        if (combo.getSelectionModel().isEmpty()) {
            isValid = false;
            // System.out.println("combo");
            combo.setStyle(error);
        }
        if (RB_Group.getSelectedToggle() == null) {
            isValid = false;
            // System.out.println("radio");

        }
        return isValid;
    }

    private void signUpAction() {
        if(validateAllFields()) {
            Image image= imgContainer.getImage();
            int w=(int)image.getWidth();
            int h=(int)image.getHeight();
            byte[] buf = new byte[w*h*4];
            String gender;
            image.getPixelReader().getPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), buf, 0, w * 4); 
            if(female.isSelected()){
                gender="female";
            }
            else{
                gender="male";
            }
            registeredUser=new User(phoneNumber.getText().trim(), nameField.getText().trim(),25
            ,gender, buf,emailField.getText().trim()
            , textArea.getText().trim(), combo.getValue().toString(), 0, 0);
            if(RegisterService.getRegisterService().registerNewUser(registeredUser,passwordField.getText())) {
                //go to chat 
            } else {
                // stay
            }
            
        }
        //System.out.println(birthDayField.getEditor().getText());
        
    }
    private int calculateAge(String strBirthDate){
        LocalDate birthDate = LocalDate.parse(strBirthDate.replaceAll("/", "-")); 
        LocalDate currentDate = LocalDate.now();
        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        LocalDate dt = dtf.parseLocalDate(yourinput);*/
       return Integer.parseInt(Period.between(birthDate, currentDate).toString()); 
       
    }
    
    @FXML
    private void handelBack(){
        SceneManager.getSceneManagerInstance().switchToPhoneLoginScreen();
    }

}
