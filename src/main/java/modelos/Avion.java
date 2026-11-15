package modelos;

import enums.EstadoAvion;
import exceptions.AvionLlenoException;
import exceptions.ElementoNoEncontradoException;
import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Avion implements Identificable {

    private int id;
    protected String modelo;
    protected EstadoAvion estado;
    protected List<Asiento> asientos;
    protected int capacidad;


    public Avion() {
        this.estado = EstadoAvion.DISPONIBLE;
        this.asientos = new ArrayList<>();
    }

    public Avion(int id, String modelo, int capacidad) {
        setId(id);
        this.modelo = modelo;
        this.estado = EstadoAvion.DISPONIBLE;
        this.asientos = new ArrayList<>();
        this.capacidad = capacidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public EstadoAvion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAvion estado) {
        this.estado = estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public int getId() {
        return this.id;
    }



    public void agregarAsiento(Asiento asiento) throws  AvionLlenoException,ElementoNoEncontradoException {
        if (asiento == null){
            throw new ElementoNoEncontradoException("Elemento no encontrado");
        }
        if (asientos.size() >= this.capacidad) {
           throw new AvionLlenoException();
       }


        this.asientos.add(asiento);
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", estado=" + estado +
                ", asientos=" + asientos.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Avion avion)) return false;
        return id == avion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
