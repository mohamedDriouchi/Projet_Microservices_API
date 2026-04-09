package fr.univamu.iut.servicemenus.infrastructure.api.config;

import fr.univamu.iut.servicemenus.domain.repository.MenuRepositoryInterface;
import fr.univamu.iut.servicemenus.infrastructure.api.controller.MenuResource;
import fr.univamu.iut.servicemenus.infrastructure.persistence.mysql.MenuRepositoryMysql;
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
 * @author Service Menus
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
        classes.add(MenuResource.class);
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
     * Produit une instance du repository des menus pour l'injection dans les contrôleurs.
     * Cette méthode est appelée une seule fois au démarrage de l'application (@ApplicationScoped).
     *
     * @return une instance de MenuRepositoryMysql configurée avec les paramètres BD
     * @throws RuntimeException en cas d'erreur de connexion à la base de données
     */
    @Produces
    @ApplicationScoped
    public MenuRepositoryInterface openMenuDb() {
        try {
            return new MenuRepositoryMysql(
                    config.getProperty("db.url"),
                    config.getProperty("db.user"),
                    config.getProperty("db.pwd")
            );
        } catch (Exception e) {
            throw new RuntimeException("Echec connexion MySQL (Menus) : " + e.getMessage());
        }
    }

    /**
     * Libère les ressources du repository des menus à la fermeture de l'application.
     * Méthode appelée automatiquement par CDI.
     *
     * @param repo le repository des menus à fermer
     */
    public void closeMenu(@Disposes MenuRepositoryInterface repo) {
        if (repo != null) repo.close();
    }
}

