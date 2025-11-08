package org.example.scrims.infra.notify;

public class DevNotifierFactory implements NotifierFactory {

    @Override
    public Notifier createEmail() {
        return message -> System.out.println("[EMAIL] " + message);
    }

    @Override
    public Notifier createPush() {
        return message -> System.out.println("[PUSH] " + message);
    }

    @Override
    public Notifier createDiscord() {
        return message -> System.out.println("[DISCORD] " + message);
    }
}
