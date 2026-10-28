package exceptions;

public class AccesoBaseDatos extends RuntimeException {
    public AccesoBaseDatos(String message) {
        super(message);
    }
}
