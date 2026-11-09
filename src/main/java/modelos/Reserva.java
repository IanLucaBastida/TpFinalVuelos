package modelos;


import enums.EstadoReserva;
import enums.MetodoPago;
import interfaces.Identificable;

import java.time.LocalTime;

public class Reserva implements Identificable {

    private int id;
    protected LocalTime hora;
    protected EstadoReserva estado;
    protected Asiento asiento;
    protected Cliente cliente;
    protected MetodoPago metodoPago;
    protected Vuelo vuelo;

    public Reserva() {
    }



    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", hora=" + hora +
                ", estado=" + estado +
                ", asiento=" + asiento +
                ", cliente=" + cliente +
                ", metodoPago=" + metodoPago +
                '}';
    }
}
