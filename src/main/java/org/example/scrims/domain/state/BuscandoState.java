package org.example.scrims.domain.state;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

public class BuscandoState implements ScrimState {
    @Override
    public void postular(ScrimContext ctx, Usuario u) {
        Scrim s = ctx.getScrim();
        if (s.estaParticipando(u)) return; // ya está
        s.agregarPostulacion(u);

        if (s.estaCompleto()) {
            ctx.setState(new LobbyArmadoState());
        }
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {
        throw new IllegalStateException("Aún no está Lobby armado: no se puede confirmar.");
    }
    @Override public void iniciar(ScrimContext ctx)   { throw new IllegalStateException("No se puede iniciar desde Buscando."); }
    @Override public void finalizar(ScrimContext ctx) { throw new IllegalStateException("No se puede finalizar desde Buscando."); }

    @Override
    public void cancelar(ScrimContext ctx) {
        ctx.setState(new CanceladoState());
    }

    @Override public String nombre() { return "Buscando"; }
}
