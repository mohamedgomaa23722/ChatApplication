package gov.iti;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Utilities {

    static MessageDigest mesDigest;

    public static String Hash(String string) {

        try {
            mesDigest = MessageDigest.getInstance("MD5");

            byte[] hashedMessage = mesDigest.digest(string.getBytes());

            BigInteger hashed = new BigInteger(1, hashedMessage);

            System.out.println(hashed.toString(16));

            return hashed.toString(16);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("error while hashing ");
            return string;
        }
    }

    public static boolean validateUserPhoneNumber(String phoneNumber) {

        String phoneRegex = "[0-9]+";

        if (phoneNumber.length() != 11) {
            return false;
        }
        boolean isPhoneValid = phoneNumber.matches(phoneRegex);
        if (!isPhoneValid) {
            return false;
        }
        return true;
    }

    public static boolean validateUserPassWd(String passwd) {

        String passwdRegex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{6,20})";

        boolean isPasswdValid = passwd.matches(passwdRegex);
        if (!isPasswdValid) {
            return false;
        }
        return true;

    }

    public static boolean validateUserNameNumber(TextField phoneNumber) {
        boolean flag = true;
        String strNum = new String(phoneNumber.getText());
        strNum = strNum.trim();
        strNum.replaceAll("//s+", "");
        System.out.println(strNum);

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

    public static boolean isMatchedPassword(String password, String confirm) {
        if (!password.equals(confirm)) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {

        String emailRegex = "^([a-zA-Z]+[0-9]*)+@[a-zA-Z]+\\.([a-zA-Z]{2,6})$";

        boolean isValidEmail = email.matches(emailRegex);
        if (!isValidEmail) {
            return false;
        }
        return true;
    }

    public static boolean validateName(String name) {
        String NameRegx = "^[A-Za-z]\\w{2,29}$";
        boolean isValidName = name.matches(NameRegx);
        if (!isValidName) {
            return false;
        }
        return true;
    }

    public static List<String> country_list = List.of(
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
            "Ecuador",
            "Egypt");

    public static Image fromBytesToImage(byte[] bytesArr) {
        Image image = null;
        if (bytesArr != null && bytesArr.length > 0) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytesArr);
            image = new Image(bis);
        }
        return image;
    }
}
