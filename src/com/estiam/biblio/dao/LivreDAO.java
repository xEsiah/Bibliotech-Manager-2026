package com.estiam.biblio.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.estiam.biblio.config.DatabaseManager;
import com.estiam.biblio.model.Genre;
import com.estiam.biblio.model.Livre;

public class LivreDAO {

    // 2.1.a Lister les livres
    public List<Livre> getAllLivres() {
        List<Livre> livres = new ArrayList<>();
        // Jointure pour récupérer le libellé du genre (typelivre)
        String sql = "SELECT l.*, t.libelle_t FROM Livre l " +
                     "JOIN typelivre t ON l.id_t = t.id_t";
        
                     
        Connection conn = DatabaseManager.getInstance().getConnection();
    
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Genre genre = new Genre(rs.getInt("id_t"), rs.getString("libelle_t"));
                Livre livre = new Livre(
                    rs.getInt("id_l"),
                    rs.getString("titre_l"),
                    LocalDate.of(rs.getInt("annee_l"), 1, 1),
                    rs.getString("resume_l"),
                    genre
                );
                livres.add(livre);
            }
        } catch (SQLException e) { 
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
        return livres;
    }

    // 2.1.b Affichage propre
    public void afficherListePropre() {
        for (Livre l : getAllLivres()) {
            System.out.println("[" + l.getId_l() + "] " + l.getTitre_l() + 
                               " (" + l.getAnneeInt() + ") - Genre: " + l.getGenre().getLibelle_t());
        }
    }

    // 2.2 Filtrage par genre (Streams)
    public List<Livre> filtrerParGenre(String libelleGenre) {
        // Le filtrage se fait en Java, pas via une clause WHERE (2.2.c)
        return getAllLivres().stream()
            .filter(l -> l.getGenre().getLibelle_t().equalsIgnoreCase(libelleGenre))
            .collect(Collectors.toList());
    }

    // 2.3 Emprunter un livre
    public void emprunterLivre(String refExemplaire, int idInscrit) {
    String sql = "INSERT INTO Emprunt (ref_e, id_i, date_em, delais_em) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = DatabaseManager.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, refExemplaire);
        pstmt.setInt(2, idInscrit);
        pstmt.setDate(3, Date.valueOf(LocalDate.now())); 
        // Délai d'emprunt par défaut (ex: 21 jours)
        pstmt.setInt(4, 21); 
        
        pstmt.executeUpdate();
        System.out.println("Emprunt enregistré avec un délai de 21 jours.");
    } catch (SQLException e) { 
        System.out.println("Erreur lors de l'emprunt : " + e.getMessage());
    }
}
}