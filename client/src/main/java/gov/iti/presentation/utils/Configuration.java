package gov.iti.presentation.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Properties;

public class Configuration {

    public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String dataToEncrypt, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }

    public static void createConfFile2(String phone,String passwd) {

        Properties configProp = new Properties();
        OutputStream configFile = null;
        try {
            configFile = new FileOutputStream("config.properties");

            configProp.setProperty("PhoneNumber", phone);
            configProp.setProperty("PassWord", encrypt(passwd,getKey()));
            configProp.store(configFile, null);
            System.out.println("saved createConfFile");
            if(configFile !=null) {
                System.out.println("file null");
                configFile.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can't create createConfFile");
        }
    }

    static SecretKeySpec getKey() {
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        try {
            SecretKeySpec key = Configuration.createSecretKey("encryptedPassword".toCharArray(), salt, iterationCount, keyLength);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("can't create key");
            return null;
        }
    }

    public static void createConfFile(String phone,String passwd) {

        Properties configProp = new Properties();
        File configFile = new File("config.properties");
        FileWriter writer = null;
        try {
            //configFile = new FileOutputStream("config.properties");
            writer=new FileWriter(configFile);
            configProp.setProperty("PhoneNumber", phone);
            configProp.setProperty("PassWord", encrypt(passwd,getKey()));
            configProp.store(writer, null);
            System.out.println("saved createConfFile");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can't create createConfFile");
        }
    }

    
}
