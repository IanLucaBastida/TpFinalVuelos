package gestores;

import dao.VueloDAO;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Vuelo;

import java.util.List;

public class GestorVuelos extends Gestor<Vuelo>{
    private  final DAO<Vuelo> dao =  new VueloDAO();

    @Override
    public List<Vuelo> listarDesdeBD() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Vuelo buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizarEnBD(Vuelo vuelo) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
