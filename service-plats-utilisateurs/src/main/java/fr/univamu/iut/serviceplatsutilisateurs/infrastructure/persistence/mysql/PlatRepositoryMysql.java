package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Plat;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.PlatRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation MySQL du repository des plats.
 * Gère la persistance et l'accès aux données des plats dans la base de données MySQL.
 * Implemente le contrat défini par PlatRepositoryInterface.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public class PlatRepositoryMysql implements PlatRepositoryInterface {

    /** Connexion à la base de données MySQL */
    private Connection dbConnection;

    /**
     * Constructeur. Établit une connexion à la base de données MySQL.
     *
     * @param url l'URL JDBC de la base de données (ex: jdbc:mysql://localhost:3306/db)
     * @param user le nom d'utilisateur pour se connecter
     * @param pwd le mot de passe de l'utilisateur
     * @throws Exception en cas d'erreur de chargement du driver ou de connexion
     */
    public PlatRepositoryMysql(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(url, user, pwd);
    }

    /**
     * Récupère tous les plats de la base de données.
     *
     * @return une liste de tous les plats, ou une liste vide s'il n'y en a pas
     */
    @Override
    public List<Plat> findAll() {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM PLAT";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                plats.add(new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    /**
     * Récupère un plat spécifique par son identifiant.
     *
     * @param id l'identifiant du plat à récupérer
     * @return le plat correspondant, ou null s'il n'existe pas
     */
    @Override
    public Plat findById(int id) {
        String query = "SELECT * FROM PLAT WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Plat(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Enregistre un nouveau plat dans la base de données.
     *
     * @param plat l'objet plat à enregistrer
     * @return true si l'insertion a réussi, false sinon
     */
    @Override
    public boolean save(Plat plat) {
        String query = "INSERT INTO PLAT (nom, description, prix) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getDescription());
            ps.setDouble(3, plat.getPrix());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un plat de la base de données par son identifiant.
     *
     * @param id l'identifiant du plat à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM PLAT WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ferme la connexion à la base de données.
     * Libère les ressources utilisées par la connexion.
     */
    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}