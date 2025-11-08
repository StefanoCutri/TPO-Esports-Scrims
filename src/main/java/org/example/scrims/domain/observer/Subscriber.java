package org.example.scrims.domain.observer;

/**
 * Cualquier componente interesado en eventos implementa esta interfaz.
 */
@FunctionalInterface
public interface Subscriber {
    void onEvent(DomainEvent event);
}
