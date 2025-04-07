package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest category);
    CategoryResponse update(String id,CategoryRequest category);
    List<CategoryResponse> findAll();
    CategoryResponse findById(String id);
    void deleteById(String id);
}
