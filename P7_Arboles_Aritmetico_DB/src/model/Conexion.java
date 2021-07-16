package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Conexion {
    private String host = "localhost";
    private String database = "earitmeticas";
    private int port = 5432;
    private String username = "postgres";
    private String password = "8450706183";

    private String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
    private String urlcredentials = url + "?user=" + username + "&password=" + password;
    private Connection con = null;

    private static Conexion unicaConexion = null;

    private static final Logger log = LogManager.getRootLogger();

    public static Conexion getOrCreate() {
        if (unicaConexion == null)
            unicaConexion = new Conexion();
        return unicaConexion;
    }

    private Conexion() {
        try {
            con = DriverManager.getConnection(urlcredentials);
        } catch (SQLException e) {
            log.error("No puede conectarse al servidor");
        }
    }

    public ResultSet ejecutarConsulta(String query) throws SQLException {
        ResultSet res = null;
        Statement stmt = con.createStatement();
        res = stmt.executeQuery(query);
        return res;
    }

    public void ejecutarComando(String query) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

    public void cerrarConexion() throws SQLException {
        con.close();
    }
}
