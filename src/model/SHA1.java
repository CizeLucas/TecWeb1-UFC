package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
    
    public static String byteArrayToString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String toHash(String input) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byteArrayToString(md.digest(input.getBytes()));
    }

}
