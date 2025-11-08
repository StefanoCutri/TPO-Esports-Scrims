package org.example.scrims.domain.state;
import org.example.scrims.domain.model.Rol;
import org.example.scrims.domain.model.Usuario;

public class ConfirmadoState implements ScrimState {
    @Override
    public void postular(ScrimContext ctx, Usuario u, Rol rol, String ladoDeseado) {
        throw new IllegalStateException("Scrim confirmado: no se admiten nuevas postulaciones.");
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {
        // ya está confirmado el lobby; no hace nada idempotente
    }

    @Override
    public void iniciar(ScrimContext ctx) {
        ctx.setState(new EnJuegoState());
    }

    @Override
    public void finalizar(ScrimContext ctx) {
        throw new IllegalStateException("No se puede finalizar sin iniciar.");
    }

    @Override
    public void cancelar(ScrimContext ctx) {
        ctx.setState(new CanceladoState());
    }

    @Override
    public String nombre() { return "Confirmado"; }
}
