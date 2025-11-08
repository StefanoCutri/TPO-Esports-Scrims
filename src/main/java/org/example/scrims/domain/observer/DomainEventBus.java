package org.example.scrims.domain.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Objects;

/**
 * Bus de eventos sencillo, thread-safe, in-proc.
 * - subscribe/unsubscribe en O(1) amortizado.
 * - publish notifica a todos los suscriptores.
 */
public final class DomainEventBus {

    private final List<Subscriber> subscribers = new CopyOnWriteArrayList<>();

    public void subscribe(Subscriber s) {
        subscribers.add(Objects.requireNonNull(s, "subscriber"));
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void publish(DomainEvent event) {
        for (Subscriber s : subscribers) {
            try {
                s.onEvent(event);
            } catch (RuntimeException ex) {
                // Nunca explotar el flujo por un subscriptor
                // (podés loggear acá si tenés logger)
            }
        }
    }

    public int subscriberCount() { return subscribers.size(); }
}
