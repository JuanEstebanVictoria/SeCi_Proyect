package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
    List<Category> findByStatusNot(CategoryStatus status);
}
