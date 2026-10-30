package exceptions;

public class IdYaExisteException extends Exception {

    private final String id;

    public <T> IdYaExisteException(String id) {
        super("ERROR: Ya existe un elemento registrado con el identificador: " + id);
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
