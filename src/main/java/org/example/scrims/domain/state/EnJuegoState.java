package org.example.scrims.domain.state;
import org.example.scrims.domain.model.Rol;
import org.example.scrims.domain.model.Usuario;

public class EnJuegoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, Usuario u, Rol rol, String ladoDeseado) {
        throw new IllegalStateException("Scrim en juego: no se admiten postulaciones.");
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {
        // sin efecto en juego
    }

    @Override
    public void iniciar(ScrimContext ctx) {
        // ya está en juego
    }

    @Override
    public void finalizar(ScrimContext ctx) {
        ctx.setState(new FinalizadoState());
    }

    @Override
    public void cancelar(ScrimContext ctx) {
        throw new IllegalStateException("No se puede cancelar un scrim en juego. Finalizalo.");
    }

    @Override
    public String nombre() { return "EnJuego"; }
}
