package co.edu.uniquindio.seci_proyect.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        this("Resource not found");
    }
}
