package com.estiam.biblio.model;

// Correspond à la table typelivre
public class Genre { 
    private int id_t; 
    private String libelle_t;

    public Genre(int id_t, String libelle_t) {
        this.id_t = id_t;
        this.libelle_t = libelle_t;
    }

    // Getters et Setters
    public int getId_t() { return id_t; }
    public void setId_t(int id_t) { this.id_t = id_t; }
    public String getLibelle_t() { return libelle_t; }
    public void setLibelle_t(String libelle_t) { this.libelle_t = libelle_t; }
}