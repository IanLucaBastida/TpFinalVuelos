package dao;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Avion;

import java.util.List;

public class AvionDAO implements DAO<Avion> {
    @Override
    public void insertar(Avion avion) throws AccesoDatosException {

    }

    @Override
    public List<Avion> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Avion buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Avion avion) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
