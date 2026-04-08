package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.config;

import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.PlatRepositoryInterface;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.UtilisateurRepositoryInterface;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql.PlatRepositoryMysql;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql.UtilisateurRepositoryMysql;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.io.InputStream;
import java.util.Properties;

@ApplicationPath("/api")
@ApplicationScoped
public class RestApplication extends Application {

    private Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Fichier config.properties introuvable dans src/main/resources");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de configuration : " + e.getMessage());
        }
        return props;
    }

    @Produces
    @ApplicationScoped
    public PlatRepositoryInterface openPlatDb() {
        Properties props = loadConfig();
        try {
            return new PlatRepositoryMysql(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.pwd")
            );
        } catch (Exception e) {
            throw new RuntimeException("Echec connexion MySQL (Plats) : " + e.getMessage());
        }
    }

    @Produces
    @ApplicationScoped
    public UtilisateurRepositoryInterface openUserDb() {
        Properties props = loadConfig();
        try {
            return new UtilisateurRepositoryMysql(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.pwd")
            );
        } catch (Exception e) {
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