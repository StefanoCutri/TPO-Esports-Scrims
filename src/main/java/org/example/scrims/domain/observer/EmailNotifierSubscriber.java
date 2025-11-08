package org.example.scrims.domain.observer;

import org.example.scrims.infra.notify.Notifier;
import org.example.scrims.infra.notify.NotifierFactory;

/** Envía un email (via factory) cuando cambia el estado del scrim. */
public class EmailNotifierSubscriber implements Subscriber {

    private final Notifier email;

    public EmailNotifierSubscriber(NotifierFactory factory) {
        this.email = factory.createEmail();
    }

    @Override
    public void onEvent(DomainEvent event) {
        if (event instanceof ScrimStateChanged ev) {
            String subject = "[Scrims] Cambio de estado";
            String body = "Scrim " + ev.scrimId() + " cambió a estado: " + ev.newState();
            // Si tu Notifier de email solo tiene send(String), concatená subject+body
            email.send(subject + " - " + body);
        }
    }
}
