package co.edu.uniquindio.seci_proyect.controllers;

import co.edu.uniquindio.seci_proyect.dtos.comment.CommentResponse;
import co.edu.uniquindio.seci_proyect.dtos.comment.*;
import co.edu.uniquindio.seci_proyect.services.interfaces.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/{reportId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable String reportId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body((CommentResponse) commentService.addComment(reportId, request));
    }
    @GetMapping("/{reportId}")
    public ResponseEntity<List<CommentDTO>> getAllComments(
            @PathVariable String reportId) {
        return ResponseEntity.ok(commentService.getAllComments(reportId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}