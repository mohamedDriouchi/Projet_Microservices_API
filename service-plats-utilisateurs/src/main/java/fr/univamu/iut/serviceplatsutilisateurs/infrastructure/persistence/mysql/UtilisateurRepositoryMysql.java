package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.persistence.mysql;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Utilisateur;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.UtilisateurRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation MySQL du repository des utilisateurs.
 * Gère la persistance et l'accès aux données des utilisateurs dans la base de données MySQL.
 * Implemente le contrat défini par UtilisateurRepositoryInterface.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
public class UtilisateurRepositoryMysql implements UtilisateurRepositoryInterface {

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
    public UtilisateurRepositoryMysql(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(url, user, pwd);
    }

    /**
     * Récupère tous les utilisateurs de la base de données.
     *
     * @return une liste de tous les utilisateurs, ou une liste vide s'il n'y en a pas
     */
    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT * FROM UTILISATEUR";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Récupère un utilisateur spécifique par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à récupérer
     * @return l'utilisateur correspondant, ou null s'il n'existe pas
     */
    @Override
    public Utilisateur findById(int id) {
        String query = "SELECT * FROM UTILISATEUR WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Enregistre un nouvel utilisateur dans la base de données.
     *
     * @param user l'objet utilisateur à enregistrer
     * @return true si l'insertion a réussi, false sinon
     */
    @Override
    public boolean save(Utilisateur user) {
        String query = "INSERT INTO UTILISATEUR (nom, prenom, email, adresse) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAdresse());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un utilisateur de la base de données par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM UTILISATEUR WHERE id = ?";
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