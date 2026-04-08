package fr.univamu.iut.serviceplatsutilisateurs;

import fr.univamu.iut.serviceplatsutilisateurs.resource.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuration de l'application REST et Producteurs de dépendances.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class RestApplication extends Application {

    // Récupère les paramètres de connexion depuis les variables d'environnement
    private static final String URL = System.getenv("DB_URL") != null ?
        System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/projet_api?useSSL=false&serverTimezone=UTC";
    private static final String USER = System.getenv("DB_USER") != null ?
        System.getenv("DB_USER") : "root";
    private static final String PWD = System.getenv("DB_PASSWORD") != null ?
        System.getenv("DB_PASSWORD") : "";

    @Produces
    @ApplicationScoped
    public PlatRepositoryInterface openPlatDb() {
        try {
            // On appelle l'implémentation MySQL
            return new PlatRepositoryMysql(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Echec connexion MySQL (Plats) : " + e.getMessage());
        }
    }

    @Produces
    @ApplicationScoped
    public UtilisateurRepositoryInterface openUserDb() {
        try {
            // On appelle l'implémentation MySQL
            return new UtilisateurRepositoryMysql(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Echec connexion MySQL (Users) : " + e.getMessage());
        }
    }

    public void closePlat(@Disposes PlatRepositoryInterface repo) {
        if (repo != null) repo.close();
    }

    public void closeUser(@Disposes UtilisateurRepositoryInterface repo) {
        if (repo != null) repo.close();
    }
}