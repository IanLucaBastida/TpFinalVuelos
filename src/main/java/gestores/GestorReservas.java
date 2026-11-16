package gestores;

import dao.AsientoDAO;
import dao.AvionDAO;
import dao.ReservaDAO;
import enums.EstadoAvion;
import enums.EstadoReserva;
import enums.MetodoPago;
import exceptions.AccesoDatosException;
import exceptions.AsientoOcupadoException;
import exceptions.DatosInvalidosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.*;

import java.time.LocalDateTime;
import java.util.List;

public class GestorReservas extends Gestor<Reserva>{

    private final ReservaDAO reservaDAO;
    private final AsientoDAO asientoDAO;
    private final AvionDAO avionDAO;

    public GestorReservas() {
        super(new ReservaDAO());
        this.reservaDAO = (ReservaDAO) this.dao;
        this.asientoDAO = new AsientoDAO();
        this.avionDAO = new AvionDAO();
    }

    public Reserva crearReserva(Cliente cliente, Vuelo vuelo, Asiento asiento, MetodoPago metodoPago)
            throws AccesoDatosException, AsientoOcupadoException, DatosInvalidosException, ElementoNoEncontradoException {

        if (cliente == null || vuelo == null || asiento == null || metodoPago == null) {
            throw new DatosInvalidosException("Todos los datos de la reserva son obligatorios.");
        }
        if (vuelo.getSalida().isBefore(LocalDateTime.now())) {
            throw new DatosInvalidosException("No se puede reservar un vuelo cuya fecha de salida ya paso.");
        }
        if (vuelo.getAvion().getEstado() != EstadoAvion.DISPONIBLE) {
            throw new DatosInvalidosException("El avion asignado a este vuelo no esta disponible.");
        }
        if (!vuelo.getAvion().getAsientos().contains(asiento)) {
            throw new DatosInvalidosException("El asiento seleccionado no pertenece al avion de este vuelo.");
        }
        if (!asiento.isDisponible()) {
            throw new AsientoOcupadoException(asiento.getId());
        }

        Reserva reserva = new Reserva(0, EstadoReserva.RESERVADA, asiento, cliente, metodoPago, vuelo);
        guardarEnBD(reserva);

        asiento.setDisponible(false);
        asientoDAO.actualizar(asiento);

        boolean quedanLibres = false;
        for (Asiento a : vuelo.getAvion().getAsientos()) {
            if (a.isDisponible()) {
                quedanLibres = true;
                break;
            }
        }
        if (!quedanLibres) {
            vuelo.getAvion().setEstado(EstadoAvion.LLENO);
            avionDAO.actualizar(vuelo.getAvion());
        }

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
        if (reserva.getVuelo().getSalida().isBefore(LocalDateTime.now())) {
            throw new DatosInvalidosException("No se puede cancelar una reserva de un vuelo que ya partio.");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        actualizarEnBD(reserva);

        Asiento asiento = reserva.getAsiento();
        asiento.setDisponible(true);
        asientoDAO.actualizar(asiento);

        Avion avion = reserva.getVuelo().getAvion();
        if (avion.getEstado() == EstadoAvion.LLENO) {
            avion.setEstado(EstadoAvion.DISPONIBLE);
            avionDAO.actualizar(avion);
        }
    }

    public List<Reserva> listarPorCliente(int clienteId) throws AccesoDatosException {
        return reservaDAO.listarPorCliente(clienteId);
    }


}
