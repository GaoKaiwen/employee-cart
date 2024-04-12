package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import utils.Decoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    private String emailTo;
    private String emailFromEncrypted;
    private String passwordEncrypted;
    private Properties props;
    private Session session;

    public EmailService(String emailTo) {
        this.emailTo = emailTo;
        setProperties();
        setEmailUserCredentials();
        setSession();
    }

    public void sendEmail() throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Decoder.decrypt(emailFromEncrypted)));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(emailTo));
        message.setSubject("Testando email 2");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        MimeBodyPart mimeBodyPartReport = new MimeBodyPart();
        mimeBodyPartReport.attachFile("src/main/java/utils/names.txt");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(mimeBodyPartReport);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Email sent!");
    }

    private void setEmailUserCredentials() {
        try(FileInputStream fis = new FileInputStream("src/test/resources/email.properties")) {
            Properties prop = new Properties();
            prop.load(fis);
            emailFromEncrypted = prop.getProperty("email");
            passwordEncrypted = prop.getProperty("password");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProperties() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    private void setSession() {
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                try {
                    return new PasswordAuthentication(Decoder.decrypt(emailFromEncrypted), Decoder.decrypt(passwordEncrypted));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
