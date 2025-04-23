package co.edu.uniquindio.seci_proyect.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotBlank String content
) {
}
