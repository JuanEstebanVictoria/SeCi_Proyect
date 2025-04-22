package co.edu.uniquindio.seci_proyect.dtos.user;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;
import java.util.Objects;


public record UserRegistrationRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email")
        String email,

        @NotBlank(message = "Fullname required")
        @Size(min = 2, max = 80, message = "Must be between 2 and 80 words")
        String fullName,

        @NotBlank(message = "Password must be required")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&]).{8,}$",
                message = "Must containt al least: one number, one lower case letter, one upper case y un special caracter")
        String password,

        @NotNull(message = "DateBirth required")
        @Past(message = "DateBirth must be on the past")
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