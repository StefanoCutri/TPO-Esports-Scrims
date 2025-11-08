package org.example.scrims.domain.observer;

import org.example.scrims.infra.notify.Notifier;
import org.example.scrims.infra.notify.NotifierFactory;

/** Envía notificación push cuando cambia el estado del scrim. */
public class PushNotifierSubscriber implements Subscriber {

    private final Notifier push;

    public PushNotifierSubscriber(NotifierFactory factory) {
        this.push = factory.createPush();
    }

    @Override
    public void onEvent(DomainEvent event) {
        if (event instanceof ScrimStateChanged ev) {
            String msg = "Scrim " + ev.scrimId() + " → " + ev.newState();
            push.send(msg);
        }
    }
}
