package gov.iti;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities{

    static MessageDigest mesDigest;

    public static String Hash(String string){
        
        try{
            mesDigest = MessageDigest.getInstance("MD5");
        
            byte[] hashedMessage = mesDigest.digest(string.getBytes());
        
            BigInteger hashed = new BigInteger(1,hashedMessage);
        
            System.out.println(hashed.toString(16));
            
            return hashed.toString(16);
        
            }catch (NoSuchAlgorithmException e) {
                System.out.println("error while hashing ");
                return string;
            }
    }
}


