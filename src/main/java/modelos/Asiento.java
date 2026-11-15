package modelos;

import enums.ClaseAsiento;
import enums.EstadoReserva;
import interfaces.Disponible;
import interfaces.Identificable;

import java.util.Objects;

public class Asiento implements Identificable, Disponible, Comparable<Asiento> {

    private int id;
    protected int numero;
    protected ClaseAsiento clase;
    protected boolean disponible;


    public Asiento(int id, int numero, ClaseAsiento clase) {
        setId(id);
        this.numero = numero;
        this.clase = clase;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClaseAsiento getClase() {
        return clase;
    }

    public void setClase(ClaseAsiento clase) {
        this.clase = clase;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
    this.disponible = disponible;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "id=" + id +
                ", clase=" + clase +
                ", disponible=" + disponible +
                '}';
    }


    @Override
    public int compareTo(Asiento o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Asiento asiento)) return false;
        return id == asiento.id;
    }
}
