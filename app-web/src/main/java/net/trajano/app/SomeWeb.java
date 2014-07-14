package net.trajano.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;

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
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
public class SomeWeb {
    /**
     * Persisted beans table model.
     */
    @EJB
    private PersistedBeans persistedBeans;

    /**
     * Echos back the original message data.
     *
     * @param messageBean
     *            message bean.
     * @return the input message bean.
     */
    @WebMethod(action = "echoMessage")
    public MessageBean echoMessage(
            @WebParam(targetNamespace = "http://app.trajano.net/schema") final MessageBean messageBean) {
        return messageBean;
    }

    /**
     * Gets a bean by ID.
     *
     * @param id
     *            id of bean to retrieve
     * @return persisted bean
     */
    @WebMethod(action = "getBean")
    public MessageBean getBean(final long id) {
        return mapPersistedBeanToMessageBean(persistedBeans.get(id));
    }

    /**
     * Gets the latest stored persisted bean.
     *
     * @return latest stored persisted bean.
     */
    @WebMethod(action = "getLatest")
    public MessageBean getLatest() {
        return mapPersistedBeanToMessageBean(persistedBeans.getLatest());
    }

    /**
     * Maps a persisted bean to a message bean.
     *
     * @param persistedBean
     *            persisted bean.
     * @return message bean
     */
    private MessageBean mapPersistedBeanToMessageBean(
            final PersistedBean persistedBean) {
        final MessageBean ret = new MessageBean();
        ret.setMessage(persistedBean.getMessage());
        final Calendar c = new GregorianCalendar();
        c.setTime(persistedBean.getDate());
        ret.setTimestamp(c);
        return ret;
    }

    /**
     * Saves the message bean data into a persisted bean.
     *
     * @param messageBean
     *            data to persist
     */
    @Oneway
    public void persistBean(
            @WebParam(targetNamespace = "http://app.trajano.net/schema") final MessageBean messageBean) {
        final PersistedBean persistedBean = new PersistedBean();
        persistedBean.setMessage(messageBean.getMessage());
        persistedBean.setSomeDate(messageBean.getTimestamp().getTime());
        persistedBean.setSomeTime(messageBean.getTimestamp().getTime());
        persistedBean.setSomeTimestamp(messageBean.getTimestamp().getTime());
        persistedBeans.save(persistedBean);
    }

    /**
     * Echos back the input input string reversed.
     *
     * @param s
     *            input string
     * @return the input string
     */
    @WebMethod(action = "reverse")
    public String reverse(final String s) {
        final char[] a = s.toCharArray();
        final int len = a.length;
        final StringBuilder r = new StringBuilder(len);
        for (int i = len - 1; i >= 0; --i) {
            r.append(a[i]);
        }
        return r.toString();
    }
}
