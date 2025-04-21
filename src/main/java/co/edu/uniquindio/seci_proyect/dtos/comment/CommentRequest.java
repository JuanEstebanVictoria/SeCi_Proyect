package co.edu.uniquindio.seci_proyect.dtos.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank String content
) {
}
