package fr.univamu.iut.serviceplatsutilisateurs.domain.model;

/**
 * Représente un utilisateur du système.
 * Cette classe est une entité du domaine, indépendante de la persistance.
 * Elle encapsule les informations personnelles et de contact d'un utilisateur.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public class Utilisateur {
    /** Identifiant unique de l'utilisateur */
    private int id;

    /** Nom de famille de l'utilisateur */
    private String nom;

    /** Prénom de l'utilisateur */
    private String prenom;

    /** Adresse email de l'utilisateur */
    private String email;

    /** Adresse postale de l'utilisateur */
    private String adresse;

    /**
     * Constructeur par défaut. Crée un utilisateur vide.
     */
    public Utilisateur() {
    }

    /**
     * Constructeur avec paramètres.
     * Crée un utilisateur avec tous les attributs initialisés.
     *
     * @param id l'identifiant unique de l'utilisateur
     * @param nom le nom de famille de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param email l'adresse email de l'utilisateur
     * @param adresse l'adresse postale de l'utilisateur
     */
    public Utilisateur(int id, String nom, String prenom, String email, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
    }

    /**
     * Obtient l'identifiant de l'utilisateur.
     *
     * @return l'identifiant unique de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     *
     * @param id l'identifiant à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le nom de famille de l'utilisateur.
     *
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de famille de l'utilisateur.
     *
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le prénom de l'utilisateur.
     *
     * @return le prénom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom le prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Obtient l'adresse email de l'utilisateur.
     *
     * @return l'adresse email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email de l'utilisateur.
     *
     * @param email l'adresse email à définir
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient l'adresse postale de l'utilisateur.
     *
     * @return l'adresse postale de l'utilisateur
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse postale de l'utilisateur.
     *
     * @param adresse l'adresse postale à définir
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}