package org.example.scrims.app;

import org.example.scrims.domain.model.Rol;
import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;
import org.example.scrims.domain.observer.DiscordNotifierSubscriber;
import org.example.scrims.domain.observer.DomainEventBus;
import org.example.scrims.domain.observer.EmailNotifierSubscriber;
import org.example.scrims.domain.observer.PushNotifierSubscriber;
import org.example.scrims.domain.state.ScrimContext;
import org.example.scrims.domain.strategy.ByHistoryStrategy;
import org.example.scrims.domain.strategy.ByLatencyStrategy;
import org.example.scrims.domain.strategy.ByMMRStrategy;
import org.example.scrims.domain.strategy.MatchmakingStrategy;
import org.example.scrims.infra.notify.DevNotifierFactory;
import org.example.scrims.infra.notify.NotifierFactory;

import java.util.*;

public class AppMenu {

    private final Scanner in = new Scanner(System.in);
    private final DomainEventBus bus = new DomainEventBus();

    // “BD” en memoria
    private final Map<String, ScrimContext> scrims = new LinkedHashMap<>();
    private final Map<String, Usuario> usuarios = new LinkedHashMap<>();
    private final NotifierFactory factory = new DevNotifierFactory();

    public AppMenu() {
        // Ver los eventos en consola (además de tus notifiers, si están suscritos)
        bus.subscribe(e -> System.out.println("[EVENT] " + e));
        bus.subscribe(new DiscordNotifierSubscriber(factory));
        bus.subscribe(new EmailNotifierSubscriber(factory));
        bus.subscribe(new PushNotifierSubscriber(factory));
    }

    public void run() {
        while (true) {
            System.out.println("\n=== eScrims :: Menu ===");
            System.out.println("1) Crear usuario");
            System.out.println("2) Crear scrim");
            System.out.println("3) Listar scrims");
            System.out.println("4) Unirse / Postularse a un scrim");
            System.out.println("5) Confirmar participación");
            System.out.println("6) Iniciar scrim");
            System.out.println("7) Finalizar scrim");
            System.out.println("8) Cambiar estrategia de emparejamiento");
            System.out.println("0) Salir");
            System.out.print("> ");
            String op = in.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> crearUsuario();
                    case "2" -> crearScrim();
                    case "3" -> listarScrims();
                    case "4" -> postularseAScrim();
                    case "5" -> confirmarParticipacion();
                    case "6" -> iniciarScrim();
                    case "7" -> finalizarScrim();
                    case "8" -> cambiarEstrategia();
                    case "0" -> { System.out.println("¡Nos vemos!"); return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception ex) {
                System.out.println("⚠️  Error: " + ex.getMessage());
            }
        }
    }

    // ------- Usuarios -------
    private void crearUsuario() {
        System.out.print("Username: ");
        String username = in.nextLine().trim();
        if (username.isEmpty()) { System.out.println("El username no puede estar vacío."); return; }

        if (usuarios.containsKey(username)) {
            System.out.println("Ya existe un usuario con ese username.");
            return;
        }

        int mmr = leerEntero("MMR (ej. 1500): ");
        Usuario u = new Usuario(username, mmr);
        usuarios.put(username, u);
        System.out.println("✅ Usuario creado: " + username + " (mmr=" + mmr + ")");
    }

    // ------- Scrims -------
    private void crearScrim() {
        int rangoMin = leerEntero("Rango mínimo (ej. 1400): ");
        int rangoMax = leerEntero("Rango máximo (ej. 2000): ");
        int cupos    = leerEntero("Cupos totales (ej. 10 para 5v5, 2 para 1v1): ");

        Scrim s = new Scrim(bus);
        s.setRango(rangoMin, rangoMax);
        s.setCupos(cupos);

        ScrimContext ctx = new ScrimContext(s, bus);
        scrims.put(s.getId(), ctx);

        System.out.println("✅ Scrim creado con id: " + s.getId());
        System.out.println("   rango=[" + rangoMin + " - " + rangoMax + "], cupos=" + cupos);
    }

    private void listarScrims() {
        if (scrims.isEmpty()) {
            System.out.println("(No hay scrims creados)");
            return;
        }
        System.out.println("Scrims:");
        scrims.forEach((id, ctx) -> {
            var s = ctx.getScrim();
            String estado;
            try {
                // usa el que tengas disponible
                estado = ctx.estadoActual();           // si existe
            } catch (Throwable t) {
                try { estado = ctx.getStateName(); }    // alternativa
                catch (Throwable t2) { estado = "(estado)"; }
            }
            System.out.println(" - " + id +
                    " | estado=" + estado +
                    " | participantes=" + s.getParticipantes().size() + "/" + s.getCupos() +
                    " | rango=[" + s.getRangoMin() + " - " + s.getRangoMax() + "]");
        });
    }

