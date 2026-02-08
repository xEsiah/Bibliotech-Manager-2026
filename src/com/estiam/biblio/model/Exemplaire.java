package com.estiam.biblio.model;

public class Exemplaire {
    private String ref_e; 
    // Toutes les entrées sont numériques en BdD bien que (VARCHAR(20)) alors on utilise String pour respecter le type
    private Edition edition;
    private Livre livre;

    public Exemplaire(String ref_e, Edition edition, Livre livre) {
        this.ref_e = ref_e;
        this.edition = edition;
        this.livre = livre;
    }

    // Getters et Setters
    public String getRef_e() { return ref_e; }
    public void setRef_e(String ref_e) { this.ref_e = ref_e; }
    public Edition getEdition() { return edition; }
    public void setEdition(Edition edition) { this.edition = edition; }
    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
}