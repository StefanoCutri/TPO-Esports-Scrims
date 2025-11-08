package org.example.scrims.infra.notify;

public class ProdNotifierFactory implements NotifierFactory {

    @Override
    public Notifier createPush() {
        return n -> {
            // Aquí iría FirebaseAdapter.send(n) en una integración real
            System.out.println("[PROD-PUSH] Enviado a dispositivo: " + n);
        };
    }

    @Override
    public Notifier createEmail() {
        return n -> {
            // Aquí iría SendGridAdapter.send(n)
            System.out.println("[PROD-EMAIL] Enviado por SendGrid: " + n);
        };
    }

    @Override
    public Notifier createDiscord() {
        return n -> {
            // Aquí iría DiscordAdapter.send(n)
            System.out.println("[PROD-DISCORD] Enviado al webhook: " + n);
        };
    }
}
