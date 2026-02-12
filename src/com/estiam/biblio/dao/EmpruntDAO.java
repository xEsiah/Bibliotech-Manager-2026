package com.estiam.biblio.dao;

import com.estiam.biblio.config.DatabaseManager;
import com.estiam.biblio.config.I18nManager;
import com.estiam.biblio.exception.LivreIndisponibleException;
import com.estiam.biblio.exception.SaisieInvalideException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmpruntDAO {

    public void enregistrerEmprunt(String refExemplaire, int idInscrit, int dureeJours, String titreLivre) 
            throws LivreIndisponibleException, SaisieInvalideException { 
        if (!verifierAdherentExiste(idInscrit)) {
            throw new SaisieInvalideException(I18nManager.get("error.member.notfound", idInscrit));
        }
        verifierDisponibilite(refExemplaire);

        String sql = "INSERT INTO emprunt (ref_e, id_i, date_em, delais_em) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, refExemplaire);
            pstmt.setInt(2, idInscrit);
            pstmt.setDate(3, Date.valueOf(LocalDate.now())); 
            pstmt.setInt(4, dureeJours); 
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println(I18nManager.get("loan.success"));
                ecrireLog(refExemplaire, idInscrit, dureeJours, titreLivre);
            }
        } catch (SQLException e) { 
            System.out.println(I18nManager.get("error.sql") + " : " + e.getMessage());
        }
    }

    private boolean verifierAdherentExiste(int idInscrit) {
        String sql = "SELECT id_i FROM inscrit WHERE id_i = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idInscrit);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erreur vérification adhérent : " + e.getMessage());
            return false;
        }
    }

    private void verifierDisponibilite(String refExemplaire) throws LivreIndisponibleException {
        String sql = "SELECT date_em, delais_em FROM emprunt WHERE ref_e = ? ORDER BY date_em DESC LIMIT 1";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, refExemplaire);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Date dateEmpruntSql = rs.getDate("date_em");
                    int delais = rs.getInt("delais_em");
                    
                    if (dateEmpruntSql != null) {
                        LocalDate dateEmprunt = dateEmpruntSql.toLocalDate();
                        LocalDate dateRetourPrevue = dateEmprunt.plusDays(delais);
                        
                        if (dateRetourPrevue.isAfter(LocalDate.now())) {
                            throw new LivreIndisponibleException(
                                I18nManager.get("error.book.unavailable", refExemplaire, dateRetourPrevue)
                            );
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(I18nManager.get("error.sql") + " : " + e.getMessage());
        }
    }

    private void ecrireLog(String ref, int idInscrit, int duree, String titre) {
        try {
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            String logLine = I18nManager.get("loan.log.msg", dateStr, titre, ref, idInscrit, duree) + System.lineSeparator();
            Files.write(Paths.get("journal.log"), logLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Log Error: " + e.getMessage());
        }
    }
}