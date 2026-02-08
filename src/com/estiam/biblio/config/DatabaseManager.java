package com.estiam.biblio.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {
    // Instance unique (Partie 1.2.b)
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    private Connection connection;

    // Constructeur privé (Partie 1.2.b)
    private DatabaseManager() {
        try {
            Properties props = new Properties();
            // Chargement depuis le fichier de configuration db.properties (Partie 1.2.c)
            InputStream input = getClass().getClassLoader().getResourceAsStream("ressources/db.properties");
            if (input == null) {
                System.out.println("Erreur : Fichier db.properties introuvable.");
                return;
            }
            props.load(input);
            
            // Établissement de la connexion
            this.connection = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
            System.out.println("Connexion à la BDD réussie.");
        } catch (Exception e) {
            System.out.println("Erreur de connexion à la BDD : " + e.getMessage());
        }
    }

    public static DatabaseManager getInstance() { return INSTANCE; }
    public Connection getConnection() { return connection; }
}