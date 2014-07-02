package net.trajano.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.websocket.Session;

/**
 * Manages web socket sessions. This is a singleton as it is storing
 * non-serializable data namely {@link Session}.
 *
 * @author Archimedes Trajano
 *
 */
@Singleton
public class WebSocketSessions {
    /**
     * Map of sessions.
     */
    private final Map<String, Session> map = new HashMap<>();

    /**
     * Gets a collection of all sessions.
     *
     * @return all sessions
     */
    public Collection<Session> getAllSessions() {
        return map.values();
    }

    /**
     * Puts a session in the collection.
     *
     * @param session
     *            session
     */
    public void put(final Session session) {
        map.put(session.getId(), session);
    }

    /**
     * Remove session from the collection.
     *
     * @param session
     *            session
     */
    public void remove(final Session session) {
        map.remove(session.getId());
    }
}
