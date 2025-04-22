package co.edu.uniquindio.seci_proyect.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentDTO(
        @NotBlank
        @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
        String content,

        @NotNull
        String reportId,

        @NotNull
        String userId
) {}