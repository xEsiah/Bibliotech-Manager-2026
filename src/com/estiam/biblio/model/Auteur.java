package com.estiam.biblio.model;

import java.time.LocalDate;
import java.time.Period;

public class Auteur {
    private int id_a;
    private String nom_a;
    private String prenom_a;
    private LocalDate date_naissance_a;
    private Pays pays; // Relation avec une autre classe, objet imbriqué

    public Auteur(int id_a, String nom_a, String prenom_a, LocalDate date_naissance_a, Pays pays) {
        this.id_a = id_a;
        this.nom_a = nom_a;
        this.prenom_a = prenom_a;
        this.date_naissance_a = date_naissance_a;
        this.pays = pays;
    }

    // Getters et Setters
    public int getId_a() { return id_a; }
    public void setId_a(int id_a) { this.id_a = id_a; }
    public String getNom_a() { return nom_a; }
    public void setNom_a(String nom_a) { this.nom_a = nom_a; }
    public String getPrenom_a() { return prenom_a; }
    public void setPrenom_a(String prenom_a) { this.prenom_a = prenom_a; }
    public LocalDate getDate_naissance_a() { return date_naissance_a; }
    public void setDate_naissance_a(LocalDate date_naissance_a) { this.date_naissance_a = date_naissance_a; }
    public Pays getPays() { return pays; }
    public void setPays(Pays pays) { this.pays = pays; }

    // Méthode de calcul de l'âge
    public int getAge_a() {
        if (this.date_naissance_a == null) return 0;
        return Period.between(this.date_naissance_a, LocalDate.now()).getYears();
    }
}