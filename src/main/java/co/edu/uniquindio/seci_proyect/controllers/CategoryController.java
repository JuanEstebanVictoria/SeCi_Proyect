package co.edu.uniquindio.seci_proyect.controllers;

import co.edu.uniquindio.seci_proyect.dtos.category.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.category.CategoryResponse;
import co.edu.uniquindio.seci_proyect.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryServices;

    @PostMapping
    public CategoryResponse create(@Valid CategoryRequest  category) {
        return categoryServices.save(category);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryServices.findAll();
    }

    @GetMapping("/active")
    public List<CategoryResponse> getAllActive() {
        return categoryServices.findAllActive();
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable String id, @Valid CategoryRequest category) {
        return categoryServices.update(id,category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        categoryServices.deleteById(id);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable("id")String id) {
        return categoryServices.findById(id);
    }
/**
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllActiveCategories() {
        List<CategoryResponse> categories = categoryServices.findAllActive();
        return ResponseEntity.ok(categories);
    }**/
}
