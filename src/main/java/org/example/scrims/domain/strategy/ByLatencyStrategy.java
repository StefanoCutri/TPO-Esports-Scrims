package org.example.scrims.domain.strategy;

import org.example.scrims.domain.model.Scrim;
import org.example.scrims.domain.model.Usuario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ByLatencyStrategy implements MatchmakingStrategy {

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos, Scrim scrim) {
        return List.of();
    }

    @Override
    public List<Usuario> ordenar(List<Usuario> usuarios) {
        return usuarios.stream()
                .sorted(Comparator.comparingInt(Usuario::getPing)) // menor ping = mejor
                .collect(Collectors.toList());
    }
}
