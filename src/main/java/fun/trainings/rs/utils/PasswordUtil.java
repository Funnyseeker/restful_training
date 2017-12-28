package fun.trainings.rs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Утилитный класс для хэширования паролей.
 */
public class PasswordUtil {

    /**
     * Алгоритм для хэширования.
     */
    private static String ALGORITHM = "SHA-512";

    /**
     * Создает хэш для заданного пароля, используя определенный алгоритм {@link PasswordUtil#ALGORITHM}
     *
     * @param password пароль введеный пользователем
     *
     * @return хэш указанного пароля.
     */
    public static String getHash(String password) {
        try {
            StringBuffer code = new StringBuffer(); //the hash code
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

            byte bytes[] = password.getBytes();
            byte digest[] = messageDigest.digest(bytes); //create code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x100 + (digest[i] & 0x00FF)).substring(1));
            }

            String hash = code.toString();

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * Сверяет указанный пароль с уже существующим.
     *
     * @param password введенный пароль
     * @param hash     хэш пароля пользователя
     *
     * @return true, если введенный пароль является верным, иначе - false.
     */
    public static boolean checkPassword(String password, String hash) {
        try {
            StringBuffer code = new StringBuffer(); //the hash code
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

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
