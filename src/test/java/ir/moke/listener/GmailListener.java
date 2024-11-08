package ir.moke.listener;

import jakarta.mail.Message;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.event.MessageCountListener;

public class GmailListener implements MessageCountListener {

    @Override
    public void messagesAdded(MessageCountEvent messageCountEvent) {
        for (Message message : messageCountEvent.getMessages()) {
            System.out.println(message);


        }
    }

    @Override
    public void messagesRemoved(MessageCountEvent messageCountEvent) {
        for (Message message : messageCountEvent.getMessages()) {
            System.out.println(message);
        }
    }
}
