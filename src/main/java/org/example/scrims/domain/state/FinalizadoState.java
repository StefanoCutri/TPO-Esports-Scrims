package org.example.scrims.domain.state;
import org.example.scrims.domain.model.Usuario;

public class FinalizadoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, Usuario u) {
        throw new IllegalStateException("Scrim finalizado.");
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) { }

    @Override
    public void iniciar(ScrimContext ctx) {
        throw new IllegalStateException("Scrim finalizado: no se puede cancelar.");
    }

    @Override
    public void finalizar(ScrimContext ctx) { }

    @Override
    public void cancelar(ScrimContext ctx) {
        throw new IllegalStateException("Scrim finalizado: no se puede cancelar.");
    }

    @Override
    public String nombre() { return "Finalizado"; }
}
