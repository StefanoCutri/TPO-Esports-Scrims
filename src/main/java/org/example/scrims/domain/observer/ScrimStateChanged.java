package org.example.scrims.domain.observer;

import java.util.Objects;
import java.util.UUID;

/**
 * Evento simple para cambios de estado del Scrim.
 */
public final class ScrimStateChanged implements DomainEvent {
    private final String scrimId;
    private final String newState;
    private final long occurredAt;

    public ScrimStateChanged(String scrimId, String newState) {
        this.scrimId = Objects.requireNonNull(scrimId, "scrimId");
        this.newState = Objects.requireNonNull(newState, "newState");
        this.occurredAt = System.currentTimeMillis();
    }

    public String scrimId() { return scrimId; }
    public String newState() { return newState; }

    @Override public String eventName() { return "ScrimStateChanged"; }
    @Override public long occurredAtEpochMillis() { return occurredAt; }

    @Override public String toString() {
        return "ScrimStateChanged{scrimId=" + scrimId + ", newState='" + newState + "'}";
    }
}
