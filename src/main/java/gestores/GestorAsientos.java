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
    public void guardarEnBD(Asiento asiento) throws AccesoDatosException {
    dao.insertar(asiento);
    }

    @Override
    public List<Asiento> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    @Override
    public Asiento buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(codigo);
    }

    @Override
    public void actualizarEnBD(Asiento asiento) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.actualizar(asiento);
    }

    @Override
    public void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.eliminar(codigo);
    }

}
