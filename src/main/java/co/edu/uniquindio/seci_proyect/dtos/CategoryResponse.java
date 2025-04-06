package co.edu.uniquindio.seci_proyect.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryResponse(
        @NotBlank
        String id,
        @NotBlank
        String name,
        String description
) {
}
