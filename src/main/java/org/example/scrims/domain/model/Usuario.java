package org.example.scrims.domain.model;

public class Usuario {
    private String username;
    private int mmr;

    public Usuario(String username, int mmr) {
        this.username = username;
        this.mmr = mmr;
    }

    public String getUsername() { return username; }
    public int getMmr() { return mmr; }
}