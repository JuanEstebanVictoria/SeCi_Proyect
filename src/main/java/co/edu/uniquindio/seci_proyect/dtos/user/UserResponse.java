package co.edu.uniquindio.seci_proyect.dtos.user;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

public record UserResponse(
        @NotBlank(message = "Id required")
        String id,

        @NotBlank(message = "Email required")
        @Email
        String email,

        @NotBlank(message = "Fullname is required")

        String fullName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateBirth,

        Rol rol,

        GeoJsonPoint location
) {

}
