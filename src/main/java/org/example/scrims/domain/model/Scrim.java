package org.example.scrims.domain.model;

import org.example.scrims.domain.observer.DomainEventBus;
import org.example.scrims.domain.observer.ScrimStateChanged;
import java.util.*;

public class Scrim {
    private final String id = UUID.randomUUID().toString();
    private int rangoMin, rangoMax, cupos;

    // NUEVO: equipos A y B
    private Equipo equipoA;
    private Equipo equipoB;

    // límites opcionales por rol (por lado)
    private final Map<Rol,Integer> reglasRoles = new EnumMap<>(Rol.class);

    private final List<Usuario> participantes = new ArrayList<>();
    private final Map<Usuario, Boolean> confirmaciones = new HashMap<>();
    private final DomainEventBus bus;

    public Scrim(DomainEventBus bus){
        this.bus = bus;
    }

    // --- Configuración de reglas por rol (opcional) ---
    public void setLimiteRol(Rol rol, int maxPorLado) {
        reglasRoles.put(rol, maxPorLado);
    }

    // --- Postulación con lado y rol ---
    public void agregarPostulacion(Usuario u, Rol rol, String ladoDeseado){
        if (estaCompleto()) throw new IllegalStateException("El lobby ya está completo.");

        Equipo destino = elegirEquipo(ladoDeseado);
        validarCupoRol(destino, rol);

        destino.agregar(u, rol);
        if (!confirmaciones.containsKey(u)) {
            participantes.add(u);
            confirmaciones.put(u, false);
        }

        // si se llenó todo, avisamos (para que el State pase a LobbyArmado)
        if (estaCompleto()) {
            emitirCambioDeEstado("LobbyArmado");
        }
    }

    private Equipo elegirEquipo(String ladoDeseado) {
        if (ladoDeseado != null) {
            if (ladoDeseado.equalsIgnoreCase("A")) return equipoA;
            if (ladoDeseado.equalsIgnoreCase("B")) return equipoB;
            throw new IllegalArgumentException("Lado inválido (use A o B)");
        }
        // balanceo simple
        if (!equipoA.estaLleno() && (equipoA.size() <= equipoB.size() || equipoB.estaLleno())) return equipoA;
        return equipoB;
    }

    private void validarCupoRol(Equipo eq, Rol rol) {
        if (eq.estaLleno()) throw new IllegalStateException("No hay más lugar en el equipo " + eq.getLado());
        Integer max = reglasRoles.get(rol);
        if (max != null && eq.cantidadPorRol(rol) >= max) {
            throw new IllegalStateException("No hay más cupo para el rol " + rol + " en el equipo " + eq.getLado());
        }
    }

    public boolean estaCompleto(){
        return equipoA != null && equipoB != null && (equipoA.size() + equipoB.size()) >= cupos;
    }

    public boolean estaParticipando(Usuario u){
        return confirmaciones.containsKey(u);
    }

    // Confirmaciones
    public void confirmarJugador(Usuario u){
        if (confirmaciones.containsKey(u)) confirmaciones.put(u, true);
    }
    public void confirmar(Usuario u){ confirmarJugador(u); }
    public boolean todosConfirmaron(){
        return confirmaciones.values().stream().allMatch(Boolean::booleanValue);
    }

    // Eventos
    public void emitirCambioDeEstado(String nuevoEstado){
        bus.publish(new ScrimStateChanged(id, nuevoEstado));
    }

    // Getters
    public String getId(){ return id; }
    public int getRangoMin(){ return rangoMin; }
    public int getRangoMax(){ return rangoMax; }
    public int getCupos(){ return cupos; }
    public List<Usuario> getParticipantes(){ return participantes; }
    public Equipo getEquipoA() { return equipoA; }
    public Equipo getEquipoB() { return equipoB; }

    // Setters
    public void setRango(int min, int max){ this.rangoMin=min; this.rangoMax=max; }

    public void setCupos(int cupos){
        this.cupos=cupos;
        int porLado = Math.max(1, cupos / 2);
        int resto   = cupos - porLado;
        this.equipoA = new Equipo("A", porLado);
        this.equipoB = new Equipo("B", resto);
    }
}
