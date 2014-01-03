package net.trajano.app;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Web socket endpoint.
 * 
 * @author Archimedes
 */
@ServerEndpoint("/echo")
public class EchoEndpoint {
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
        session.getBasicRemote().sendText(message);
    }
}
