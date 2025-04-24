package co.edu.uniquindio.seci_proyect.repositories;


import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import co.edu.uniquindio.seci_proyect.data.TestDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        testCategory = new Category();
        testCategory.setName("Test Category");
        testCategory.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(testCategory);
    }

    @Test
    void findByName_ExistingName_ReturnsCategory() {
        Optional<Category> result = categoryRepository.findByName(testCategory.getName());
        assertTrue(result.isPresent());
        assertEquals(testCategory.getName(), result.get().getName());
    }

    @Test
    void findByStatus_ActiveStatus_ReturnsCategories() {
        List<Category> result = categoryRepository.findByStatus(CategoryStatus.ACTIVE);
        assertFalse(result.isEmpty());
        assertEquals(testCategory.getStatus(), result.get(0).getStatus());
    }



    @Test
    void findMostUsedCategories_ReturnsCategoryCounts() {
        List<CategoryRepository.CategoryCount> result = categoryRepository.findMostUsedCategories();
        assertNotNull(result);
    }
}