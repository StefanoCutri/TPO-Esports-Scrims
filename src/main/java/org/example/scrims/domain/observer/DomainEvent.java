package org.example.scrims.domain.observer;

/**
 * Marcador para eventos de dominio.
 * Sugerencia: exponer sólo datos inmutables.
 */
public interface DomainEvent {
    String eventName();
    long occurredAtEpochMillis();
}
