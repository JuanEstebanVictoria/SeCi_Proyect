package co.edu.uniquindio.seci_proyect.dtos.category;

import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import jakarta.validation.constraints.NotBlank;

public record CategoryResponse(
        @NotBlank(message = "Id required")
        String id,
        @NotBlank(message = "Name is required")
        String name,
        String description,
        CategoryStatus status
) {}
