package co.edu.uniquindio.seci_proyect.dtos.comment;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record CommentResponse(
        ObjectId userId,
        ObjectId reportId,
        String content,
        LocalDateTime date
) {}