package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import co.edu.uniquindio.seci_proyect.dtos.comment.CommentResponse;

public interface CommentMapper {
    CommentResponse toResponse(Comment comment);
}