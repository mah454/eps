# Email Publish Subscribe

use mail server as topic broker server .    
**IMAP**: used for subscribe messages   
**SMTP**: used for publish messages

### Usage:

Publish message:

```java
import ir.moke.mbp.Configuration;

import java.util.Properties;

public void publishMessage() {
    Properties properties = new Configuration.Builder()
            .setAuth(true)
            .setUsername("USERNAME")
            .setUsername("PASSWORD")
            .setPort(587)
            .setHost("smtp.gmail.com")
            .buildImap();
    EmailProducer emailProducer = new EmailProducer(properties);
    Session session = emailProducer.getSession();

    try {
        MimeMessage message = new MimeMessage(session);
        message.setSubject("subject");
        message.setContent("message content", "text/plain");
        message.setRecipients(Message.RecipientType.TO, "target@domain.com");
        emailProducer.send(message);
    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
```
---
Subscribe messages :    
Implement Listener :

```java
public class GmailListener implements MessageCountListener {

    @Override
    public void messagesAdded(MessageCountEvent messageCountEvent) {
        for (Message message : messageCountEvent.getMessages()) {
            // process message
        }
    }

    @Override
    public void messagesRemoved(MessageCountEvent messageCountEvent) {
        for (Message message : messageCountEvent.getMessages()) {
            // process message
        }
    }
}
```

Now use that :

```java
import ir.moke.mbp.Configuration;

import java.util.Properties;

public void publishMessage() {
    Properties properties = new Configuration.Builder()
            .setAuth(true)
            .setUsername("USERNAME")
            .setUsername("PASSWORD")
            .setPort(993)
            .setHost("imap.gmail.com")
            .buildSmtp();
    EmailProducer emailProducer = new EmailProducer(properties);
    Session session = emailProducer.getSession();

    EmailConsumer emailConsumer = new EmailConsumer(properties);
    emailConsumer.listen(GmailListener.class);
}
```


