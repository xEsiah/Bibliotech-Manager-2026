package com.estiam.biblio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Livre {
    private int id_l;
    private String titre_l;
    private LocalDate annee_l;
    private String resume_l;
    private Genre genre;
    
    // Attribut de relation pour la POO uniquement, rempli par le DAO
    private List<Auteur> auteurs;

    public Livre(int id_l, String titre_l, LocalDate annee_l, String resume_l, Genre genre) {
        this.id_l = id_l;
        this.titre_l = titre_l;
        this.annee_l = annee_l;
        this.resume_l = resume_l;
        this.genre = genre;
        this.auteurs = new ArrayList<>();
    }

    // Gestion des auteurs
    public void addAuteur(Auteur auteur) {
        this.auteurs.add(auteur);
    }

    // Pour l'affichage demandé : "Prénom Nom, Prénom Nom"
    public String getNomsAuteurs() {
        if (auteurs.isEmpty()) return "Auteur Inconnu";
        return auteurs.stream()
                .map(a -> a.getPrenom_a() + " " + a.getNom_a())
                .collect(Collectors.joining(", "));
    }

    // Getters standards
    public int getId_l() { return id_l; }
    public String getTitre_l() { return titre_l; }
    public int getAnneeInt() { return annee_l.getYear(); }
    public Genre getGenre() { return genre; }
}