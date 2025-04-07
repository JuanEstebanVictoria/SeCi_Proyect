package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.controllers.CategoryServices;
import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.CategoryMapper;
import co.edu.uniquindio.seci_proyect.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServicesImpl implements CategoryServices {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponse save(CategoryRequest category) {
        var newCategory = categoryMapper.parseOf(category);
        validateCategoryName(category.name());
        return categoryMapper.toCategoryResponse(
                categoryRepository.save(newCategory)
        );
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest category) {
        var updateCategory = findCategoryById(id);
        updateCategory.setName(category.name());
        if(!updateCategory.getName().equals(category.name())){
            validateCategoryName(category.name());
        }
        updateCategory.setDescription(category.description());
        return categoryMapper.toCategoryResponse(
                categoryRepository.save(updateCategory)
        );

    }

    @Override
    public List<CategoryResponse> findAllCategories() {
        return List.of();
    }

    @Override
    public CategoryResponse findCategoryById(String id) {
        return null;
    }

    @Override
    public void deleteCategoryById(String id) {

    }
    private void validateCategoryName(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if(category.isPresent()) {
            throw new ValueConflictException("Category name already exists");
        }
    }
}
