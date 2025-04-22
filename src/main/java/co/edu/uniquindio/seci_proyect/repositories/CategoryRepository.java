package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
    List<Category> findByStatusNot(CategoryStatus status);
    List<Category> findByStatus(CategoryStatus status);

    // Consulta para categorías más usadas
    @Aggregation(pipeline = {
            "{ $unwind: '$categories' }",
            "{ $group: { _id: '$categories', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }",
            "{ $limit: 5 }"
    })
    List<CategoryCount> findMostUsedCategories();

    public interface CategoryCount {
        String getId();
        String getName();
        long getCount();
    }
}
