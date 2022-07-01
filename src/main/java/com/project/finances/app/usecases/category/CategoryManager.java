package com.project.finances.app.usecases.category;

import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.app.usecases.category.dto.CategoryDto;
import com.project.finances.app.usecases.category.repository.CategoryCommand;
import com.project.finances.app.usecases.category.repository.CategoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.finances.domain.exception.messages.MessagesException.CATEGORY_NOT_FOUND;
import static com.project.finances.domain.exception.messages.MessagesException.CATEGORY_USER_NOT_PROVIDER;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryManagerProtocol {
    private final CategoryQuery query;
    private final CategoryCommand command;
    private final UserQuery userQuery;

    @Override
    public CategoryDto create(CategoryDto dto, String userId) {
        User user = userQuery.findByIdIsActive(userId).orElseThrow(()-> new BadRequestException(CATEGORY_USER_NOT_PROVIDER));

        Category categoryToSave = CategoryDto.of(dto).withUser(user);

        return CategoryDto.of(command.save(categoryToSave));
    }

    @Override
    public List<CategoryDto> getCategories(String userId, String description) {
        return query.getCategoriesByUser(userId, description).stream().map(CategoryDto::of).collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto dto, String userId) {
        Category categoryToUpdate = query.getCategoryByIdAndByUserId(dto.getId(), userId)
                .orElseThrow(()-> new BadRequestException(CATEGORY_NOT_FOUND));

        categoryToUpdate.withDescription(dto.getDescription()).withImage(dto.getImage());

        return CategoryDto.of(command.save(categoryToUpdate));
    }
}
