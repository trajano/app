package net.trajano.app;

import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebService;

import net.trajano.app.schema.MessageBean;

/**
 * Web service endpoint example. The {@link WebService#targetNamespace()} is
 * defined to conform with enterprise standards. No WSDL file was used because
 * web services methods are easier to write and define in Java code. However,
 * the message data should be in XSDs. Note that "Service" gets appended to the
 * name of the class.
 * 
 * @author Archimedes
 */
@WebService(targetNamespace = "http://app.trajano.net/ws")
public class SomeWeb {
    /**
     * Persisted beans table model.
     */
    @EJB
    private PersistedBeans persistedBeans;

    /**
     * Echos back the input input string.
     * 
     * @param s
     *            input string
     * @return the input string
     */
    public String echo(final String s) {
        return s;
    }

    /**
     * Echos back the original message data.
     * 
     * @param messageBean
     *            message bean.
     * @return the input message bean.
     */
    public MessageBean echoMessage(final MessageBean messageBean) {
        return messageBean;
    }

    /**
     * Gets a bean by ID.
     * 
     * @param id
     *            id of bean to retrieve
     * @return persisted bean
     */
    public MessageBean getBean(final long id) {
        return mapPersistedBeanToMessageBean(persistedBeans.get(id));
    }

    /**
     * Gets the latest stored persisted bean.
     * 
     * @return latest stored persisted bean.
     */
    public MessageBean getLatest() {
        return mapPersistedBeanToMessageBean(persistedBeans.getLatest());
    }

    private MessageBean mapMessageBeanToPersistedBean(
            final PersistedBean persistedBean) {
        // TODO
        return null;
    }

    private MessageBean mapPersistedBeanToMessageBean(
            final PersistedBean persistedBean) {
        final MessageBean ret = new MessageBean();
        ret.setMessage(persistedBean.getMessage());
        return ret;
    }

    /**
     * Saves the message bean data into a persisted bean.
     * 
     * @param messageBean
     */
    @Oneway
    public void persistBean(final MessageBean messageBean) {
        // TODO
    }
}
