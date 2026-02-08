package com.estiam.biblio.model;

public class Rediger {
    private Auteur auteur;
    private Livre livre;

    public Rediger(Auteur auteur, Livre livre) {
        this.auteur = auteur;
        this.livre = livre;
    }

    // Getters et Setters
    public Auteur getAuteur() { return auteur; }
    public void setAuteur(Auteur auteur) { this.auteur = auteur; }
    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
}