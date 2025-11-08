package org.example.scrims.domain.state;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;
import org.example.scrims.domain.observer.DomainEventBus;
import org.example.scrims.domain.observer.ScrimStateChanged;

public class ScrimContext {

    private final Scrim scrim;
    private ScrimState state;
    private final DomainEventBus bus;


    public ScrimContext(Scrim scrim, DomainEventBus bus) {
        this.bus = bus;
        this.scrim = scrim;
        this.state = new BuscandoState();
        scrim.emitirCambioDeEstado(state.nombre());
    }

    public void setState(ScrimState newState) {
        this.state = newState;
        scrim.emitirCambioDeEstado(newState.nombre());
    }

    public String getStateName() {
        return state.nombre();
    }

    public Scrim getScrim() {
        return scrim;
    }

    // Delegaciones — cada una delega en el estado actual
    public void postular(Usuario u) {
        state.postular(this, u);
    }

    public void confirmar(Usuario u) {
        state.confirmar(this, u);
    }

    public void iniciar() {
        state.iniciar(this);
    }

    public void finalizar() {
        state.finalizar(this);
    }

    public void cancelar() {
        state.cancelar(this);
    }

    public String estadoActual() {
        return state.nombre();
    }
}
