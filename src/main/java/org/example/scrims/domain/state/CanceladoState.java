package org.example.scrims.domain.state;
import org.example.scrims.domain.model.Usuario;

public class CanceladoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, Usuario u) {
        throw new IllegalStateException("Scrim cancelado.");
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {
        throw new IllegalStateException("Scrim cancelado.");
    }

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
