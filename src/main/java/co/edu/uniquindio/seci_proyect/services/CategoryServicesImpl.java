package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.CategoryMapper;
import co.edu.uniquindio.seci_proyect.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServicesImpl implements CategoryService {

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

    private void validateCategoryName(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if(category.isPresent()) {
            throw new ValueConflictException("Category name already exists");
        }
    }
}
