package fr.univamu.iut.serviceplatsutilisateurs.model;

public class Plat {
    private int id;
    private String nom;

    public Plat() {
    }

    public Plat(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}