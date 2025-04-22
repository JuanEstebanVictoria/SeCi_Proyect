package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ReportService {
    ReportResponse createReport(ReportRequest report);
    ReportResponse getReport(String id);
    Page<ReportResponse> listReportsByStatus(ReportStatus status, int page, int size);
    List<ReportResponse> findVerifiedReportsNearUser(String userId, double radiusInKm);
    ReportResponse updateReportStatus(String reportId, ReportStatus newStatus, String moderatorId, String reason);
    void addVoteToReport(String reportId, String userId);
    Page<ReportResponse> searchReports(String keyword, List<String> categoryIds, ReportStatus status, int page, int size);
}
