package com.project.finances.domain.protocols;

import com.project.finances.domain.usecases.category.dto.CategoryDto;

import java.util.List;

public interface CategoryManagerProtocol {

    CategoryDto create(CategoryDto dto, String userId);

    List<CategoryDto> getCategories(String userId);

    CategoryDto update(CategoryDto dto, String userId);


}
