package com.estiam.biblio.model;

public class Edition {
    private int id_ed;
    private String nom_ed;

    public Edition(int id_ed, String nom_ed) {
        this.id_ed = id_ed;
        this.nom_ed = nom_ed;
    }

    // Getters et Setters
    public int getId_ed() { return id_ed; }
    public void setId_ed(int id_ed) { this.id_ed = id_ed; }
    public String getNom_ed() { return nom_ed; }
    public void setNom_ed(String nom_ed) { this.nom_ed = nom_ed; }
}