package com.estiam.biblio.main;

import com.estiam.biblio.dao.EmpruntDAO;
import com.estiam.biblio.dao.ExemplaireDAO;
import com.estiam.biblio.dao.LivreDAO;
import com.estiam.biblio.model.Livre;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        LivreDAO livreDAO = new LivreDAO();
        ExemplaireDAO exemplaireDAO = new ExemplaireDAO();
        EmpruntDAO empruntDAO = new EmpruntDAO();
        
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        System.out.println("\n\n\n=== BIBLIOTECH MANAGER 2026 ===");

        // BOUCLE PRINCIPALE DU MENU
        while (continuer) {
            System.out.println("\n-----------------------------------");
            System.out.println("MENU PRINCIPAL :");
            System.out.println("1. Lister tous les livres");
            System.out.println("2. Rechercher des livres par Genre");
            System.out.println("3. Emprunter un livre");
            System.out.println("0. Quitter");
            System.out.println("-----------------------------------");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        // --- LISTER ---
                        System.out.println("\n--- Catalogue complet ---");
                        livreDAO.afficherListePropre();
                        break;

                    case 2:
                        // --- FILTRER ---
                        System.out.print("Genres: Roman, Biographie, Conte, Science Fiction, Policier, Fantaisie, Manga,\n Historique, Poésie, Essai, Guide, Scenario, Témoignage, Horreur, Fantastique\n");
                        System.out.print("Entrez le genre recherché: ");
                        String genre = scanner.nextLine();
                        List<Livre> livresFiltres = livreDAO.filtrerParGenre(genre);
                        
                        if (livresFiltres.isEmpty()) {
                            System.out.println("Aucun livre trouvé pour le genre : " + genre);
                        } else {
                            System.out.println("\n--- Livres du genre : " + genre + " ---");
                            for (Livre l : livresFiltres) {
                                System.out.println("- " + l.getTitre_l() + " (" + l.getAnneeInt() + ")");
                            }
                        }
                        break;

                    case 3:
                        // --- EMPRUNTER ---
                        gererEmprunt(scanner, exemplaireDAO, empruntDAO);
                        break;

                    case 0:
                        System.out.println("Fermeture de l'application. Au revoir !");
                        continuer = false;
                        break;

                    default:
                        System.out.println("Choix invalide, veuillez recommencer.");
                }
            } else {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
            }
        }
        scanner.close();
    }
    private static void gererEmprunt(Scanner scanner, ExemplaireDAO exemplaireDAO, EmpruntDAO empruntDAO) {
        System.out.println("\n--- Emprunter un livre ---");
        System.out.print("Entrez l'ID du livre à emprunter : ");
        
        if (scanner.hasNextInt()) {
            int idLivre = scanner.nextInt();

            List<String> refsDisponibles = exemplaireDAO.getExemplairesPourLivre(idLivre);

            if (refsDisponibles.isEmpty()) {
                System.out.println("Désolé, aucun exemplaire disponible pour ce livre.");
            } else {
                System.out.println("✅ Exemplaires en rayon :");
                for (String s : refsDisponibles) {
                    System.out.print(s); 
                }
                
                System.out.print("\nCopiez la référence (ref_e) exacte : ");
                String refExemplaire = scanner.next();

                boolean refValide = refsDisponibles.stream()
                    .anyMatch(line -> line.contains(refExemplaire));
                
                if (refValide) {                
                    System.out.print("Entrez l'ID de l'adhérent (Inscrit) : ");
                    if(scanner.hasNextInt()) {
                        int idInscrit = scanner.nextInt();
                        empruntDAO.enregistrerEmprunt(refExemplaire, idInscrit);
                    } else {
                         System.out.println("ID Adhérent invalide.");
                    }
                } else {
                    System.out.println("Erreur : Cette référence ne correspond pas au livre choisi.");
                }
            }
        } else {
            System.out.println("Erreur : ID de livre invalide.");
            scanner.next();
        }
    }
}