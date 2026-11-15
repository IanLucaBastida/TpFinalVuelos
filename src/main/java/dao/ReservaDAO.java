package dao;

import config.ConexionDB;
import enums.EstadoReserva;
import enums.MetodoPago;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;
import modelos.Cliente;
import modelos.Reserva;
import modelos.Vuelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO implements DAO<Reserva> {
    private final AsientoDAO asientoDAO = new AsientoDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final VueloDAO vueloDAO = new VueloDAO();

    @Override
    public void insertar(Reserva reserva) throws AccesoDatosException {
        String sql = "INSERT INTO reservas (fecha_reserva, estado, asiento_id, cliente_id, metodo_pago, vuelo_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(reserva.getFechaReserva()));
            ps.setString(2, reserva.getEstado().name());
            ps.setInt(3, reserva.getAsiento().getId());
            ps.setInt(4, reserva.getCliente().getId());
            ps.setString(5, reserva.getMetodoPago().name());
            ps.setInt(6, reserva.getVuelo().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar reserva", e);
        }
    }

    @Override
    public List<Reserva> listarTodos() throws AccesoDatosException {
        String sql = "SELECT * FROM reservas";
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar reservas", e);
        }
    }
    public List<Reserva> listarPorCliente(int clienteId) throws AccesoDatosException {
        String sql = "SELECT * FROM reservas WHERE cliente_id = ?";
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar reservas del cliente", e);
        }
    }

    @Override
    public Reserva buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM reservas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReserva(rs);
                }
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar reserva por ID", e);
        }
    }

    @Override
    public void actualizar(Reserva reserva) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "UPDATE reservas SET estado = ?, asiento_id = ?, cliente_id = ?, metodo_pago = ?, vuelo_id = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reserva.getEstado().name());
            ps.setInt(2, reserva.getAsiento().getId());
            ps.setInt(3, reserva.getCliente().getId());
            ps.setString(4, reserva.getMetodoPago().name());
            ps.setInt(5, reserva.getVuelo().getId());
            ps.setInt(6, reserva.getId());

            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(reserva.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar reserva", e);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar reserva", e);
        }
    }
    private Reserva mapearReserva(ResultSet rs) throws SQLException, AccesoDatosException {
        try {
            Asiento asiento = asientoDAO.buscarPorId(rs.getInt("asiento_id"));
            Cliente cliente = (Cliente) personaDAO.buscarPorId(rs.getInt("cliente_id"));
            Vuelo vuelo = vueloDAO.buscarPorId(rs.getInt("vuelo_id"));

            Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    EstadoReserva.valueOf(rs.getString("estado")),
                    asiento,
                    cliente,
                    MetodoPago.valueOf(rs.getString("metodo_pago")),
                    vuelo
            );
            reserva.setFechaReserva(rs.getTimestamp("fecha_reserva").toLocalDateTime());
            return reserva;
        } catch (ElementoNoEncontradoException e) {
            throw new AccesoDatosException("Datos asociados a la reserva no encontrados", e);
        }
    }
}
