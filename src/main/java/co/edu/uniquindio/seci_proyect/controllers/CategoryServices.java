package co.edu.uniquindio.seci_proyect.controllers;

import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;

import java.util.List;

public interface CategoryServices {
    CategoryResponse save(CategoryRequest category);
    CategoryResponse update(String id, CategoryRequest category);
    List<CategoryResponse> findAllCategories();
    CategoryResponse findCategoryById(String id);
    void deleteCategoryById(String id);
}
