package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.Report;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.ReportMapper;
import co.edu.uniquindio.seci_proyect.repositories.ReportRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private ReportMapper reportMapper;

    @Override
    public ReportResponse createReport(ReportRequest report) {
        if(reportRepository.findByTitle(report.title()).isPresent()) {
            throw new ValueConflictException("Title already exists");
        }
        var newReport = reportMapper.parseOf(report);
        return reportMapper.toReportResponse(reportRepository.save(newReport));
    }





}
