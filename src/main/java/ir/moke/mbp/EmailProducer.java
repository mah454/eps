package ir.moke.mbp;

import jakarta.mail.*;
import jakarta.mail.event.MessageCountListener;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailProducer {

    private final Session session;
    private final Properties properties;

    public EmailProducer(Properties properties) {
        this.properties = properties;
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUsername(), getPassword());
            }
        });
    }

    private String getPassword() {
        return properties.getProperty("mail.password");
    }

    private String getUsername() {
        return properties.getProperty("mail.username");
    }

    public Session getSession() {
        return session;
    }

    public void send(MimeMessage message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
