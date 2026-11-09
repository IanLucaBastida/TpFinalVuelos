package modelos;

import enums.Destinos;
import interfaces.Identificable;

public class Vuelo implements Identificable {

    private int id;
    protected Avion avion;
    protected Destinos destino;
    protected String llegada;
    protected String salida;

    public Vuelo() {
    }

    public Vuelo(Avion avion, Destinos destino, String llegada, String salida) {
        this.avion = avion;
        this.destino = destino;
        this.llegada = llegada;
        this.salida = salida;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
