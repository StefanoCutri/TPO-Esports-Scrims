package org.example.scrims.domain.state;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

public class BuscandoState implements ScrimState {
    @Override
    public void postular(ScrimContext ctx, Usuario u) {
        Scrim s = ctx.getScrim();
        s.agregarPostulacion(u);
        if (s.estaCompleto()){
            ctx.setState(new LobbyArmadoState());
        }
    }

    @Override public void confirmar(ScrimContext ctx, Usuario u) { }
    @Override public void iniciar(ScrimContext ctx) { }
    @Override public void finalizar(ScrimContext ctx) { }
    @Override public void cancelar(ScrimContext ctx) { ctx.setState(new CanceladoState()); }
    @Override public String nombre() { return "Buscando"; }
}
