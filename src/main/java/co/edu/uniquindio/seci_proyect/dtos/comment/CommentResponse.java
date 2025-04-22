package co.edu.uniquindio.seci_proyect.dtos.comment;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record CommentResponse(
        String id,
        String content,
        LocalDateTime date,
        String authorName,
        String authorId
) {}