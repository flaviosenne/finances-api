package com.project.finances.domain.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQuery {
    private final CategoryRepository repository;

    public List<Category> getCategoriesByUser(String userId) {
        return repository.findCategoryByUserId(userId);
    }
}
