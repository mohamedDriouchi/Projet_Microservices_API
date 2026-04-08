package fr.univamu.iut.serviceplatsutilisateurs.domain.model;

/**
 * Représente un plat du catalogue.
 * Cette classe est une entité du domaine, indépendante de la persistance.
 * Elle encapsule les informations essentielles d'un plat : son identifiant,
 * son nom, sa description et son prix.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public class Plat {
    /** Identifiant unique du plat */
    private int id;

    /** Nom du plat */
    private String nom;

    /** Description détaillée du plat */
    private String description;

    /** Prix du plat en euros */
    private double prix;

    /**
     * Constructeur par défaut. Crée un plat vide.
     */
    public Plat() {
    }

    /**
     * Constructeur avec paramètres.
     * Crée un plat avec tous les attributs initialisés.
     *
     * @param id l'identifiant unique du plat
     * @param nom le nom du plat
     * @param description la description du plat
     * @param prix le prix du plat
     */
    public Plat(int id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    /**
     * Obtient l'identifiant du plat.
     *
     * @return l'identifiant unique du plat
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant du plat.
     *
     * @param id l'identifiant à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le nom du plat.
     *
     * @return le nom du plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du plat.
     *
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient la description du plat.
     *
     * @return la description du plat
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du plat.
     *
     * @param description la description à définir
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient le prix du plat.
     *
     * @return le prix du plat en euros
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Définit le prix du plat.
     *
     * @param prix le prix à définir en euros
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }
}