    // ------- Postulación / Unirse -------
    private void postularseAScrim() {
        if (scrims.isEmpty()) {
            System.out.println("(No hay scrims creados)");
            return;
        }

        listarScrims();
        System.out.print("Ingresá el ID del scrim al que querés unirte: ");
        String id = in.nextLine().trim();
        ScrimContext ctx = scrims.get(id);
        if (ctx == null) {
            System.out.println("No existe ese scrim.");
            return;
        }

        Usuario u = pedirUsuarioExistenteOCrearlo();
        Rol rol = elegirRol();
        String lado = elegirLado(); // puede ser null

        try {
            ctx.postular(u, rol, lado); // ✅ ahora coincide con la firma correcta
            System.out.println("✅ " + u.getUsername() + " se postuló correctamente.");
        } catch (Exception e) {
            System.out.println("⚠️  " + e.getMessage());
        }
    }

    // === Confirmar participación ===
    private void confirmarParticipacion() {
        if (scrims.isEmpty()) {
            System.out.println("No hay scrims creados.");
            return;
        }

        listarScrims();
        String scrimId = leerLinea("ID del scrim a confirmar: ").trim();
        var ctx = scrims.get(scrimId);
        if (ctx == null) {
            System.out.println("Scrim no encontrado.");
            return;
        }

        String username = leerLinea("Tu username: ").trim();
        var u = usuarios.get(username);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        try {
            ctx.confirmar(u);
            System.out.println("✅ " + username + " confirmó su participación.");
            System.out.println("Estado actual: " + ctx.estadoActual());
        } catch (IllegalStateException | IllegalArgumentException ex) {
            System.out.println("⚠️  " + ex.getMessage());
        }
    }

    // === Iniciar scrim ===
    private void iniciarScrim() {
        if (scrims.isEmpty()) {
            System.out.println("No hay scrims creados.");
            return;
        }

        listarScrims();
        String scrimId = leerLinea("ID del scrim a iniciar: ").trim();
        var ctx = scrims.get(scrimId);
        if (ctx == null) {
            System.out.println("Scrim no encontrado.");
            return;
        }

        try {
            ctx.iniciar();
            System.out.println("🚀 Scrim iniciado. Estado: " + ctx.estadoActual());
        } catch (IllegalStateException ex) {
            System.out.println("⚠️  " + ex.getMessage());
        }
    }

    // === Finalizar scrim ===
    private void finalizarScrim() {
        if (scrims.isEmpty()) {
            System.out.println("No hay scrims creados.");
            return;
        }

        listarScrims();
        String scrimId = leerLinea("ID del scrim a finalizar: ").trim();
        var ctx = scrims.get(scrimId);
        if (ctx == null) {
            System.out.println("Scrim no encontrado.");
            return;
        }

        try {
            ctx.finalizar();
            System.out.println("🏁 Scrim finalizado. Estado: " + ctx.estadoActual());
        } catch (IllegalStateException ex) {
            System.out.println("⚠️  " + ex.getMessage());
        }
    }

    private void cambiarEstrategia() {
        System.out.println("""
        Elegí estrategia:
        1) Por MMR (default)
        2) Por Latencia (ping)
        3) Por Historial / Reputación
    """);

        int op = leerEntero("> ");
        MatchmakingStrategy s;

        switch (op) {
            case 1 -> s = new ByMMRStrategy();
            case 2 -> s = new ByLatencyStrategy();
            case 3 -> s = new ByHistoryStrategy();
            default -> {
                System.out.println("⚠️ Opción inválida.");
                return;
            }
        }

        // Aplicar a todos los scrims activos
        scrims.values().forEach(ctx -> ctx.setStrategy(s));

        System.out.println("✅ Estrategia actualizada para todos los scrims.");
    }

    private Rol elegirRol() {
        System.out.println("""
    Elegí tu rol:
    1) DUELISTA
    2) TANQUE
    3) SOPORTE
    4) FLEX
    """);
        int op = leerEntero("Opción: ");
        return switch(op) {
            case 1 -> Rol.DUELIST;
            case 2 -> Rol.ENTRY;
            case 3 -> Rol.SNIPER;
            case 4 -> Rol.FLEX;
            case 5 -> Rol.SUPPORT;
            default -> {
                System.out.println("Rol inválido, se asigna FLEX por defecto.");
                yield Rol.FLEX;
            }
        };
    }

    private String elegirLado() {
        System.out.print("Elegí lado (A / B) o Enter para auto: ");
        String lado = in.nextLine().trim().toUpperCase();
        return (lado.equals("A") || lado.equals("B")) ? lado : null;
    }



    // ------- Helpers -------
    private Usuario pedirUsuarioExistenteOCrearlo() {
        System.out.print("Tu username: ");
        String username = in.nextLine().trim();
        Usuario u = usuarios.get(username);
        if (u != null) return u;

        System.out.println("(No existe. Lo creamos rápido)");
        int mmr = leerEntero("MMR para " + username + ": ");
        u = new Usuario(username, mmr);
        usuarios.put(username, u);
        System.out.println("✅ Usuario creado: " + username + " (mmr=" + mmr + ")");
        return u;
    }

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Debe ser un número entero."); }
        }
    }

    private String leerLinea(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
}
