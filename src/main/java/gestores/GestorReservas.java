package gestores;

import dao.ReservaDAO;
import exceptions.AccesoDatosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Reserva;

import java.util.List;

public class GestorReservas extends Gestor<Reserva>{
    private  final DAO<Reserva> dao =  new ReservaDAO();

    @Override
    public void guardarEnBD(Reserva reserva) throws AccesoDatosException {
    dao.insertar(reserva);
    }

    @Override
    public List<Reserva> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    @Override
    public Reserva buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(codigo);
    }

    @Override
    public void actualizarEnBD(Reserva reserva) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.actualizar(reserva);
    }

    @Override
    public void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException {
    dao.eliminar(codigo);
    }


}
