package exceptions;

public class ElementoNoEncontradoException extends Exception {

    private final int id;

    public ElementoNoEncontradoException(int id) {
        super("No se encontró ningún elemento con el identificador: " + id);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
