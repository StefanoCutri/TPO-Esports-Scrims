package org.example.scrims.infra.notify;

/**
 * Abstract Factory para crear notificaciones según el canal.
 */
public interface NotifierFactory {
    Notifier createEmail();
    Notifier createPush();
    Notifier createDiscord();
}
