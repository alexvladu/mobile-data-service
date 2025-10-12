package org.acme.ControllerWebSocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/notifications")
public class NotificationWebSocket {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    System.out.println("Sending message to session " + s.getId() + ": " + message);
                    s.getAsyncRemote().sendText("Broadcast: " + message);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public static Set<Session> getConnectedClients() {
        return Collections.unmodifiableSet(sessions);
    }
}
