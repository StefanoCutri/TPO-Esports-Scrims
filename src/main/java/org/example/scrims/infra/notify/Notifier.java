package org.example.scrims.infra.notify;

/**
 * Representa un medio de notificación (email, push, discord, etc.)
 */
public interface Notifier {
    void send(String message);
}
