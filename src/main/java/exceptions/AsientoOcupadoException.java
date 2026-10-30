package exceptions;

public class AsientoOcupadoException extends Exception {

    private final int id;

    public AsientoOcupadoException(int id) {
        super("El asiento " +  id + " ya se encuentra ocupado");
        this.id = id;
    }
}
