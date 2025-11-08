package org.example.scrims.domain.state;

public class FinalizadoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, org.example.scrims.domain.model.Usuario u) {
        throw new IllegalStateException("Scrim finalizado.");
    }

    @Override
    public void confirmar(ScrimContext ctx, org.example.scrims.domain.model.Usuario u) { }

    @Override
    public void iniciar(ScrimContext ctx) { }

    @Override
    public void finalizar(ScrimContext ctx) { }

    @Override
    public void cancelar(ScrimContext ctx) {
        throw new IllegalStateException("Scrim finalizado: no se puede cancelar.");
    }

    @Override
    public String nombre() { return "Finalizado"; }
}
