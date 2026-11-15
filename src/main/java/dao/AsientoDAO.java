package dao;

import config.ConexionDB;
import enums.ClaseAsiento;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsientoDAO implements DAO<Asiento> {

    @Override
    public void insertar(Asiento asiento) throws AccesoDatosException {
        throw new AccesoDatosException("Debe indicar el ID del avión para insertar un asiento (use insertar(asiento, avionId))", null);
    }

    public void insertar(Asiento asiento, int avionId) throws AccesoDatosException {
        String sql = "INSERT INTO asientos (avion_id, numero, clase, disponible) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, avionId);
            ps.setInt(2, asiento.getNumero());
            ps.setString(3, asiento.getClase().name());
            ps.setBoolean(4, asiento.isDisponible());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    asiento.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar asiento", e);
        }
    }

    @Override
    public List<Asiento> listarTodos() throws AccesoDatosException {
        String sql = "SELECT * FROM asientos";
        List<Asiento> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAsiento(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar asientos", e);
        }
    }
    public List<Asiento> listarPorAvion(int avionId) throws AccesoDatosException {
        String sql = "SELECT * FROM asientos WHERE avion_id = ? ORDER BY numero";
        List<Asiento> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, avionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAsiento(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar asientos del avión " + avionId, e);
        }
    }
    @Override
    public Asiento buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM asientos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAsiento(rs);
                }
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar asiento por ID", e);
        }
    }

    @Override
    public void actualizar(Asiento asiento) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "UPDATE asientos SET numero = ?, clase = ?, disponible = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, asiento.getNumero());
            ps.setString(2, asiento.getClase().name());
            ps.setBoolean(3, asiento.isDisponible());
            ps.setInt(4, asiento.getId());

            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(asiento.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar asiento", e);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "DELETE FROM asientos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar asiento", e);
        }
    }
    private Asiento mapearAsiento(ResultSet rs) throws SQLException {
        Asiento asiento = new Asiento(
                rs.getInt("id"),
                rs.getInt("numero"),
                ClaseAsiento.valueOf(rs.getString("clase")));

        asiento.setDisponible(rs.getBoolean("disponible"));
        return asiento;
    }
}
