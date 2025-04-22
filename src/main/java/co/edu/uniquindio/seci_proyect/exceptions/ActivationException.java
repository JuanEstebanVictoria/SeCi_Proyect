package co.edu.uniquindio.seci_proyect.exceptions;

public class ActivationException extends RuntimeException {
    public ActivationException(String message) {
        super(message);
    }

    public ActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}