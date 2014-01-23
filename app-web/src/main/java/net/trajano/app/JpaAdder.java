package net.trajano.app;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Adds a new record to the database when there is a new message.
 * 
 * @author Archimedes
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/MyQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class JpaAdder implements MessageListener {

    @EJB
    private PersistedBeans persistedBeans;

    @Override
    public void onMessage(final Message message) {

        final PersistedBean bean = new PersistedBean();
        try {
            bean.setMessage(((TextMessage) message).getText());
        } catch (final JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        persistedBeans.save(bean);
    }

}
