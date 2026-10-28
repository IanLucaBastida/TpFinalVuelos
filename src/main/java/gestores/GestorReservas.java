package gestores;

import dao.ReservaDAO;
import interfaces.DAO;
import modelos.Reserva;

public class GestorReservas extends Gestor<Reserva>{
    private  final DAO<Reserva> dao =  new ReservaDAO();
}
