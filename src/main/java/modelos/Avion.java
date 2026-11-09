package modelos;

import enums.EstadoAvion;
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

    public Avion(String modelo, int capacidad) {
        this.modelo = modelo;
        this.estado = EstadoAvion.DISPONIBLE;
        this.asientos = new ArrayList<>();
        this.capacidad = capacidad;
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

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", estado=" + estado +
                ", asientos=" + asientos +
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
