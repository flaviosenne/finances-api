package com.project.finances.app.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryQuery {
    private final CategoryRepository repository;

    public List<Category> getCategoriesByUser(String userId, String description) {
        return repository.findCategoryByUserId(userId, description);
    }

    public Optional<Category> getCategoryById(String id) {
        return repository.findById(id);
    }

    public Optional<Category> getCategoryByIdAndByUserId(String id, String userId) {
        return repository.findByIdAndByUserId(id, userId);
    }
}
