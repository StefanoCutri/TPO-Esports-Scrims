package org.example.scrims.app;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;
import org.example.scrims.domain.observer.DiscordNotifierSubscriber;
import org.example.scrims.domain.observer.DomainEventBus;
import org.example.scrims.domain.observer.EmailNotifierSubscriber;
import org.example.scrims.domain.observer.PushNotifierSubscriber;
import org.example.scrims.domain.state.BuscandoState;
import org.example.scrims.domain.state.ScrimContext;
import org.example.scrims.infra.notify.*;

public class Main {
    public static void main(String[] args) {
        new AppMenu().run();
    }
}
