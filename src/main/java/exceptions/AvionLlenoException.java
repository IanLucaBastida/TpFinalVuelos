package exceptions;

import modelos.Avion;

public class AvionLlenoException extends Exception {
    public AvionLlenoException() {
        super("Avion lleno, elija otro vuelo porfavor");

    }
}
