package service;

import config.ConfigEmail;
import config.ConfigMailSmtp;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

    private final ConfigEmail configEmail;
    private final ConfigMailSmtp configMailSmtp;
    private Session session;

    public EmailService(String emailTo, ConfigEmail configEmail, ConfigMailSmtp configMailSmtp) {
        this.configEmail = configEmail;
        this.configMailSmtp = configMailSmtp;
        setSession();
    }

    public void sendEmail() throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress());
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(configEmail.getEmailTo()));
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

    private void setSession() {
        session = Session.getInstance(configMailSmtp.getMailSmtpProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                try {
                    return new PasswordAuthentication(configEmail.getEmail(), configEmail.getPassword());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
