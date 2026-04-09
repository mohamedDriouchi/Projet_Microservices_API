package fr.univamu.iut.servicemenus.infrastructure.persistence.mysql;

import fr.univamu.iut.servicemenus.domain.model.Menu;
import fr.univamu.iut.servicemenus.domain.model.MenuItem;
import fr.univamu.iut.servicemenus.domain.repository.MenuRepositoryInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation MySQL du repository des menus.
 * Gère la persistance et l'accès aux données des menus dans la base de données MySQL.
 * Implemente le contrat défini par MenuRepositoryInterface.
 *
 * @author Service Menus
 * @version 1.0
 */
public class MenuRepositoryMysql implements MenuRepositoryInterface {

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
    public MenuRepositoryMysql(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(url, user, pwd);
    }

    /**
     * Récupère tous les menus de la base de données.
     *
     * @return une liste de tous les menus, ou une liste vide s'il n'y en a pas
     */
    @Override
    public List<Menu> findAll() {
        List<Menu> menus = new ArrayList<>();
        String query = "SELECT * FROM MENU";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                menus.add(new Menu(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("createur"),
                        rs.getTimestamp("date_creation") != null ? rs.getTimestamp("date_creation").toLocalDateTime() : null,
                        rs.getTimestamp("date_maj") != null ? rs.getTimestamp("date_maj").toLocalDateTime() : null,
                        rs.getDouble("prix_total"),
                        getItemsForMenu(rs.getInt("id"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    /**
     * Récupère un menu spécifique par son identifiant.
     *
     * @param id l'identifiant du menu à récupérer
     * @return le menu correspondant, ou null s'il n'existe pas
     */
    @Override
    public Menu findById(int id) {
        String query = "SELECT * FROM MENU WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Menu(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getString("createur"),
                            rs.getTimestamp("date_creation") != null ? rs.getTimestamp("date_creation").toLocalDateTime() : null,
                            rs.getTimestamp("date_maj") != null ? rs.getTimestamp("date_maj").toLocalDateTime() : null,
                            rs.getDouble("prix_total"),
                            getItemsForMenu(id)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Enregistre un nouveau menu dans la base de données.
     *
     * @param menu l'objet menu à enregistrer
     * @return true si l'insertion a réussi, false sinon
     */
    @Override
    public boolean save(Menu menu) {
        String query = "INSERT INTO MENU (nom, description, createur, date_creation, date_maj, prix_total) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getDescription());
            ps.setString(3, menu.getCreateur());
            ps.setTimestamp(4, Timestamp.valueOf(menu.getDateCreation()));
            ps.setTimestamp(5, Timestamp.valueOf(menu.getDateMaj()));
            ps.setDouble(6, menu.getPrixTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un menu de la base de données par son identifiant.
     *
     * @param id l'identifiant du menu à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM MENU WHERE id = ?";
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

    /**
     * Récupère les items (plats) associés à un menu.
     *
     * @param menuId l'identifiant du menu
     * @return une liste des MenuItem du menu
     */
    private List<MenuItem> getItemsForMenu(int menuId) {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT plat_id, quantite FROM MENU_ITEM WHERE menu_id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new MenuItem(
                            rs.getInt("plat_id"),
                            rs.getInt("quantite")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}

