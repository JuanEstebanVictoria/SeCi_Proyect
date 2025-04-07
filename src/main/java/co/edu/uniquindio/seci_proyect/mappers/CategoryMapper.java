package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Category;
import co.edu.uniquindio.seci_proyect.dtos.CategoryRequest;
import co.edu.uniquindio.seci_proyect.dtos.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    Category parseOf(CategoryRequest categoryRequest);
    CategoryResponse toCategoryResponse(Category category);
}
