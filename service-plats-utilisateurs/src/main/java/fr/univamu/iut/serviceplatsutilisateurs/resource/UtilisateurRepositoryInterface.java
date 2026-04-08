package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Utilisateur;
import java.util.List;

public interface UtilisateurRepositoryInterface {
    List<Utilisateur> findAll();
    Utilisateur findById(int id);
    boolean save(Utilisateur user);
    boolean delete(int id);
    void close();
}