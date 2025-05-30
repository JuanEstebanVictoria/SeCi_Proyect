package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportStatusDTO;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    ReportResponse createReport(ReportRequest report);
    Optional<ReportResponse>getReport(String id);
    ReportStatusDTO updateReportStatus(String reportId, ReportStatusDTO reportStatusDTO);
    Page<ReportResponse> listReportsByStatus(ReportStatus status, int page, int size);
    List<ReportResponse> findVerifiedReportsNearUser(String userId, double radiusInKm);
    void addVoteToReport(String reportId, String userId);
    Page<ReportResponse> searchReports(String keyword, List<String> categoryIds, ReportStatus status, int page, int size);
}
