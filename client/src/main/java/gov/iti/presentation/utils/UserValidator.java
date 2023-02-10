package gov.iti.presentation.utils;

import javafx.scene.control.TextField;

public class UserValidator {

    private static UserValidator userValidator = new UserValidator();

    private UserValidator(){

    }
    public static UserValidator getUserValidator() {
        return userValidator;
    }

    public boolean validateUserPhoneNumber(String phoneNumber) {

        String phoneRegex = "[0-9]+";

        if(phoneNumber.length()!=11) {
            return false;
        }
        boolean isPhoneValid =phoneNumber.matches(phoneRegex);
        if(!isPhoneValid) {
            return false;
        } 
        return true;
    }

    public boolean validateUserPassWd(String passwd) {

        String passwdRegex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{6,20})";

        boolean isPasswdValid =passwd.matches(passwdRegex);
        if(!isPasswdValid) {
            return false;
        } 
        return true;

    }
    
    public boolean validateUserNameNumber(TextField phoneNumber) {
        boolean flag = true;
        String strNum = new String(phoneNumber.getText());
        strNum = strNum.trim();
        strNum.replaceAll("//s+", "");
        System.out.println(strNum);
        /* 
        if (strNum == null) {
            flag = false;
        }
        */
        if (strNum.length() > 0) {
            try {
                System.out.println(strNum);
                Integer.parseInt(strNum);
            } catch (NumberFormatException nfe) {
                flag = false;
            }
            if (!flag) {
                phoneNumber.clear();
                phoneNumber.setText(strNum.substring(0, strNum.length() - 1));
                phoneNumber.positionCaret(strNum.length());
            }
            if (strNum.length() > 11) {
                phoneNumber.clear();
                phoneNumber.setText(strNum.substring(0, 11));
                phoneNumber.positionCaret(strNum.length());
            }
        }
        return flag;

    }

    public boolean isMatchedPassword(String password,String confirm ) {
        if (!password.equals(confirm)) {
            return false;
        }
        return true;
    }
    
    public boolean validateEmail(String email) {

        String emailRegex = "^([a-zA-Z]+[0-9]*)+@[a-zA-Z]+\\.([a-zA-Z]{2,6})$";

        boolean isValidEmail = email.matches(emailRegex);
        if (!isValidEmail) {
            return false;
        }
        return true;

    }
    public boolean validateName(String name) {
        String NameRegx = "^[A-Za-z]\\w{2,29}$";
        boolean isValidName = name.matches(NameRegx);
        if (!isValidName) {
            return false;
        }
        return true;
    }

   
}
