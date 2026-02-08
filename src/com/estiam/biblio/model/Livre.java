package com.estiam.biblio.model;

import java.time.LocalDate;

public class Livre {
    private int id_l;
    private String titre_l;
    private LocalDate annee_l; // gérée avec LocalDate (Partie 1.1.c)
    private String resume_l;
    private Genre genre; // Relation avec une autre classe, objet imbriqué

    public Livre(int id_l, String titre_l, LocalDate annee_l, String resume_l, Genre genre) {
        this.id_l = id_l;
        this.titre_l = titre_l;
        this.annee_l = annee_l;
        this.resume_l = resume_l;
        this.genre = genre;
    }

    // Getters et Setters
    public int getId_l() { return id_l; }
    public void setId_l(int id_l) { this.id_l = id_l; }
    public String getTitre_l() { return titre_l; }
    public void setTitre_l(String titre_l) { this.titre_l = titre_l; }
    public LocalDate getAnnee_l() { return annee_l; }
    public void setAnnee_l(LocalDate annee_l) { this.annee_l = annee_l; }
    public String getResume_l() { return resume_l; }
    public void setResume_l(String resume_l) { this.resume_l = resume_l; }
    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public int getAnneeInt() { return annee_l.getYear(); }
}