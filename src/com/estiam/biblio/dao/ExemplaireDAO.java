package com.estiam.biblio.dao;

import com.estiam.biblio.config.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplaireDAO {

    public List<String> getExemplairesPourLivre(int idLivre) {
        List<String> resultats = new ArrayList<>();

        // Afficher le nom de l'éditeur pour chaque exemplaire (2.3.c)
        String sql = "SELECT ex.ref_e, ed.nom_ed " +
                     "FROM exemplaire ex " +
                     "JOIN edition ed ON ex.id_ed = ed.id_ed " +
                     "WHERE ex.id_l = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idLivre);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ref = rs.getString("ref_e");
                    String editeur = rs.getString("nom_ed");
                    resultats.add(ref + " - Editeur: " + editeur + "\n");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur récupération exemplaires : " + e.getMessage());
        }
        return resultats;
    }
}