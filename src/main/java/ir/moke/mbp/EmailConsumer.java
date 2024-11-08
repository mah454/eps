package ir.moke.mbp;

import jakarta.mail.Session;
import jakarta.mail.event.MessageCountListener;
import org.eclipse.angus.mail.imap.IMAPFolder;
import org.eclipse.angus.mail.imap.IMAPSSLStore;
import org.eclipse.angus.mail.imap.IdleManager;

import java.util.Properties;
import java.util.concurrent.Executors;

public class EmailConsumer {

    private final IMAPSSLStore store;
    private final Session session;
    private final Properties properties;

    public EmailConsumer(Properties properties) {
        this.properties = properties;
        try {
            session = Session.getDefaultInstance(properties, null);
            IMAPSSLStore store = (IMAPSSLStore) session.getStore("imaps");
            store.connect(getUsername(), getPassword());

            if (!store.hasCapability("IDLE")) {
                throw new RuntimeException("server does not supported idle connection");
            }

            this.store = store;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static MessageCountListener createInstance(Class<? extends MessageCountListener> listener) {
        try {
            return listener.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getPassword() {
        return properties.getProperty("mail.password");
    }

    private String getUsername() {
        return properties.getProperty("mail.username");
    }

    public boolean isConnected() {
        return store.isConnected();
    }

    public void listen(Class<? extends MessageCountListener> listener) {
        MessageCountListener emailListener = createInstance(listener);
        try {
            if (store.isConnected()) {
                IMAPFolder inbox = (IMAPFolder) store.getFolder("inbox");
                inbox.open(jakarta.mail.Folder.READ_WRITE);

                IdleManager idleManager = new IdleManager(session, Executors.newVirtualThreadPerTaskExecutor());
                inbox.addMessageCountListener(emailListener);

                while (store.isConnected()) {
                    if (!store.isConnected()) {
                        System.out.println("Disconnected");
                        store.connect(getUsername(), getPassword());
                    }
                    if (!inbox.isOpen()) {
                        System.out.println("Inbox closed");
                        inbox.open(jakarta.mail.Folder.READ_WRITE);
                    }
                    idleManager.watch(inbox);
                }
            } else {
                System.out.println("Connection Failure");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
