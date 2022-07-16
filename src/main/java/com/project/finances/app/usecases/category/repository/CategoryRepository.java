package com.project.finances.app.usecases.category.repository;

import com.project.finances.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findCategoryByUserIdAndDescription(String userId, String description);
    Optional<Category> findById(String id);

    Optional<Category> findByIdAndByUserId(String id, String userId);

    Category create(Category category);
}
