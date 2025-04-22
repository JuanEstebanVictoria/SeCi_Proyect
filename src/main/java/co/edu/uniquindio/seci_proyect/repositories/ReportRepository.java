package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Report;
import co.edu.uniquindio.seci_proyect.Model.ReportStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    // Consultas básicas
    Optional<Report> findByTitle(String title);
    Optional<Report> findReportById(String id);
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByUserId(ObjectId userId);
    List<Report> findByCategories_Id(String categoryId);

    // Consultas geoespaciales
    @Query("{'location': {$geoWithin: {$centerSphere: [[?0, ?1], ?2]}}}")
    List<Report> findReportsNearLocation(double longitude, double latitude, double radiusInRadians);

    // Consultas combinadas
    @Query("{'status': ?0, 'location': {$geoWithin: {$centerSphere: [[?1, ?2], ?3]}}}")
    List<Report> findReportsByStatusNearLocation(
            ReportStatus status,
            double longitude,
            double latitude,
            double radiusInRadians
    );

    // Consultas con paginación
    Page<Report> findByStatus(ReportStatus status, Pageable pageable);

    // Consulta para reportes con más votos
    @Query(sort = "{'votes': -1}")
    List<Report> findTop10ByStatusOrderByVotesDesc(ReportStatus status);

    // Consulta para historial de estados (usando agregación)
    @Aggregation(pipeline = {
            "{ $match: { '_id': ?0 } }",
            "{ $project: { 'statusHistory': 1 } }"
    })
    Optional<Report> findStatusHistoryByReportId(String reportId);

    // Consulta para reportes con imágenes
    @Query("{'urlsImages': { $exists: true, $not: { $size: 0 } } }")
    List<Report> findReportsWithImages();
}
