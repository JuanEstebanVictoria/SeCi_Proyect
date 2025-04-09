package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
}
