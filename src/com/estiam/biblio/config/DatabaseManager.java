package com.estiam.biblio.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    private Connection connection;
    private Properties props;

    private DatabaseManager() {
        try {
            props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("ressources/db.properties");
            if (input == null) return;
            props.load(input);
        } catch (Exception e) {
            System.out.println("Erreur config : " + e.getMessage());
        }
    }

    public static DatabaseManager getInstance() { return INSTANCE; }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur reconnexion : " + e.getMessage());
        }
        return connection;
    }
}