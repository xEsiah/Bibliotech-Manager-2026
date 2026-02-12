package com.estiam.biblio.dao;

import com.estiam.biblio.config.DatabaseManager;
import com.estiam.biblio.config.I18nManager;
import com.estiam.biblio.model.Auteur;
import com.estiam.biblio.model.Genre;
import com.estiam.biblio.model.Livre;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LivreDAO {

    public List<Livre> getAllLivres() {
        Map<Integer, Livre> livreMap = new HashMap<>();
        // Requête corrigée avec ta structure BDD
        String sql = "SELECT l.id_l, l.titre_l, l.annee_l, l.resume_l, " +
                     "t.id_t, t.libelle_t, " +
                     "a.id_a, a.nom_a, a.prenom_a " +
                     "FROM livre l " +
                     "JOIN typelivre t ON l.id_t = t.id_t " +
                     "LEFT JOIN rediger r ON l.id_l = r.id_l " + 
                     "LEFT JOIN auteur a ON r.id_a = a.id_a";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idLivre = rs.getInt("id_l");
                Livre livre = livreMap.get(idLivre);
                if (livre == null) {
                    Genre genre = new Genre(rs.getInt("id_t"), rs.getString("libelle_t"));
                    LocalDate dateParution = LocalDate.of(rs.getInt("annee_l"), 1, 1);
                    livre = new Livre(idLivre, rs.getString("titre_l"), dateParution, rs.getString("resume_l"), genre);
                    livreMap.put(idLivre, livre);
                }
                int idAuteur = rs.getInt("id_a");
                if (idAuteur > 0) {
                    Auteur auteur = new Auteur(idAuteur, rs.getString("nom_a"), rs.getString("prenom_a"), null, null);
                    livre.addAuteur(auteur);
                }
            }
        } catch (SQLException e) { 
            System.out.println(I18nManager.get("error.sql") + " : " + e.getMessage());
        }
        return new ArrayList<>(livreMap.values());
    }

    public void afficherListePropre() {
        List<Livre> livres = getAllLivres();
        if (livres.isEmpty()) {
            System.out.println(I18nManager.get("book.none"));
            return;
        }
        for (Livre l : livres) {
            String labelAuteur = I18nManager.get("label.author"); 
            System.out.println("[" + l.getId_l() + "] " + l.getTitre_l() + 
                               " (" + l.getAnneeInt() + ") - " + labelAuteur + " : " + l.getNomsAuteurs());
        }
    }
    
    public List<Livre> filtrerParGenre(String genreLibelle) {
        return getAllLivres().stream()
                .filter(l -> l.getGenre().getLibelle_t().equalsIgnoreCase(genreLibelle))
                .collect(Collectors.toList());
    }
}