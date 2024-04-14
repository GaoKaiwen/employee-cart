package config;

public class ConfigEmail extends ConfigProperties {

    private String email;
    private String password;
    private String emailTo;

    public ConfigEmail() {
        this.email = getOriginalProperties().getProperty("email.sender.address");
        this.password = getOriginalProperties().getProperty("email.sender.password");
        this.emailTo = getOriginalProperties().getProperty("email.receiver.address");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
}
