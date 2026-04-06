package fr.univamu.iut.serviceplatsutilisateurs.resource;
import fr.univamu.iut.serviceplatsutilisateurs.model.Plat;
import java.util.List;

public interface PlatRepositoryInterface {
    List<Plat> findAll();
    void close();
}