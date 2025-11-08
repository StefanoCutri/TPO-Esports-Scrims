package org.example.scrims.domain.model;

import org.example.scrims.domain.observer.DomainEventBus;
import org.example.scrims.domain.observer.ScrimStateChanged;
import java.util.*;

public class Scrim {
    private final String id = UUID.randomUUID().toString(); // mantenemos String
    private int rangoMin, rangoMax, cupos;
    private final List<Usuario> participantes = new ArrayList<>();
    private final Map<Usuario, Boolean> confirmaciones = new HashMap<>();
    private final DomainEventBus bus;

    public Scrim(DomainEventBus bus){
        this.bus = bus;
    }

    // --- Postulación ---
    public void agregarPostulacion(Usuario u){
        if (!estaCompleto()) {
            participantes.add(u);
            confirmaciones.put(u, false);
        }
    }

    public boolean estaCompleto(){
        return participantes.size() >= cupos;
    }

    // helper usado por los states
    public boolean estaParticipando(Usuario u){
        return confirmaciones.containsKey(u);
    }

    // --- Confirmaciones ---
    public void confirmarJugador(Usuario u){
        if (confirmaciones.containsKey(u)) {
            confirmaciones.put(u, true);
        }
    }

    // alias para que los states que llamen s.confirmar(u) funcionen
    public void confirmar(Usuario u){
        confirmarJugador(u);
    }

    public boolean todosConfirmaron(){
        return confirmaciones.values().stream().allMatch(Boolean::booleanValue);
    }

    // --- Eventos de cambio de estado ---
    public void emitirCambioDeEstado(String nuevoEstado){
        // ScrimStateChanged aceptará String (ver sección 2)
        bus.publish(new ScrimStateChanged(id, nuevoEstado));
    }


    // --- Getters y setters ---
    public String getId(){ return id; }
    public int getRangoMin(){ return rangoMin; }
    public int getRangoMax(){ return rangoMax; }
    public int getCupos(){ return cupos; }
    public List<Usuario> getParticipantes(){ return participantes; }

    public void setRango(int min, int max){ this.rangoMin=min; this.rangoMax=max; }
    public void setCupos(int cupos){ this.cupos=cupos; }
}
