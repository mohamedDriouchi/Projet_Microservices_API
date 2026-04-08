package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Plat;
import java.util.List;

public interface PlatRepositoryInterface {
    List<Plat> findAll();
    Plat findById(int id);
    boolean save(Plat plat);
    boolean delete(int id);
    void close();
}