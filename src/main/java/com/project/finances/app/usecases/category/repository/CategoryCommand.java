package com.project.finances.app.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCommand {
    private final CategoryRepository repository;

    public Category save(Category category) {
        return repository.create(category);
    }
}
