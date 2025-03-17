package springBootCurso.exceptions;

public class ContextNotFoundException extends Exception {

    // Constructor vacío
    public ContextNotFoundException() {
        super();
    }

    // Constructor con mensaje
    public ContextNotFoundException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public ContextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con causa
    public ContextNotFoundException(Throwable cause) {
        super(cause);
    }
}
