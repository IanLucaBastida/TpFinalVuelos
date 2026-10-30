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
    public List<Reserva> listarDesdeBD() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Reserva buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizarEnBD(Reserva reserva) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
