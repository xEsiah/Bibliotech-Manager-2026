package com.estiam.biblio.dao;

import com.estiam.biblio.config.DatabaseManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmpruntDAO {

    public void enregistrerEmprunt(String refExemplaire, int idInscrit) {
        String sql = "INSERT INTO emprunt (ref_e, id_i, date_em, delais_em) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, refExemplaire);
            pstmt.setInt(2, idInscrit);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.setInt(4, 15); // NE PAS OUBLIER DE CHANGER
            
            pstmt.executeUpdate();
            System.out.println("Emprunt enregistré avec succès !");
            
        } catch (SQLException e) { 
            System.out.println("Erreur lors de l'emprunt : " + e.getMessage());
        }
    }
}