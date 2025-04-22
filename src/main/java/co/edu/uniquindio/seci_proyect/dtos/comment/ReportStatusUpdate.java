package co.edu.uniquindio.seci_proyect.dtos.report;

import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import jakarta.validation.constraints.NotNull;

public record ReportStatusUpdate(
        @NotNull ReportStatus status,
        @NotNull String moderatorId,
        String reason
) {}