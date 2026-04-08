package fr.univamu.iut.serviceplatsutilisateurs.domain.repository;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Plat;
import java.util.List;

/**
 * Interface définissant le contrat de stockage et d'accès aux plats.
 * Suit le patron Repository pour isoler la couche métier des détails
 * techniques de persistance. Toute implémentation doit gérer les opérations
 * CRUD (Create, Read, Update, Delete) sur les plats.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public interface PlatRepositoryInterface {

    /**
     * Récupère la liste complète des plats stockés.
     *
     * @return une liste de tous les plats disponibles,
     *         ou une liste vide si aucun plat n'existe
     */
    List<Plat> findAll();

    /**
     * Récupère un plat spécifique par son identifiant.
     *
     * @param id l'identifiant du plat à rechercher
     * @return le plat correspondant, ou null si non trouvé
     */
    Plat findById(int id);

    /**
     * Enregistre un nouveau plat ou met à jour un plat existant.
     *
     * @param plat l'objet plat à enregistrer
     * @return true si l'opération a réussi, false sinon
     */
    boolean save(Plat plat);

    /**
     * Supprime un plat par son identifiant.
     *
     * @param id l'identifiant du plat à supprimer
     * @return true si la suppression a réussi, false si le plat n'existe pas
     */
    boolean delete(int id);

    /**
     * Libère les ressources utilisées par ce repository (comme les connexions BD).
     */
    void close();
}