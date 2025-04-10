package co.edu.uniquindio.seci_proyect.dtos.report;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import co.edu.uniquindio.seci_proyect.Model.Vote;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryResponse;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResponse(
        String id,
        String title,
        List<CategoryResponse> categories,
        String description,
        Double latitude,
        Double longitude,
        List<String> urlsImages,
        ReportStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ObjectId userId,
        List<Comment> comments,
        List<Vote> votes
) {}