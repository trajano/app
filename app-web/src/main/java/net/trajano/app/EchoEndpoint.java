package net.trajano.app;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class EchoEndpoint {
    @OnMessage
    public void onMessage(final Session session, final String message)
            throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
