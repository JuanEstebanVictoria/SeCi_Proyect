package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryResponse;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryWithStatsResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.CategoryMapper;
import co.edu.uniquindio.seci_proyect.repositories.CategoryRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

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
    public CategoryResponse findById(String id) {
        return categoryMapper.toCategoryResponse(findCategoryById(id));
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    private Category findCategoryById(String id){
        var storedCategory = categoryRepository.findById(id);
//        if(storedCategory.isEmpty()) {
//            throw new ResourceNotFoundException();
//        }
        return storedCategory.orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(String id) {
        var categoryStored = findCategoryById(id);
        categoryStored.setStatus(CategoryStatus.DELETED);
        categoryRepository.save(categoryStored);
    }

    @Override
    public List<CategoryResponse> findAllActive() {
        return categoryRepository.findByStatus(CategoryStatus.ACTIVE).stream()
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName(), cat.getDescription(), cat.getStatus()))
                .toList();
    }

    @Override
    public Page<CategoryWithStatsResponse> findAllWithStats(int page, int size) {
        return null;
    }

    private void validateCategoryName(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if(category.isPresent()) {
            throw new ValueConflictException("Category name already exists");
        }
    }
}
