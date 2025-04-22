package co.edu.uniquindio.seci_proyect.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusHistory {
    private ReportStatus status;
    private LocalDateTime date;
    private String changedBy; // ID del usuario que realizó el cambio
    private String reason;
}