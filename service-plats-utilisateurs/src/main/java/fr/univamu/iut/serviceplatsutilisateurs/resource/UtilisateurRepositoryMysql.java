package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepositoryMysql implements UtilisateurRepositoryInterface {
    private Connection dbConnection;

    /**
     * Constructeur : initialise la connexion JDBC.
     * @param url URL de la base AlwaysData
     * @param user Nom d'utilisateur AlwaysData
     * @param pwd Mot de passe AlwaysData
     */
    public UtilisateurRepositoryMysql(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(url, user, pwd);
    }

    /**
     * Récupère tous les utilisateurs de la table UTILISATEUR.
     * @return une liste d'objets Utilisateur
     */
    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT * FROM UTILISATEUR";

        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Ferme la connexion proprement (Section 1.6 du TD).
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