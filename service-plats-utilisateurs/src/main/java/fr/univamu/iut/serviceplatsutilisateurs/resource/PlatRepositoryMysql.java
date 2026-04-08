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
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PLAT")) {
            while (rs.next()) {
                plats.add(new Plat(rs.getInt("id"), rs.getString("nom")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return plats;
    }

    @Override
    public void close() {
        try { if (dbConnection != null) dbConnection.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}