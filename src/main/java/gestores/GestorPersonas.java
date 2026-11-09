package gestores;

import dao.PersonaDAO;
import exceptions.*;
import interfaces.DAO;
import modelos.Persona;

import java.util.List;

public class GestorPersonas extends Gestor<Persona>{
    private  final DAO<Persona> dao =  new PersonaDAO();

    @Override
    public List<Persona> listarDesdeBD() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Persona buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizarEnBD(Persona persona) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    }
}
