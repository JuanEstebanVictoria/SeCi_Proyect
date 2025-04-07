package co.edu.uniquindio.seci_proyect.dtos.category;

import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


public record CategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,

        CategoryStatus status
) {
        public CategoryRequest{
                status = Objects.requireNonNullElse(status, CategoryStatus.ACTIVE);
        }
}
