package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.*;
import co.edu.uniquindio.seci_proyect.dtos.comment.*;
import co.edu.uniquindio.seci_proyect.exceptions.*;
import co.edu.uniquindio.seci_proyect.mappers.CommentMapper;
import co.edu.uniquindio.seci_proyect.repositories.CommentRepository;
import co.edu.uniquindio.seci_proyect.repositories.ReportRepository;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.CommentService;
import co.edu.uniquindio.seci_proyect.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private  CommentRepository commentRepository;
    private  ReportRepository reportRepository;
    private  UserRepository userRepository;
    private  CommentMapper commentMapper;
    private EmailService emailService;

    @Override
    @Transactional
    public CommentResponse addComment(String reportId, String userId, CommentRequest commentRequest) {
        // Validate report
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        if (report.getStatus() == ReportStatus.DELETED) {
            throw new BusinessRuleException("Comment already deleted");
        }

        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AuthorizationException("User not active");
        }

        // Create comments by mapper
        Comment comment = commentMapper.toComment(commentRequest, reportId, userId);

        // save
        Comment savedComment = commentRepository.save(comment);

        // Notificar al creador del reporte, si no es el mismo usuario
        if (!report.getUserId().equals(new ObjectId(userId))) {
            emailService.sendCommentNotification(
                    report.getUserId().toString(),
                    report.getTitle(),
                    comment.getContent()
            );
        }

        return commentMapper.toResponse(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public Page<CommentResponse> getCommentsByReport(String reportId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByReportId(new ObjectId(reportId), pageable)
                .map(commentMapper::toResponse);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(String commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toResponse(updatedComment);
    }



    //completar

    @Override
    public Object addComment(String reportId, CommentRequest request) {
        return null;
    }

    @Override
    public List<CommentDTO> getAllComments(String idReport) {
        Report report= reportRepository.findReportById(idReport)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));




        return report.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getContent()
                ) ).toList();
    }
}