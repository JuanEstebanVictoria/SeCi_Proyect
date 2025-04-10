package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;

public interface ReportService {
    ReportResponse createReport(ReportRequest report);

}
