package co.edu.uniquindio.seci_proyect.services.integration;


import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.services.interfaces.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private CategoryRequest testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new CategoryRequest(
                "Test Category",
                "Test Description",
                CategoryStatus.ACTIVE
        );
    }

    @Test
    void testSaveCategory() {
        CategoryResponse response = categoryService.save(testCategory);

        assertNotNull(response.id());
        assertEquals(testCategory.name(), response.name());
        assertEquals(testCategory.description(), response.description());
    }

    @Test
    void testSaveCategoryWithExistingName() {
        categoryService.save(testCategory);

        CategoryRequest duplicate = new CategoryRequest(
                testCategory.name(),
                "Different description",
                CategoryStatus.ACTIVE
        );

        assertThrows(ValueConflictException.class,
                () -> categoryService.save(duplicate));
    }

    @Test
    void testUpdateCategory() {
        // Crear categoría inicial
        CategoryResponse created = categoryService.save(testCategory);

        // Actualizar categoría
        CategoryRequest updateRequest = new CategoryRequest(
                "Updated Name",
                "Updated Description",
                CategoryStatus.ACTIVE
        );

        CategoryResponse updated = categoryService.update(created.id(), updateRequest);

        assertEquals(created.id(), updated.id());
        assertEquals("Updated Name", updated.name());
        assertEquals("Updated Description", updated.description());
    }

    @Test
    void testUpdateNonExistingCategory() {
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.update("nonexisting", testCategory));
    }

    @Test
    void testFindById() {
        CategoryResponse created = categoryService.save(testCategory);
        CategoryResponse found = categoryService.findById(created.id());

        assertEquals(created.id(), found.id());
        assertEquals(created.name(), found.name());
    }

    @Test
    void testFindAll() {
        categoryService.save(testCategory);
        List<CategoryResponse> allCategories = categoryService.findAll();

        assertFalse(allCategories.isEmpty());
    }

    @Test
    void testFindAllActive() {
        // Crear una categoría activa y una inactiva
        categoryService.save(testCategory);
        categoryService.save(new CategoryRequest(
                "Inactive Category",
                "Description",
                CategoryStatus.DELETED
        ));

        List<CategoryResponse> activeCategories = categoryService.findAllActive();

        assertFalse(activeCategories.isEmpty());
        assertTrue(activeCategories.stream()
                .allMatch(c -> c.status() == CategoryStatus.ACTIVE));
    }

    @Test
    void testDeleteById() {
        CategoryResponse created = categoryService.save(testCategory);
        assertDoesNotThrow(() -> categoryService.deleteById(created.id()));

        // Verificar que quedó marcada como eliminada
        CategoryResponse deleted = categoryService.findById(created.id());
        assertEquals(CategoryStatus.DELETED, deleted.status());
    }
}