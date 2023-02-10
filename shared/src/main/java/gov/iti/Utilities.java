package gov.iti;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
            "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
            "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",
            "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic",
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
}
