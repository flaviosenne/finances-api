package com.project.finances.app.usecases.category.dto;

import com.project.finances.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CategoryDto {
    private String id;
    private String description;

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .description(category.getDescription())
                .build();
    }

    public static Category of(CategoryDto dto){
        return Category.builder()
                .description(dto.getDescription())
                .build();
    }
}
