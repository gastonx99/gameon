package se.dandel.gameon.adapter.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.MessageDrivenContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/Team.Q"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class TeamMDB implements MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private MessageDrivenContext mdc;

    @Override
    public void onMessage(Message message) {
        try {
            LOGGER.info("Received message {}", message.getJMSMessageID());
            if (TextMessage.class.isAssignableFrom(message.getClass())) {
                TextMessage textMessage = TextMessage.class.cast(message);
                LOGGER.info("Received text {}", textMessage.getText());
            } else {
                throw new IllegalArgumentException("Textmessages only");
            }
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
            mdc.setRollbackOnly();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            mdc.setRollbackOnly();
        }

    }
}
