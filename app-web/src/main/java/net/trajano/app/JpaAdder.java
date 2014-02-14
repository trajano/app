package net.trajano.app;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Adds a new record to the database when there is a new message.
 * 
 * @author Archimedes Trajano
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/MyQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class JpaAdder implements MessageListener {

	/**
	 * Injected SLSB.
	 */
	@EJB
	private PersistedBeans persistedBeans;

	@Override
	public void onMessage(final Message message) {

		final PersistedBean bean = new PersistedBean();
		try {
			bean.setMessage(((TextMessage) message).getText());
		} catch (final JMSException e) {
			throw new JMSRuntimeException(e.getMessage(), e.getErrorCode(), e);
		}
		persistedBeans.save(bean);
	}

}
