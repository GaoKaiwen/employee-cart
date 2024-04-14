package config;

import java.util.Properties;

public class ConfigMailSmtp extends ConfigProperties {

    private Properties mailSmtpProperties;

    public ConfigMailSmtp() {
        mailSmtpProperties = new Properties();
        Properties originalProperties = getOriginalProperties();
        originalProperties.keySet().stream()
                .filter(keyObj -> ((String)keyObj).startsWith("mail.smtp"))
                .forEach(key -> mailSmtpProperties.put(key, originalProperties.get(key)));
    }

    public Properties getMailSmtpProperties() {
        return mailSmtpProperties;
    }

    public void setMailSmtpProperties(Properties mailSmtpProperties) {
        this.mailSmtpProperties = mailSmtpProperties;
    }
}
