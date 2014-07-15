package net.trajano.app;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

import com.w3schools.webservices.TempConvert;
import com.w3schools.webservices.TempConvertSoap;

/**
 * Hello world servlet.
 */
@WebServlet("/hello")
@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {

    /**
     * Mail session resource.
     */
    @Resource(name = "mail/MyMail")
    private Session mailSession;

    /**
     * Web Service client reference.
     */
    @WebServiceRef(TempConvert.class)
    private TempConvertSoap tempConvert;

    /**
     * WebSocket sessions.
     */
    @EJB
    private WebSocketSessions webSocketSessions;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        resp.getWriter().print(
                String.format("<div id='text'>Hello servlet on %s</div>",
                        new Date()));
        resp.getWriter().print(
                String.format("<div id='temp'>312F is %sC</div>",
                        tempConvert.fahrenheitToCelsius("312")));
        resp.getWriter().print(
                String.format("<div id='mail'>mailSession is %s</div>",
                        mailSession));
        resp.getWriter().print(
                String.format(
                        "<div id='websocket'>webSocketSessions is %s</div>",
                        webSocketSessions));
    }
}
