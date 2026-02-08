package com.estiam.biblio.model;

import java.time.LocalDate;

public class Emprunt {
    private Exemplaire exemplaire;
    private Inscrit inscrit;
    private LocalDate date_emprunt;

    public Emprunt(Exemplaire exemplaire, Inscrit inscrit, LocalDate date_emprunt) {
        this.exemplaire = exemplaire;
        this.inscrit = inscrit;
        this.date_emprunt = date_emprunt;
    }

    // Getters et Setters
    public Exemplaire getExemplaire() { return exemplaire; }
    public void setExemplaire(Exemplaire exemplaire) { this.exemplaire = exemplaire; }
    public Inscrit getInscrit() { return inscrit; }
    public void setInscrit(Inscrit inscrit) { this.inscrit = inscrit; }
    public LocalDate getDate_emprunt() { return date_emprunt; }
    public void setDate_emprunt(LocalDate date_emprunt) { this.date_emprunt = date_emprunt; }
}