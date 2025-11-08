package org.example.scrims.domain.strategy;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy que selecciona jugadores basándose en el MMR.
 */
public class ByMMRStrategy implements MatchmakingStrategy {

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos, Scrim scrim) {
        List<Usuario> seleccion = new ArrayList<>();
        int cupos = scrim.getCupos();

        for (Usuario u : candidatos) {
            if (seleccion.size() >= cupos) break;

            int mmr = u.getMmr();
            if (mmr >= scrim.getRangoMin() && mmr <= scrim.getRangoMax()) {
                seleccion.add(u);
            }
        }

        return seleccion;
    }
}
