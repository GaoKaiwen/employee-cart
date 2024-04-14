package config;

import utils.DecryptionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public abstract class ConfigProperties {

    private static Properties originalProperties;

    public ConfigProperties() {
        if(originalProperties == null) {
            originalProperties = loadProperties();
        }
        decryptEncryptedProperties();
    }

    private Properties loadProperties() {
        try(FileInputStream fis = new FileInputStream("src/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // FIXME: Throw custom exception
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME: Throw custom exception
        }
    }

    private void decryptEncryptedProperties() {
        originalProperties.entrySet().forEach(this::decryptEncryptedValue);
    }

    public static Properties getOriginalProperties() {
        return (Properties) originalProperties.clone();
    }

    private void decryptEncryptedValue(Map.Entry<Object, Object> propertyEntry) {
        String rawValue = (String)propertyEntry.getValue();
        if(isEncryptedValue(rawValue)) {
            String encryptedValue = removeEncWrapping(rawValue);
            String decryptedValue = decrypt(encryptedValue);
            propertyEntry.setValue(decryptedValue);
        }
    }

    private boolean isEncryptedValue(String rawValue) {
        return rawValue.startsWith("ENC(") && rawValue.endsWith(")");
    }

    private String removeEncWrapping(String originalValue) {
        return originalValue.substring(4, originalValue.length() - 1);
    }

    private String decrypt(String encryptedValue) {
        String decryptedValue;
        try {
            decryptedValue = DecryptionUtils.decrypt(encryptedValue);
        } catch (Exception e) { // FIXME: Catch custom exception
            throw new RuntimeException(e); // FIXME: Throw custom exception
        }
        return decryptedValue;
    }
}
