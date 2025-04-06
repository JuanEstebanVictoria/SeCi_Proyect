package co.edu.uniquindio.seci_proyect.dtos;

import jakarta.validation.constraints.NotBlank;


public record CategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
