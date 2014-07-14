package net.trajano.app;

import javax.ejb.EJB;
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
public class SecondWeb {
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
    @WebMethod(action = "reverse")
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
    @WebMethod(action = "echoMessage")
    public MessageBean echoMessage(
            @WebParam(targetNamespace = "http://app.trajano.net/schema") final MessageBean messageBean) {
        return messageBean;
    }

}
