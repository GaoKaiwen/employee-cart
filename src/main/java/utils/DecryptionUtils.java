package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Properties;

public class DecryptionUtils {

    public static String decrypt(String encryptedValue) throws Exception { // FIXME: Throw custom exception
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, generateKey(getKeyValue()));

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue.getBytes());

        byte[] decryptedValueBytes = cipher.doFinal(decodedBytes);

        System.out.println("Decrypted Value :: " + new String(decryptedValueBytes));
        return new String(decryptedValueBytes);
    }

    private static Key generateKey(String keyValue) {
        return new SecretKeySpec(keyValue.getBytes(), "AES");
    }

    // FIXME: Fix circular dependency with ConfigEncryption to use ConfigEncryption, instead
    private static String getKeyValue() {
        try(FileInputStream fis = new FileInputStream("src/resources/application.properties")) {
            Properties props = new Properties();
            props.load(fis);
            return props.getProperty("encryption.key");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}