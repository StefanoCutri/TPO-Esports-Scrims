package org.example.scrims.domain.model;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Equipo {
    private final String lado;                 // "A" o "B"
    private final int capacidad;               // cupos del lado
    private final Map<Usuario,Rol> jugadores = new LinkedHashMap<>();

    public Equipo(String lado, int capacidad) {
        this.lado = lado;
        this.capacidad = capacidad;
    }

    public String getLado() { return lado; }
    public int getCapacidad() { return capacidad; }
    public int size() { return jugadores.size(); }
    public Map<Usuario, Rol> getJugadores() { return jugadores; }
    public boolean estaLleno() { return size() >= capacidad; }
    public boolean contiene(Usuario u) { return jugadores.containsKey(u); }

    public void agregar(Usuario u, Rol rol) {
        if (estaLleno()) throw new IllegalStateException("No hay más lugar en el equipo " + lado);
        jugadores.put(u, rol);
    }

    public int cantidadPorRol(Rol rol) {
        int c = 0;
        for (Rol r : jugadores.values()) if (r == rol) c++;
        return c;
    }
}
