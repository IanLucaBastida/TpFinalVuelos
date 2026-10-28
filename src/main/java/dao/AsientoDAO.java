package dao;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;

import java.util.List;

public class AsientoDAO implements DAO<Asiento> {

    @Override
    public void insertar(Asiento asiento) throws AccesoDatosException {

    }

    @Override
    public List<Asiento> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Asiento buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Asiento asiento) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
