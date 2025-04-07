package co.edu.uniquindio.seci_proyect.dtos.report;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import co.edu.uniquindio.seci_proyect.Model.Vote;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record ReportRequest(@NotBlank
                            String title,
                            @NotEmpty
                            List<String> categoryIds,
                            @NotBlank
                            String description,
                            @NotNull
                            Double latitude,
                            @NotNull
                            Double longitude,
                            List<String> urlsImages,
                            ReportStatus status,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            List<Vote> votes,
                            ObjectId userId,
                            List<Comment> comments


                            ) {
    public ReportRequest{
        status = Objects.requireNonNullElse(status, ReportStatus.PENDING);
        createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
        votes = votes != null ? votes : new ArrayList<>();
        comments = comments != null ? comments : new ArrayList<>();

    }
}
