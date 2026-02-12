package com.estiam.biblio.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18nManager {
    private static ResourceBundle bundle;

    // Charge la langue demandée (fr ou en)
    public static void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("ressources.messages", locale);
    }

    // Récupère un texte simple
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "???" + key + "???";
        }
    }

    // Récupère un texte avec paramètres (ex: "Livre {0} emprunté")
    public static String get(String key, Object... params) {
        try {
            return MessageFormat.format(bundle.getString(key), params);
        } catch (Exception e) {
            return "???" + key + "???";
        }
    }
}