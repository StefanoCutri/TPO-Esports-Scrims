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

        // 1) Bus de eventos
        DomainEventBus bus = new DomainEventBus();

        // 2) Fábrica de notificaciones (modo desarrollo → imprime en consola)
        NotifierFactory factory = new DevNotifierFactory();

        // 3) Registrar subscriptores
        bus.subscribe(new DiscordNotifierSubscriber(factory));
        bus.subscribe(new EmailNotifierSubscriber(factory));
        bus.subscribe(new PushNotifierSubscriber(factory));

        // 4) Crear scrim
        Scrim scrim = new Scrim(bus);
        scrim.setRango(1400, 2000);
        scrim.setCupos(2);

        // 5) Crear contexto en estado inicial
        ScrimContext ctx = new ScrimContext(scrim, new BuscandoState());

        // 6) Crear jugadores
        Usuario a = new Usuario("Alice", 1500);
        Usuario b = new Usuario("Bob", 1600);

        // 7) Simulación
        System.out.println("\n--- POSTULACIONES ---");
        ctx.postular(a);
        ctx.postular(b); // Aquí ya debería pasar automáticamente a LobbyArmado

        System.out.println("\n--- CONFIRMACIONES ---");
        ctx.confirmar(a);
        ctx.confirmar(b); // → ConfirmadoState

        System.out.println("\n--- INICIAR PARTIDA ---");
        ctx.iniciar(); // → EnJuegoState

        System.out.println("\n--- FINALIZAR PARTIDA ---");
        ctx.finalizar(); // → FinalizadoState

        System.out.println("\nEstado final: " + ctx.getStateName());
    }
}
