package org.example.scrims.domain.strategy;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

import java.util.List;

/**
 * Strategy para seleccionar jugadores para un scrim.
 */
public interface MatchmakingStrategy {
    List<Usuario> seleccionar(List<Usuario> candidatos, Scrim scrim);
}
