package fr.univamu.iut.serviceplatsutilisateurs.resource;
import fr.univamu.iut.serviceplatsutilisateurs.model.Utilisateur;
import java.util.List;

public interface UtilisateurRepositoryInterface {
    List<Utilisateur> findAll();
    void close();
}