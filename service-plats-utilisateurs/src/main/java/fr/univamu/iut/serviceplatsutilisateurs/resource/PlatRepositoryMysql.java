package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Plat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatRepositoryMysql implements PlatRepositoryInterface {
    private Connection dbConnection;

    public PlatRepositoryMysql(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(url, user, pwd);
    }

    @Override
    public List<Plat> findAll() {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM PLAT";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                plats.add(new Plat(rs.getInt("id"), rs.getString("nom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    @Override
    public Plat findById(int id) {
        String query = "SELECT * FROM PLAT WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Plat(rs.getInt("id"), rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Plat plat) {
        String query = "INSERT INTO PLAT (nom) VALUES (?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, plat.getNom());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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