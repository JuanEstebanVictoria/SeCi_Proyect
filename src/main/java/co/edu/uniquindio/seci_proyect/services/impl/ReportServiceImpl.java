package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.Report;
import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportStatusDTO;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.ReportMapper;
import co.edu.uniquindio.seci_proyect.repositories.ReportRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private ReportMapper reportMapper;
    private MongoTemplate mongoTemplate;


    @Override
    public Optional<ReportResponse> getReport(String id) {
        return reportRepository.findReportById(id)
                .map(reportMapper::toReportResponse);
    }

    // Implementa los demás métodos requeridos por la interfaz
    @Override
    public Page<ReportResponse> listReportsByStatus(ReportStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findByStatus(status, pageable)
                .map(reportMapper::toReportResponse);
    }
//completar
    @Override
    public List<ReportResponse> findVerifiedReportsNearUser(String userId, double radiusInKm) {
        return List.of();
    }

    @Override
    public ReportStatusDTO updateReportStatus(String reportId, ReportStatusDTO reportStatusDTO) {
        Report report = reportRepository.findReportById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(reportStatusDTO.newStatus());
        reportRepository.save(report);

        return new ReportStatusDTO(report.getStatus());

    }


    @Override
    public void addVoteToReport(String reportId, String userId) {

    }

    @Override
    public Page<ReportResponse> searchReports(String keyword, List<String> categoryIds, ReportStatus status, int page, int size) {
        return null;
    }
//---
    @Override
    public ReportResponse createReport(ReportRequest report) {
        if(reportRepository.findByTitle(report.title()).isPresent()) {
            throw new ValueConflictException("Title already exists");
        }
        var newReport = reportMapper.parseOf(report);
        return reportMapper.toReportResponse(reportRepository.save(newReport));
    }

    public List<Report> findImportantReportsNearUser(String userId, double radiusInKm) {
        // 1. Obtener ubicación del usuario
        User user = mongoTemplate.findById(userId, User.class);
        if (user == null || user.getLocation() == null) {
            throw new ResourceNotFoundException("Usuario o ubicación no encontrada");
        }

        // 2. Construir consulta geoespacial con criterios adicionales
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("location").nearSphere(user.getLocation())
                        .maxDistance(radiusInKm / 6378.1), // Conversión km a radianes
                Criteria.where("status").is(ReportStatus.VERIFIED),
                Criteria.where("votes").size(5) // Mínimo 5 votos
        );

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "votes"))
                .limit(20);

        return mongoTemplate.find(query, Report.class);
    }

    public List<ReportStats> getReportStatsByCategory(LocalDateTime startDate, LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createdAt").gte(startDate).lte(endDate)),
                Aggregation.unwind("categories"),
                Aggregation.group("categories").count().as("totalReports"),
                Aggregation.project().and("_id").as("categoryId")
                        .and("totalReports").as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count")
        );

        return mongoTemplate.aggregate(aggregation, Report.class, ReportStats.class)
                .getMappedResults();
    }

    @Getter
    @Setter
    public static class ReportStats {
        private String categoryId;
        private long count;
    }

}
