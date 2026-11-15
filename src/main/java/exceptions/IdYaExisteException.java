package exceptions;

public class IdYaExisteException extends Exception {

    private final int id;

    public <T> IdYaExisteException(int id) {
        super("ERROR: Ya existe un elemento registrado con el identificador: " + id);
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
