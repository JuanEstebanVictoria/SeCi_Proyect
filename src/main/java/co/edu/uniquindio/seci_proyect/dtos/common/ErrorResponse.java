package co.edu.uniquindio.seci_proyect.dtos.common;

public record ErrorResponse(
        String code,
        String message
) {
    // Constructor adicional para compatibilidad con códigos numéricos
    public ErrorResponse(int statusCode, String message) {
        this(String.valueOf(statusCode), message);
    }
}