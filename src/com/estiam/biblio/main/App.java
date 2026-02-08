package com.estiam.biblio.main;

import java.util.List;

import com.estiam.biblio.dao.LivreDAO;
import com.estiam.biblio.model.Livre;

public class App {
    public static void main(String[] args) {
        LivreDAO livreDAO = new LivreDAO();

        // 2.1 Test Lister
        System.out.println("--- Liste des livres ---");
        livreDAO.afficherListePropre();

        // 2.2 Test Filtrage Streams
        System.out.println("\n--- Filtrage par genre (ex: 'Roman') ---");
        List<Livre> romans = livreDAO.filtrerParGenre("Roman");
        romans.forEach(l -> System.out.println(l.getTitre_l()));

        // 2.3 Test Emprunt
        System.out.println("\n--- Test Emprunt ---");
        livreDAO.emprunterLivre("10", 1); 
    }
}