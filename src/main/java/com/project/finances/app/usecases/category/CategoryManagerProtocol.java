package com.project.finances.app.usecases.category;

import com.project.finances.app.usecases.category.dto.CategoryDto;

import java.util.List;

public interface CategoryManagerProtocol {

    CategoryDto create(CategoryDto dto, String userId);

    List<CategoryDto> getCategories(String userId, String description);

    CategoryDto update(CategoryDto dto, String userId);


}
