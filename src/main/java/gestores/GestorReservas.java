package gestores;

import dao.AsientoDAO;
import dao.ReservaDAO;
import enums.EstadoReserva;
import enums.MetodoPago;
import exceptions.AccesoDatosException;
import exceptions.AsientoOcupadoException;
import exceptions.DatosInvalidosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.*;

import java.util.List;

public class GestorReservas extends Gestor<Reserva>{

    private final ReservaDAO reservaDAO;
    private final AsientoDAO asientoDAO;

    public GestorReservas() {
        super(new ReservaDAO());
        this.reservaDAO = (ReservaDAO) this.dao;
        this.asientoDAO = new AsientoDAO();
    }

    public Reserva crearReserva(Cliente cliente, Vuelo vuelo, Asiento asiento, MetodoPago metodoPago)
            throws AccesoDatosException, AsientoOcupadoException, DatosInvalidosException, ElementoNoEncontradoException {

        if (cliente == null || vuelo == null || asiento == null || metodoPago == null) {
            throw new DatosInvalidosException("Todos los datos de la reserva son obligatorios.");
        }
        if (!vuelo.getAvion().getAsientos().contains(asiento)) {
            throw new DatosInvalidosException("El asiento seleccionado no pertenece al avión de este vuelo.");
        }
        if (!asiento.isDisponible()) {
            throw new AsientoOcupadoException(asiento.getId());
        }

        Reserva reserva = new Reserva(0, EstadoReserva.RESERVADA, asiento, cliente, metodoPago, vuelo);
        guardarEnBD(reserva);

        asiento.setDisponible(false);
        asientoDAO.actualizar(asiento);

        return reserva;
    }

    public void cancelarReserva(int reservaId, Persona usuarioOperando)
            throws AccesoDatosException, ElementoNoEncontradoException, DatosInvalidosException {

        Reserva reserva = buscarEnBD(reservaId);
        if (usuarioOperando instanceof Cliente cliente) {
            if (reserva.getCliente().getId() != cliente.getId()) {
                throw new DatosInvalidosException("No tiene permisos para cancelar una reserva de otro cliente.");
            }
        }

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new DatosInvalidosException("La reserva ya se encuentra cancelada.");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        actualizarEnBD(reserva);

        Asiento asiento = reserva.getAsiento();
        asiento.setDisponible(true);
        asientoDAO.actualizar(asiento);
    }

    public List<Reserva> listarPorCliente(int clienteId) throws AccesoDatosException {
        return reservaDAO.listarPorCliente(clienteId);
    }


}
