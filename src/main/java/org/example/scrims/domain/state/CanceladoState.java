package org.example.scrims.domain.state;

public class CanceladoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, org.example.scrims.domain.model.Usuario u) {
        throw new IllegalStateException("Scrim cancelado.");
    }

    @Override
    public void confirmar(ScrimContext ctx, org.example.scrims.domain.model.Usuario u) { }

    @Override
    public void iniciar(ScrimContext ctx) {
        throw new IllegalStateException("Scrim cancelado.");
    }

    @Override
    public void finalizar(ScrimContext ctx) { }

    @Override
    public void cancelar(ScrimContext ctx) { }

    @Override
    public String nombre() { return "Cancelado"; }
}
