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
    public void guardarEnBD(Vuelo vuelo) throws AccesoDatosException {
    dao.insertar(vuelo);
    }

    @Override
    public List<Vuelo> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    @Override
    public Vuelo buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(codigo);
    }

    @Override
    public void actualizarEnBD(Vuelo vuelo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.actualizar(vuelo);
    }

    @Override
    public void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.eliminar(codigo);
    }


}
