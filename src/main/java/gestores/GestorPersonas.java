package gestores;

import dao.PersonaDAO;
import exceptions.*;
import interfaces.DAO;
import modelos.Persona;

import java.util.List;

public class GestorPersonas extends Gestor<Persona>{
    private  final DAO<Persona> dao =  new PersonaDAO();

    @Override
    public void guardarEnBD(Persona persona) throws AccesoDatosException {
    dao.insertar(persona);
    }

    @Override
    public List<Persona> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    @Override
    public Persona buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(codigo);
    }

    @Override
    public void actualizarEnBD(Persona persona) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.actualizar(persona);
    }

    @Override
    public void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.eliminar(codigo);
    }

}
