package modelos;

import enums.Destinos;
import interfaces.Identificable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Vuelo implements Identificable {

    private int id;
    protected Avion avion;
    protected Destinos destino;
    protected LocalDateTime llegada;
    protected LocalDateTime salida;

    public Vuelo() {
    }

    public Vuelo(int id, Avion avion, Destinos destino, LocalDateTime llegada, LocalDateTime salida) {
        setId(id);
        this.avion = avion;
        this.destino = destino;
        this.llegada = llegada;
        this.salida = salida;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Destinos getDestino() {
        return destino;
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;
    }

    public LocalDateTime getLlegada() {
        return llegada;
    }

    public void setLlegada(LocalDateTime llegada) {
        this.llegada = llegada;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public void setSalida(LocalDateTime salida) {
        this.salida = salida;
    }

    public double calcularPrecioBase(){
        if (destino == null) return 0;

        return destino.getPrecioBase();
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
        if (!(o instanceof Vuelo vuelo)) return false;
        return Objects.equals(avion, vuelo.avion) && destino == vuelo.destino && Objects.equals(salida, vuelo.salida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avion, destino, salida);
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "id=" + id +
                ", avion=" + avion +
                ", destino=" + destino +
                ", llegada=" + llegada +
                ", salida=" + salida +
                "precio base = " + calcularPrecioBase() +
                '}';
    }
}
