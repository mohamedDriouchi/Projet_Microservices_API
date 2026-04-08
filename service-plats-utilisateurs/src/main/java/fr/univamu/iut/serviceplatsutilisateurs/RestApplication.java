package fr.univamu.iut.serviceplatsutilisateurs;

import fr.univamu.iut.serviceplatsutilisateurs.resource.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class RestApplication extends Application {

    private static final String URL = "jdbc:mariadb://mysql-mohamed-d.alwaysdata.net/mohamed-d_projet_api";
    private static final String USER = "mohamed-d_api";
    private static final String PWD = "projet-api";

    @Produces
    @ApplicationScoped
    public PlatRepositoryInterface openPlatDb() {
        try {
            return new PlatRepositoryMariadb(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Echec connexion MariaDB (Plats) : " + e.getMessage());
        }
    }

    @Produces
    @ApplicationScoped
    public UtilisateurRepositoryInterface openUserDb() {
        try {
            return new UtilisateurRepositoryMariadb(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Echec connexion MariaDB (Users) : " + e.getMessage());
        }
    }

    public void closePlat(@Disposes PlatRepositoryInterface repo) { if (repo != null) repo.close(); }
    public void closeUser(@Disposes UtilisateurRepositoryInterface repo) { if (repo != null) repo.close(); }
}