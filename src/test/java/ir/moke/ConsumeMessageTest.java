package ir.moke;

import ir.moke.listener.GmailListener;
import ir.moke.mbp.EmailConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConsumeMessageTest {
    private static Properties properties;

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
    public void listenTest() {
        EmailConsumer emailConsumer = new EmailConsumer(properties);
        emailConsumer.listen(GmailListener.class);
    }
}
