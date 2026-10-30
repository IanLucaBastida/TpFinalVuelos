package gestores;

import dao.AvionDAO;

import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Avion;

import java.util.List;

public class GestorAviones extends Gestor<Avion>{
    private final DAO<Avion> dao =  new AvionDAO();


    @Override
    public List<Avion> listarDesdeBD() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Avion buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizarEnBD(Avion avion) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
