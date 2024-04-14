package config;

public class ConfigEncryption extends ConfigProperties {

    private String key;

    public ConfigEncryption() {
        key = getOriginalProperties().getProperty("encryption.key");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
