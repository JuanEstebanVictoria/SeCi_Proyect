package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.dtos.comment.CommentRequest;
import co.edu.uniquindio.seci_proyect.dtos.comment.CommentResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(String reportId, String userId, CommentRequest commentRequest);
    void deleteComment(String commentId);
    Page<CommentResponse> getCommentsByReport(String reportId, int page, int size);
    CommentResponse updateComment(String commentId, String newContent);
    Object addComment(String reportId, @Valid CommentRequest request);

    List<CommentResponse> getAllComments(String idReport);

}