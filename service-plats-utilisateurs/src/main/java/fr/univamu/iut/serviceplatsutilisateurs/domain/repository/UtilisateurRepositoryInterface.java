package fr.univamu.iut.serviceplatsutilisateurs.domain.repository;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Utilisateur;
import java.util.List;

/**
 * Interface définissant le contrat de stockage et d'accès aux utilisateurs.
 * Suit le patron Repository pour isoler la couche métier des détails
 * techniques de persistance. Toute implémentation doit gérer les opérations
 * CRUD (Create, Read, Update, Delete) sur les utilisateurs.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public interface UtilisateurRepositoryInterface {

    /**
     * Récupère la liste complète des utilisateurs stockés.
     *
     * @return une liste de tous les utilisateurs disponibles,
     *         ou une liste vide si aucun utilisateur n'existe
     */
    List<Utilisateur> findAll();

    /**
     * Récupère un utilisateur spécifique par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à rechercher
     * @return l'utilisateur correspondant, ou null si non trouvé
     */
    Utilisateur findById(int id);

    /**
     * Enregistre un nouvel utilisateur ou met à jour un utilisateur existant.
     *
     * @param user l'objet utilisateur à enregistrer
     * @return true si l'opération a réussi, false sinon
     */
    boolean save(Utilisateur user);

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false si l'utilisateur n'existe pas
     */
    boolean delete(int id);

    /**
     * Libère les ressources utilisées par ce repository (comme les connexions BD).
     */
    void close();
}