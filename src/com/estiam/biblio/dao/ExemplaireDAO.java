package com.estiam.biblio.dao;

import com.estiam.biblio.config.DatabaseManager;
import com.estiam.biblio.config.I18nManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplaireDAO {

    public List<String> getExemplairesPourLivre(int idLivre) {
        List<String> resultats = new ArrayList<>();

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
                    // Récupération dynamique du mot "Editeur"
                    String labelEditeur = I18nManager.get("label.publisher");
                    resultats.add(ref + " - " + labelEditeur + ": " + editeur + "\n");
                }
            }
        } catch (SQLException e) {
            System.out.println(I18nManager.get("error.sql") + " : " + e.getMessage());
        }
        return resultats;
    }
}