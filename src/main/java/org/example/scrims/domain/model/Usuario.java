package org.example.scrims.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private String username;
    private int mmr;
    private Rol rolPreferido;
    private final Set<Rol> rolesFlex = new HashSet<>(); // opcionales
    private Equipo equipo;
    private int ping;
    private int reputacion = 100;

    public Usuario(String username, int mmr) {
        this.username = username;
        this.mmr = mmr;
    }

    public String getUsername() { return username; }
    public int getMmr() { return mmr; }

    public Rol getRolPreferido() { return rolPreferido; }
    public void setRolPreferido(Rol rol) { this.rolPreferido = rol; }

    public Set<Rol> getRolesFlex() { return rolesFlex; }
    public void addRolFlex(Rol r) { this.rolesFlex.add(r); }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }

    public int getPing() { return ping; }
    public void setPing(int ping) { this.ping = ping; }

    public int getReputacion() { return reputacion; }
    public void setReputacion(int reputacion) { this.reputacion = reputacion; }

}