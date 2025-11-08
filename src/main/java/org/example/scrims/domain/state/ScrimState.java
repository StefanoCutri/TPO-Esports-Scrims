package org.example.scrims.domain.state;

import org.example.scrims.domain.model.Usuario;

public interface ScrimState {
    void postular(ScrimContext ctx, Usuario u);
    void confirmar(ScrimContext ctx, Usuario u);
    void iniciar(ScrimContext ctx);
    void finalizar(ScrimContext ctx);
    void cancelar(ScrimContext ctx);
    String nombre();
}
