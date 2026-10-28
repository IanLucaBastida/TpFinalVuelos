package dao;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Persona;

import java.util.List;

public class PersonaDAO implements DAO<Persona> {


    @Override
    public void insertar(Persona persona) throws AccesoDatosException {

    }

    @Override
    public List<Persona> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Persona buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Persona persona) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
