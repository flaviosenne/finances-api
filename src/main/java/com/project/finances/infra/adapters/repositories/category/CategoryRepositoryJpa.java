package com.project.finances.infra.adapters.repositories.category;

import com.project.finances.app.usecases.category.repository.CategoryRepository;
import com.project.finances.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoryRepositoryJpa implements CategoryRepository {

    private final CategoryInterfaceJpa jpa;

    @Override
    public List<Category> findCategoryByUserIdAndDescription(String userId, String description) {
        return jpa.findCategoryByUserId(userId, description);
    }

    @Override
    public Optional<Category> findById(String id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Category> findByIdAndByUserId(String id, String userId) {
        return jpa.findByIdAndByUserId(id, userId);
    }

    @Override
    public Category create(Category category) {
        return jpa.save(category);
    }
}
