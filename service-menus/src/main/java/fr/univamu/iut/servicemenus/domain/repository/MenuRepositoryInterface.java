package fr.univamu.iut.servicemenus.domain.repository;

import fr.univamu.iut.servicemenus.domain.model.Menu;
import java.util.List;

/**
 * Interface définissant le contrat de stockage et d'accès aux menus.
 * Suit le patron Repository pour isoler la couche métier des détails
 * techniques de persistance. Toute implémentation doit gérer les opérations
 * CRUD (Create, Read, Update, Delete) sur les menus.
 *
 * @author Service Menus
 * @version 1.0
 */
public interface MenuRepositoryInterface {

    /**
     * Récupère la liste complète des menus stockés.
     *
     * @return une liste de tous les menus disponibles,
     *         ou une liste vide si aucun menu n'existe
     */
    List<Menu> findAll();

    /**
     * Récupère un menu spécifique par son identifiant.
     *
     * @param id l'identifiant du menu à rechercher
     * @return le menu correspondant, ou null si non trouvé
     */
    Menu findById(int id);

    /**
     * Enregistre un nouveau menu ou met à jour un menu existant.
     *
     * @param menu l'objet menu à enregistrer
     * @return true si l'opération a réussi, false sinon
     */
    boolean save(Menu menu);

    /**
     * Supprime un menu par son identifiant.
     *
     * @param id l'identifiant du menu à supprimer
     * @return true si la suppression a réussi, false si le menu n'existe pas
     */
    boolean delete(int id);

    /**
     * Libère les ressources utilisées par ce repository (comme les connexions BD).
     */
    void close();
}

