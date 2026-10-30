package exceptions;

import modelos.Avion;

public class AvionLlenoException extends Exception {
    private final Avion  avion;
    public AvionLlenoException(Avion avion) {
        super("Avion lleno, elija otro vuelo porfavor");
        this.avion = avion;
    }
}
