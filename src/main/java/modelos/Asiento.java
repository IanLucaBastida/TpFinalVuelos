package modelos;

import enums.ClaseAsiento;
import enums.EstadoReserva;
import interfaces.Disponible;
import interfaces.Identificable;

public class Asiento implements Identificable, Disponible, Comparable<Asiento> {

    private  int id;
    protected ClaseAsiento clase;
    protected boolean disponible;

    public Asiento() {
        this.disponible = true;
    }
    public Asiento(ClaseAsiento clase) {
        this.clase = clase;
        this.disponible = true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean isDisponible() {
        return false;
    }

    @Override
    public void setDisponible(boolean disponible) {

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
