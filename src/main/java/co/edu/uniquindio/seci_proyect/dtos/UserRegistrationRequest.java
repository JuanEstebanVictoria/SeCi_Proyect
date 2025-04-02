package co.edu.uniquindio.seci_proyect.dtos;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.boot.logging.structured.JsonWriterStructuredLogFormatter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;


public record UserRegistrationRequest(
        @NotBlank(message = "El email es requerido")
        @Email(message = "Debe ser un email válido")
        String email,

        @NotBlank(message = "El nombre completo es requerido")
        @Size(min = 2, max = 80, message = "Debe tener entre 2 y 80 caracteres")
        String fullName,

        @NotBlank(message = "La contraseña es requerida")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&]).{8,}$",
                message = "Debe contener al menos: un número, una letra minúscula, una mayúscula y un carácter especial")
        String password,

        @NotNull(message = "La fecha de nacimiento es requerida")
        @Past(message = "La fecha debe ser en el pasado")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateBirth,

        Rol rol,

        UserStatus status,

        GeoJsonPoint location
) {
    public UserRegistrationRequest {
        rol = Objects.requireNonNullElse(rol, Rol.USER);
        status = Objects.requireNonNullElse(status, UserStatus.ACTIVE);
    }
}