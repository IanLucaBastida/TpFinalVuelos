package dao;

import config.ConexionDB;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Cliente;
import modelos.Persona;
import modelos.Recepcionista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO implements DAO<Persona> {


    @Override
    public void insertar(Persona persona) throws AccesoDatosException {
        String sql = "INSERT INTO personas (tipo_persona, nombre, apellido, genero, dni, email, legajo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (persona instanceof Cliente c) {
                ps.setString(1, "CLIENTE");
                ps.setString(6, c.getEmail());
                ps.setNull(7, Types.INTEGER);
            } else if (persona instanceof Recepcionista r) {
                ps.setString(1, "RECEPCIONISTA");
                ps.setNull(6, Types.VARCHAR);
                ps.setInt(7, r.getLegajo());
            } else {
                throw new AccesoDatosException("Tipo de persona no soportado", null);
            }

            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getApellido());
            ps.setString(4, persona.getGenero());
            ps.setInt(5, persona.getDni());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    persona.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar persona en la base de datos", e);
        }
    }

    @Override
    public List<Persona> listarTodos() throws AccesoDatosException {
        String sql = "SELECT * FROM personas";
        List<Persona> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPersona(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar personas", e);
        }
    }

    @Override
    public Persona buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM personas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPersona(rs);
                }
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar persona por ID", e);
        }
    }
    public Persona buscarPorDni(int dni) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM personas WHERE dni = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPersona(rs);
                }
                throw new ElementoNoEncontradoException(dni);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar persona por DNI", e);
        }
    }

    @Override
    public void actualizar(Persona persona) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "UPDATE personas SET nombre = ?, apellido = ?, genero = ?, dni = ?, email = ?, legajo = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getGenero());
            ps.setInt(4, persona.getDni());

            if (persona instanceof Cliente c) {
                ps.setString(5, c.getEmail());
                ps.setNull(6, Types.INTEGER);
            } else if (persona instanceof Recepcionista r) {
                ps.setNull(5, Types.VARCHAR);
                ps.setInt(6, r.getLegajo());
            }

            ps.setInt(7, persona.getId());

            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(persona.getId());
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar persona", e);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "DELETE FROM personas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ElementoNoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar persona", e);
        }
    }
    private Persona mapearPersona(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String tipo = rs.getString("tipo_persona");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String genero = rs.getString("genero");
        int dni = rs.getInt("dni");

        if ("RECEPCIONISTA".equalsIgnoreCase(tipo)) {
            return new Recepcionista(id, nombre, apellido, genero, dni, rs.getInt("legajo"));
        }
        return new Cliente(id, nombre, apellido, genero, dni, rs.getString("email"));
    }
}
