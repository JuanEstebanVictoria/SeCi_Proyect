package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest category);
    CategoryResponse updateCategory(String id,CategoryRequest category);
    List<CategoryResponse> findAllCategories();
    CategoryResponse findCategoryById(String id);
    void deleteCategoryById(String id);
}
