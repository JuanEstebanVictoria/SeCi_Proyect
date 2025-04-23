package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import co.edu.uniquindio.seci_proyect.dtos.comment.CommentRequest;
import co.edu.uniquindio.seci_proyect.dtos.comment.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "userId", expression = "java(new ObjectId(userId))")
    @Mapping(target = "reportId", expression = "java(new ObjectId(reportId))")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    Comment toComment(CommentRequest commentRequest, String reportId, String userId);

    @Mapping(target = "userId", expression = "java(comment.getUserId().toString())")
    @Mapping(target = "reportId", expression = "java(comment.getReportId().toString())")
    CommentResponse toResponse(Comment comment);
}