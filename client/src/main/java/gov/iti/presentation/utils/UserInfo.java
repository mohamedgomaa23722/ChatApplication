package gov.iti.presentation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.spec.SecretKeySpec;

import gov.iti.presentation.dtos.CurrentUser;

public class UserInfo {

    private static UserInfo userInfo = new UserInfo();

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public String getSavedUserPhoneNumber() {
        try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("PhoneNumber");
        } catch (FileNotFoundException e) {
            
            System.out.println("no configuration file");
            return null;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("no configuration file");
            return null;
        }
    }

    public String getSavedUserPasswd() {
        try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String encryptedPassword = properties.getProperty("PassWord");
            String userPhone = CurrentUser.getCurrentUser().getPhoneNumber().getValue();
            String savedPhoneNumber = properties.getProperty("PhoneNumber");
            if (encryptedPassword.equals("") || encryptedPassword == null) {
                //throw new IllegalArgumentException("No such parameter present in config file");
                System.out.println("passwd not saved");
                return null;
            } else {
                if(!userPhone.equals(savedPhoneNumber)) {
                    return null;
                }
                byte[] salt = new String("12345678").getBytes();
                int iterationCount = 40000;
                int keyLength = 128;
                SecretKeySpec key = Configuration.createSecretKey("encryptedPassword".toCharArray(), salt, iterationCount, keyLength);
                String decryptedPassword = Configuration.decrypt(encryptedPassword, key);
                System.out.println("Encrypted password: " + encryptedPassword);
                System.out.println("Decrypted password: " + decryptedPassword);
                return decryptedPassword;
            }
        } catch (FileNotFoundException e) {
            
            //e.printStackTrace();
            System.out.println("no configuration file");
            return null;
        } catch (IOException e) {
            
            //e.printStackTrace();
            System.out.println("no configuration file");
            return null;
        } catch (NoSuchAlgorithmException e) {
          
            //e.printStackTrace();
            System.out.println("NoSuchAlgorithmException");
            return null;
        } catch (InvalidKeySpecException e) {
          
            //e.printStackTrace();
            System.out.println("InvalidKeySpecException");
            return null;
        } catch (GeneralSecurityException e) {
            
            //e.printStackTrace();
            System.out.println("GeneralSecurityException");
            return null;
        }
    }

    public boolean forgetUserPasswd() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        File configFile = new File("config.properties");
        FileWriter writer = null;
        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            String encryptedPassword = properties.getProperty("PassWord");
            if (encryptedPassword == null || encryptedPassword=="") {
                //throw new IllegalArgumentException("No such parameter present in config file");
                System.out.println("passwd aleady not saved");
            } else {
                writer=new FileWriter(configFile);
                properties.setProperty("PassWord", "");
                properties.store(writer, null);
                System.out.println("forget passwd");
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
                return false;
            }
            
    }
    
}
