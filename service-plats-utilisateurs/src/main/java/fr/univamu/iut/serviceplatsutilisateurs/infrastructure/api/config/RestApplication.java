package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.config;

import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.PlatRepositoryInterface;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.UtilisateurRepositoryInterface;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.controller.PlatResource;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.controller.UtilisateurResource;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql.PlatRepositoryMysql;
import fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql.UtilisateurRepositoryMysql;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Configuration de l'application JAX-RS et point d'entrée pour l'API REST.
 * Cette classe configure le chemin racine de l'API (/api), enregistre les ressources REST,
 * et gère l'injection de dépendances CDI pour les repositories.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
@ApplicationPath("/api")
@ApplicationScoped
public class RestApplication extends Application {

    /** Configuration de l'application chargée au démarrage */
    private Properties config;

    /**
     * Constructeur. Charge la configuration depuis config.properties.
     */
    public RestApplication() {
        this.config = loadConfig();
    }

    /**
     * Enregistre les classes de ressources REST auprès de JAX-RS.
     *
     * @return un ensemble contenant les classes des contrôleurs REST
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PlatResource.class);
        classes.add(UtilisateurResource.class);
        return classes;
    }

    /**
     * Charge les propriétés de configuration depuis le fichier config.properties
     * présent dans le classpath (src/main/resources).
     *
     * @return un objet Properties contenant la configuration
     * @throws RuntimeException si le fichier config.properties n'est pas trouvé ou en cas d'erreur de lecture
     */
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

    /**
     * Produit une instance du repository des plats pour l'injection dans les contrôleurs.
     * Cette méthode est appelée une seule fois au démarrage de l'application (@ApplicationScoped).
     *
     * @return une instance de PlatRepositoryMysql configurée avec les paramètres BD
     * @throws RuntimeException en cas d'erreur de connexion à la base de données
     */
    @Produces
    @ApplicationScoped
    public PlatRepositoryInterface openPlatDb() {
        try {
            return new PlatRepositoryMysql(
                    config.getProperty("db.url"),
                    config.getProperty("db.user"),
                    config.getProperty("db.pwd")
            );
        } catch (Exception e) {
            throw new RuntimeException("Echec connexion MySQL (Plats) : " + e.getMessage());
        }
    }

    /**
     * Produit une instance du repository des utilisateurs pour l'injection dans les contrôleurs.
     * Cette méthode est appelée une seule fois au démarrage de l'application (@ApplicationScoped).
     *
     * @return une instance de UtilisateurRepositoryMysql configurée avec les paramètres BD
     * @throws RuntimeException en cas d'erreur de connexion à la base de données
     */
    @Produces
    @ApplicationScoped
    public UtilisateurRepositoryInterface openUserDb() {
        try {
            return new UtilisateurRepositoryMysql(
                    config.getProperty("db.url"),
                    config.getProperty("db.user"),
                    config.getProperty("db.pwd")
            );
        } catch (Exception e) {
            throw new RuntimeException("Echec connexion MySQL (Users) : " + e.getMessage());
        }
    }

    /**
     * Libère les ressources du repository des plats à la fermeture de l'application.
     * Méthode appelée automatiquement par CDI.
     *
     * @param repo le repository des plats à fermer
     */
    public void closePlat(@Disposes PlatRepositoryInterface repo) {
        if (repo != null) repo.close();
    }

    /**
     * Libère les ressources du repository des utilisateurs à la fermeture de l'application.
     * Méthode appelée automatiquement par CDI.
     *
     * @param repo le repository des utilisateurs à fermer
     */
    public void closeUser(@Disposes UtilisateurRepositoryInterface repo) {
        if (repo != null) repo.close();
    }
}