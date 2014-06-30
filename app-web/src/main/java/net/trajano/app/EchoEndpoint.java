package net.trajano.app;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Web socket endpoint.
 *
 * @author Archimedes Trajano
 */
@ServerEndpoint("/echo")
@Stateless
public class EchoEndpoint {
    /**
     * Resource bundle.
     */
    private static final ResourceBundle R = ResourceBundle
            .getBundle("net/trajano/app/internal/Messages");

    @EJB
    private WebSocketSessions sessions;

    @OnClose
    public void onClose(final Session session) {
        sessions.remove(session);
    }

    /**
     * Sends back the original message.
     *
     * @param session
     *            web socket session
     * @param message
     *            the message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(final Session session, final String message)
            throws IOException {
        System.out.println(sessions);
        session.getBasicRemote()
        .sendText(message + " hello " + session.getId());
        System.out.println("about to send to " + sessions.getAllSessions());
        for (final Session remoteSession : sessions.getAllSessions()) {
            if (remoteSession.isOpen()) {
                remoteSession.getBasicRemote().sendText(
                        String.format(R.getString("sample"), message,
                                session.getId()));
            }
        }

    }

    @OnOpen
    public void onOpen(final Session session, final EndpointConfig config) {
        System.out.println("ON OPEN " + sessions);
        System.out.println(config.getUserProperties());
        sessions.put(session);
    }
}
