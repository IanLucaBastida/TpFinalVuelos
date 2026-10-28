package dao;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Vuelo;

import java.util.List;

public class VueloDAO implements DAO<Vuelo> {

    @Override
    public void insertar(Vuelo vuelo) throws AccesoDatosException {

    }

    @Override
    public List<Vuelo> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Vuelo buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Vuelo vuelo) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
