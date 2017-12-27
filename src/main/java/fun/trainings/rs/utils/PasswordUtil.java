package fun.trainings.rs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String getHash(String password) {
        try {
            StringBuffer code = new StringBuffer(); //the hash code
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte bytes[] = password.getBytes();
            byte digest[] = messageDigest.digest(bytes); //create code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }

            String md5 = code.toString();

            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static boolean checkPassword(String password, String hash) {
        try {
            StringBuffer code = new StringBuffer(); //the hash code
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte bytes[] = password.getBytes();
            byte digest[] = messageDigest.digest(bytes); //create code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }

            String md5 = code.toString();

            if (md5.equals(hash)) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
}
