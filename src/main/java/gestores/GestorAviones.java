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
    public void guardarEnBD(Avion avion) throws AccesoDatosException {
    dao.insertar(avion);
    }

    @Override
    public List<Avion> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    @Override
    public Avion buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(codigo);
    }

    @Override
    public void actualizarEnBD(Avion avion) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.actualizar(avion);
    }

    @Override
    public void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.eliminar(codigo);
    }


}
