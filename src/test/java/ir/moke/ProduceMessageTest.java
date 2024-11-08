package ir.moke;

import ir.moke.mbp.EmailProducer;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProduceMessageTest {
    private static final Properties properties = new Properties();

    @BeforeAll
    public static void init() {
        // Load the properties file
        try (InputStream input = ConsumeMessageTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void sendTest() {
        EmailProducer emailProducer = new EmailProducer(properties);
        Session session = emailProducer.getSession();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setSubject("Hello");
            message.setContent("Hello dear", "text/plain");
            message.setRecipients(Message.RecipientType.TO, "bhswwihyxgocxtplxj@poplk.com");
            emailProducer.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
