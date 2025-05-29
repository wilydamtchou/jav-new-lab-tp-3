package com.m2i.m2ibank.core;

import java.util.List;

public class Client {
    private String id;
    private String name;
    private List<Compte> comptes;

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }

    @Override
    public String toString() {
        return "Client{id='" + id + "', name='" + name + "'}";
    }
}
