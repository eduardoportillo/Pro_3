package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import estructura.Lista;

public class expresion_artimeticaDAO {

    private static final Logger log = LogManager.getRootLogger();

    public expresion_artimeticaDAO() {

    }

    public Lista<expresion_aritmeticaDTO> getTodos() {
        Lista<expresion_aritmeticaDTO> resultado = null;
        String query = "select * from expresion_aritmetica;";

        ResultSet rs = null;

        try {
            Conexion conexion = Conexion.getOrCreate();
            rs = conexion.ejecutarConsulta(query);
        } catch (SQLException e) {
            log.error("No pudo ejecutar consulta");
            return resultado;
        }

        try {
            resultado = new Lista<expresion_aritmeticaDTO>();
            while (rs.next()) {
                int _id = rs.getInt("id");
                String _nombre = rs.getString("expresion_aritmetica");
                resultado.insertar(new expresion_aritmeticaDTO(_id, _nombre));
            }
        } catch (SQLException e) {
            log.error("Error recuperando datos del resultset: " + e.getMessage());
        }

        return resultado;
    }

    public expresion_aritmeticaDTO getPorId(int id) {
        expresion_aritmeticaDTO resultado = null;
        String query = "select * from expresion_aritmetica where id=" + id;

        ResultSet rs = null;
        try {
            Conexion conexion = Conexion.getOrCreate();
            rs = conexion.ejecutarConsulta(query);
        } catch (SQLException e) {
            log.error("No pudo ejecutar consulta");
            return resultado;
        }

        try {
            if (rs.next()) {
                int _id = rs.getInt("id");
                String EA = rs.getString("expresion_aritmetica");
                resultado = new expresion_aritmeticaDTO(_id, EA);
            }
        } catch (SQLException e) {
            log.error("Error recuperando datos del resultset: " + e.getMessage());
        }

        return resultado;
    }

    public void insertar(String expresion) {
        String query = "insert into expresion_aritmetica (expresion_aritmetica) values ('" + expresion + "')";

        try {
            Conexion conexion = Conexion.getOrCreate();
            conexion.ejecutarComando(query);
            log.error("se inserto registro");
        } catch (SQLException e) {
            log.error("No pudo ejecutar consulta");
        }
    }

    public void actualizar(expresion_aritmeticaDTO p) {
        String query = "update expresion_aritmetica set expresion_aritmetica='" + p.getExpresion() + "' where id="
                + p.getId();

        try {
            Conexion conexion = Conexion.getOrCreate();
            conexion.ejecutarComando(query);
        } catch (SQLException e) {
            log.error("No pudo ejecutar consulta");
        }
    }

    public void eliminar(expresion_aritmeticaDTO p) {
        String query = "delete from expresion_aritmetica where id=" + p.getId();

        try {
            Conexion conexion = Conexion.getOrCreate();
            conexion.ejecutarComando(query);
        } catch (SQLException e) {
            log.error("No pudo ejecutar consulta");
        }
    }
}
