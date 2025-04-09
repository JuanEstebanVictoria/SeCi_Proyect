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
                            List<String> urlsImages
                            ) {
    public ReportRequest{
        urlsImages= urlsImages != null ? urlsImages : new ArrayList<>();
    }
}
