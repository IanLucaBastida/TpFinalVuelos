package dao;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Reserva;

import java.util.List;

public class ReservaDAO implements DAO<Reserva> {

    @Override
    public void insertar(Reserva reserva) throws AccesoDatosException {

    }

    @Override
    public List<Reserva> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Reserva buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Reserva reserva) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
