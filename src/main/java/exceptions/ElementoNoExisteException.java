package exceptions;

public class ElementoNoExisteException extends RuntimeException {
    public ElementoNoExisteException(String message) {
        super(message);
    }
}
