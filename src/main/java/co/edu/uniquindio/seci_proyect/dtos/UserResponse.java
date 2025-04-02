package co.edu.uniquindio.seci_proyect.dtos;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

public record UserResponse(
        @NotBlank(message = "El ID es requerido")
        String id,

        @NotBlank(message = "El email es requerido")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String email,

        @NotBlank(message = "El nombre completo es requerido")
        String fullName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateBirth,

        @NotBlank(message = "El rol es requerido")
        Rol rol,

        @NotBlank(message = "El estado es requerido")
        UserStatus status,

        GeoJsonPoint location
) { }
