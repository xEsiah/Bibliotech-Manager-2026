package com.estiam.biblio.model;

public class Pays {
    private int id_p;
    private String nom_p;

    public Pays(int id_p, String nom_p) {
        this.id_p = id_p;
        this.nom_p = nom_p;
    }

    // Getters et Setters
    public int getId_p() { return id_p; }
    public void setId_p(int id_p) { this.id_p = id_p; }
    public String getNom_p() { return nom_p; }
    public void setNom_p(String nom_p) { this.nom_p = nom_p; }
}