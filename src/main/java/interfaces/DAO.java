package interfaces;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;

import java.util.List;

public interface DAO <T> {
    public void  insertar(T t) throws AccesoDatosException;
    public List<T> listarTodos() throws AccesoDatosException;
    public T buscarPorId(int id) throws AccesoDatosException, ElementoNoEncontradoException;
    public void actualizar(T t) throws AccesoDatosException, ElementoNoEncontradoException;
    public void eliminar(int codigo) throws AccesoDatosException, ElementoNoEncontradoException;
}
