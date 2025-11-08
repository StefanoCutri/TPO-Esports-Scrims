package org.example.scrims.domain.state;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

public class LobbyArmadoState implements ScrimState {

    @Override
    public void postular(ScrimContext ctx, Usuario u) {
        throw new IllegalStateException("El lobby ya está completo. No se puede postular.");
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {
        Scrim s = ctx.getScrim();
        if (!s.estaParticipando(u)) throw new IllegalArgumentException("El usuario no está en el lobby.");
        s.confirmar(u);
        if (s.todosConfirmaron()) {
            ctx.setState(new ConfirmadoState());
        }
    }

    @Override
    public void iniciar(ScrimContext ctx) {
        throw new IllegalStateException("Debe estar Confirmado para iniciar.");
    }

    @Override
    public void finalizar(ScrimContext ctx) {
        throw new IllegalStateException("No se puede finalizar antes de iniciar.");
    }

    @Override
    public void cancelar(ScrimContext ctx) {
        ctx.setState(new CanceladoState());
    }

    @Override
    public String nombre() { return "LobbyArmado"; }
}
