package fr.univamu.iut.serviceplatsutilisateurs.domain.repository;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Utilisateur;
import java.util.List;

public interface UtilisateurRepositoryInterface {
    List<Utilisateur> findAll();
    Utilisateur findById(int id);
    boolean save(Utilisateur user);
    boolean delete(int id);
    void close();
}