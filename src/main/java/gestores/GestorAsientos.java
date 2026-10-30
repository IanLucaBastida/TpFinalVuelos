package gestores;

import dao.AsientoDAO;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;

import java.util.List;

public class GestorAsientos extends Gestor<Asiento>{
    private final DAO<Asiento> dao =  new AsientoDAO();


    @Override
    public List<Asiento> listarDesdeBD() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Asiento buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizarEnBD(Asiento asiento) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
