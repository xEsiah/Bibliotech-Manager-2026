package com.estiam.biblio.model;

public class Inscrit {
    private int id_i;
    private String nom_i;
    private String prenom_i;

    public Inscrit(int id_i, String nom_i, String prenom_i) {
        this.id_i = id_i;
        this.nom_i = nom_i;
        this.prenom_i = prenom_i;
    }

    // Getters et Setters
    public int getId_i() { return id_i; }
    public void setId_i(int id_i) { this.id_i = id_i; }
    public String getNom_i() { return nom_i; }
    public void setNom_i(String nom_i) { this.nom_i = nom_i; }
    public String getPrenom_i() { return prenom_i; }
    public void setPrenom_i(String prenom_i) { this.prenom_i = prenom_i; }
}