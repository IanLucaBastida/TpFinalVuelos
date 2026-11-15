package exceptions;

public class ElementoNoEncontradoException extends Exception {

    private final int id;

    public ElementoNoEncontradoException(int id) {
        super("No se encontró ningún elemento con el identificador: " + id);
        this.id = id;
    }
    public ElementoNoEncontradoException(String mensaje) { super(mensaje); this.id = 0; }

    public int getId() {
        return id;
    }
}
