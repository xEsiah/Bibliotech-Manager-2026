package com.estiam.biblio.main;

import com.estiam.biblio.config.I18nManager;
import com.estiam.biblio.dao.EmpruntDAO;
import com.estiam.biblio.dao.ExemplaireDAO;
import com.estiam.biblio.dao.LivreDAO;
import com.estiam.biblio.exception.LivreIndisponibleException;
import com.estiam.biblio.exception.SaisieInvalideException;
import com.estiam.biblio.model.Livre;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // CHOIX LANGUE
        System.out.println("Select language / Choisissez la langue :");
        System.out.println("1. English");
        System.out.println("2. Français");
        System.out.print("> ");
        
        String lang = "fr";
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            if (choice == 1) lang = "en";
        }
        scanner.nextLine();

        I18nManager.setLocale(lang);

        LivreDAO livreDAO = new LivreDAO();
        ExemplaireDAO exemplaireDAO = new ExemplaireDAO();
        EmpruntDAO empruntDAO = new EmpruntDAO();
        
        boolean continuer = true;

        // --- MENU PRINCIPAL ---
        System.out.println(I18nManager.get("menu.title"));

        while (continuer) {
            System.out.println("\n-----------------------------------");
            System.out.println(I18nManager.get("menu.option1"));
            System.out.println(I18nManager.get("menu.option2"));
            System.out.println(I18nManager.get("menu.option3"));
            System.out.println(I18nManager.get("menu.option4"));
            System.out.println(I18nManager.get("menu.option5"));
            System.out.println(I18nManager.get("menu.option0"));
            System.out.println("-----------------------------------");
            System.out.print(I18nManager.get("menu.choice"));

            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();
                scanner.nextLine(); 

                switch (choix) {
                    case 1: // LISTER
                        System.out.println("\n" + I18nManager.get("book.list"));
                        livreDAO.afficherListePropre();
                        break;

                    case 2: // FILTRER
                        System.out.print(I18nManager.get("book.filter.prompt"));
                        String genre = scanner.nextLine();
                        List<Livre> livresFiltres = livreDAO.filtrerParGenre(genre);
                        
                        if (livresFiltres.isEmpty()) {
                            System.out.println(I18nManager.get("book.filter.none", genre));
                        } else {
                            System.out.println("\n" + I18nManager.get("book.filter.found", genre));
                            for (Livre l : livresFiltres) {
                                System.out.println("- " + l.getTitre_l() + " (" + l.getAnneeInt() + ")");
                            }
                        }
                        break;

                    case 3: // EMPRUNTER 
                        gererEmprunt(scanner, exemplaireDAO, empruntDAO);
                        break;

                    case 4: // AFFICHER LES STATISTIQUES DE LIVRES PAR GENRE
                        livreDAO.afficherStatistiques();
                        break;

                    case 5: // EXPORTER LA LISTE DES LIVRES EN CSV
                        livreDAO.exporterCSV();
                        break;

                    case 0:
                        System.out.println(I18nManager.get("msg.bye"));
                        continuer = false;
                        break;

                    default:
                        System.out.println(I18nManager.get("msg.error.choice"));
                }
            } else {
                System.out.println(I18nManager.get("msg.error.number"));
                scanner.next(); 
            }
        }
        scanner.close();
    }

    private static void gererEmprunt(Scanner scanner, ExemplaireDAO exemplaireDAO, EmpruntDAO empruntDAO) {
        System.out.println("\n" + I18nManager.get("loan.title"));
        System.out.print(I18nManager.get("loan.prompt.id"));
        
        if (scanner.hasNextInt()) {
            int idLivre = scanner.nextInt();
            List<String> refsDisponibles = exemplaireDAO.getExemplairesPourLivre(idLivre);

            if (refsDisponibles.isEmpty()) {
                System.out.println(I18nManager.get("loan.error.unavailable"));
            } else {
                System.out.println(I18nManager.get("loan.available"));
                for (String s : refsDisponibles) {
                    System.out.print(s); 
                }
                
                System.out.print("\n" + I18nManager.get("loan.prompt.ref"));
                String refExemplaire = scanner.next();

                boolean refValide = refsDisponibles.stream()
                    .anyMatch(line -> line.contains(refExemplaire));
                
                if (refValide) {                
                    System.out.print(I18nManager.get("loan.prompt.user"));
                    if(scanner.hasNextInt()) {
                        int idInscrit = scanner.nextInt();
                        try {
                            System.out.print(I18nManager.get("loan.prompt.duration"));
                            if (!scanner.hasNextInt()) {
                                scanner.next();
                                throw new SaisieInvalideException(I18nManager.get("error.duration.invalid"));
                            }
                            int duree = scanner.nextInt();
                            if (duree <= 0) {
                                throw new SaisieInvalideException(I18nManager.get("error.duration.negative"));
                            }
                            empruntDAO.enregistrerEmprunt(refExemplaire, idInscrit, duree, "Livre ID " + idLivre);
                            
                        } catch (SaisieInvalideException e) {
                            System.out.println(e.getMessage());
                            System.out.println(I18nManager.get("msg.operation.cancelled"));
                            
                        } catch (LivreIndisponibleException e) {
                            System.out.println(e.getMessage());
                            System.out.println(I18nManager.get("msg.operation.cancelled"));
                        }    
                    } else {
                         System.out.println(I18nManager.get("msg.error.number"));
                         scanner.next();
                    }
                } else {
                    System.out.println(I18nManager.get("loan.error.ref"));
                }
            }
        } else {
            System.out.println(I18nManager.get("loan.error.id"));
            scanner.next(); 
        }
    }
}