package dao;

import config.ConexionDB;
import enums.Destinos;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Avion;
import modelos.Vuelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO implements DAO<Vuelo> {

    private final AvionDAO avionDAO = new AvionDAO();

    @Override
    public void insertar(Vuelo vuelo) throws AccesoDatosException {
        String sql = "INSERT INTO vuelos (avion_id, destino, salida, llegada) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, vuelo.getAvion().getId());
            ps.setString(2, vuelo.getDestino().name());
            ps.setTimestamp(3, Timestamp.valueOf(vuelo.getSalida()));
            ps.setTimestamp(4, Timestamp.valueOf(vuelo.getLlegada()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    vuelo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar vuelo", e);
        }
    }

    @Override
    public List<Vuelo> listarTodos() throws AccesoDatosException {
        String sql = "SELECT * FROM vuelos";
        List<Vuelo> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearVuelo(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar vuelos", e);
        }
    }

    @Override
    public Vuelo buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM vuelos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVuelo(rs);
                }
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar vuelo por ID", e);
        }
    }

    @Override
    public void actualizar(Vuelo vuelo) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "UPDATE vuelos SET avion_id = ?, destino = ?, salida = ?, llegada = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vuelo.getAvion().getId());
            ps.setString(2, vuelo.getDestino().name());
            ps.setTimestamp(3, Timestamp.valueOf(vuelo.getSalida()));
            ps.setTimestamp(4, Timestamp.valueOf(vuelo.getLlegada()));
            ps.setInt(5, vuelo.getId());

            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(vuelo.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar vuelo", e);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "DELETE FROM vuelos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar vuelo", e);
        }
    }
    private Vuelo mapearVuelo(ResultSet rs) throws SQLException, AccesoDatosException {
        try {
            Avion avion = avionDAO.buscarPorId(rs.getInt("avion_id"));
            return new Vuelo(
                    rs.getInt("id"),
                    avion,
                    Destinos.valueOf(rs.getString("destino")),
                    rs.getTimestamp("llegada").toLocalDateTime(),
                    rs.getTimestamp("salida").toLocalDateTime()
            );
        } catch (ElementoNoEncontradoException e) {
            throw new AccesoDatosException("Avión asociado al vuelo no encontrado", e);
        }
    }
}
