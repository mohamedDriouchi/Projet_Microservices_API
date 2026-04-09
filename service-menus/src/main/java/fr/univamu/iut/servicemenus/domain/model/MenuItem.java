package fr.univamu.iut.servicemenus.domain.model;

/**
 * Représente un plat dans un menu avec sa quantité.
 * Cette classe encapsule l'association entre un plat et sa quantité.
 *
 * @author Service Menus
 * @version 1.0
 */
public class MenuItem {
    /** Identifiant du plat */
    private int platId;

    /** Quantité du plat dans le menu */
    private int quantite;

    /**
     * Constructeur par défaut. Crée un élément vide.
     */
    public MenuItem() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param platId l'identifiant du plat
     * @param quantite la quantité du plat
     */
    public MenuItem(int platId, int quantite) {
        this.platId = platId;
        this.quantite = quantite;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

