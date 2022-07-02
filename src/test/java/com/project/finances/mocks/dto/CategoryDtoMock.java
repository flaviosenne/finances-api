package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.category.dto.CategoryDto;
import com.project.finances.mocks.entity.CategoryMock;

public class CategoryDtoMock {
    public static CategoryDto get(){
        return new CategoryDto(CategoryMock.get().getId(),
                CategoryMock.get().getDescription(),
                CategoryMock.get().getImage());
    }
}
