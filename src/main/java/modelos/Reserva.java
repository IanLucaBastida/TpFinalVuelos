package modelos;


import enums.EstadoReserva;
import enums.MetodoPago;
import interfaces.Identificable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva implements Identificable {

    private int id;
    private LocalDateTime fechaReserva;
    protected EstadoReserva estado;
    protected Asiento asiento;
    protected Cliente cliente;
    protected MetodoPago metodoPago;
    protected Vuelo vuelo;

    public Reserva(int id,EstadoReserva estado, Asiento asiento, Cliente cliente, MetodoPago metodoPago, Vuelo vuelo) {
        setId(id);
        this.fechaReserva = LocalDateTime.now();
        this.estado = estado;
        this.asiento = asiento;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.vuelo = vuelo;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public double calcularTotal(){
        if (vuelo == null || asiento == null) return 0.0;
        return vuelo.calcularPrecioBase() * asiento.getClase().getRecargo();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reserva reserva)) return false;
        return Objects.equals(fechaReserva, reserva.fechaReserva) && Objects.equals(asiento, reserva.asiento) && Objects.equals(cliente, reserva.cliente) && Objects.equals(vuelo, reserva.vuelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaReserva, asiento, cliente, vuelo);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaReserva=" + fechaReserva +
                ", estado=" + estado +
                ", asiento=" + asiento +
                ", cliente=" + cliente +
                ", metodoPago=" + metodoPago +
                ", vuelo=" + vuelo +
                '}';
    }
}
