package org.example.scrims.domain.observer;

import org.example.scrims.infra.notify.Notifier;
import org.example.scrims.infra.notify.NotifierFactory;

/** Envía un mensaje a Discord cuando cambia el estado de un scrim. */
public class DiscordNotifierSubscriber implements Subscriber {

    private final Notifier discord;

    public DiscordNotifierSubscriber(NotifierFactory factory) {
        this.discord = factory.createDiscord();
    }

    @Override
    public void onEvent(DomainEvent event) {
        if (event instanceof ScrimStateChanged ev) {
            String msg = "**Scrim " + ev.scrimId() + "** → Estado: **" + ev.newState() + "**";
            discord.send(msg);
        }
    }
}
