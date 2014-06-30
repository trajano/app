package net.trajano.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.websocket.Session;

@Singleton
public class WebSocketSessions {
    private final Map<String, Session> map = new HashMap<>();

    public Collection<Session> getAllSessions() {
        return map.values();
    }

    public void put(final Session session) {
        map.put(session.getId(), session);
        System.out.println("put " + session);
    }

    public void remove(final Session session) {
        map.remove(session.getId());
    }
}
