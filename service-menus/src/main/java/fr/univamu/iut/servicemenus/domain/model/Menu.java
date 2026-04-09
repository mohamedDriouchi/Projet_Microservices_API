package fr.univamu.iut.servicemenus.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un menu du catalogue.
 * Cette classe est une entité du domaine, indépendante de la persistance.
 * Elle encapsule les informations essentielles d'un menu : son identifiant,
 * son nom, sa description, son créateur, les dates et le prix total.
 *
 * @author Service Menus
 * @version 1.0
 */
public class Menu {
    /** Identifiant unique du menu */
    private int id;

    /** Nom du menu */
    private String nom;

    /** Description du menu */
    private String description;

    /** Créateur du menu */
    private String createur;

    /** Date de création du menu */
    private LocalDateTime dateCreation;

    /** Date de dernière mise à jour */
    private LocalDateTime dateMaj;

    /** Prix total du menu */
    private double prixTotal;

    /** Liste des plats du menu */
    private List<MenuItem> items = new ArrayList<>();

    /**
     * Constructeur par défaut. Crée un menu vide.
     */
    public Menu() {
    }

    /**
     * Constructeur avec paramètres.
     * Crée un menu avec tous les attributs initialisés.
     *
     * @param id l'identifiant unique du menu
     * @param nom le nom du menu
     * @param description la description du menu
     * @param createur le créateur du menu
     * @param dateCreation la date de création
     * @param dateMaj la date de mise à jour
     * @param prixTotal le prix total du menu
     * @param items la liste des plats du menu
     */
    public Menu(int id, String nom, String description, String createur, LocalDateTime dateCreation,
                LocalDateTime dateMaj, double prixTotal, List<MenuItem> items) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.createur = createur;
        this.dateCreation = dateCreation;
        this.dateMaj = dateMaj;
        this.prixTotal = prixTotal;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(LocalDateTime dateMaj) {
        this.dateMaj = dateMaj;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}

