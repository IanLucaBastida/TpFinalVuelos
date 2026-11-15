package dao;

import config.ConexionDB;
import enums.EstadoAvion;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;
import modelos.Avion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvionDAO implements DAO<Avion> {

    private final AsientoDAO asientoDAO = new AsientoDAO();

    @Override
    public void insertar(Avion avion) throws AccesoDatosException {
        String sql = "INSERT INTO aviones (modelo, estado, capacidad) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, avion.getModelo());
            ps.setString(2, avion.getEstado().name());
            ps.setInt(3, avion.getCapacidad());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    avion.setId(rs.getInt(1));
                }
            }

            for (Asiento asiento : avion.getAsientos()) {
                asientoDAO.insertar(asiento, avion.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar avión", e);
        }
    }

    @Override
    public List<Avion> listarTodos() throws AccesoDatosException {
        String sql = "SELECT * FROM aviones";
        List<Avion> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAvion(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar aviones", e);
        }
    }

    @Override
    public Avion buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM aviones WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAvion(rs);
                }
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar avión por ID", e);
        }
    }

    @Override
    public void actualizar(Avion avion) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "UPDATE aviones SET modelo = ?, estado = ?, capacidad = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, avion.getModelo());
            ps.setString(2, avion.getEstado().name());
            ps.setInt(3, avion.getCapacidad());
            ps.setInt(4, avion.getId());

            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(avion.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar avión", e);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "DELETE FROM aviones WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar avión", e);
        }
    }
    private Avion mapearAvion(ResultSet rs) throws SQLException, AccesoDatosException {
        int id = rs.getInt("id");
        Avion avion = new Avion(id, rs.getString("modelo"), rs.getInt("capacidad"));
        avion.setEstado(EstadoAvion.valueOf(rs.getString("estado")));
        avion.setAsientos(asientoDAO.listarPorAvion(id));
        return avion;
    }
}
