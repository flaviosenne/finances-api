package com.project.finances.app.usecases.category;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.app.usecases.category.dto.CategoryDto;
import com.project.finances.app.usecases.category.repository.CategoryCommand;
import com.project.finances.app.usecases.category.repository.CategoryQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryManagerTest {
    @Mock
    private CategoryQuery categoryQuery;
    @Mock
    private CategoryCommand categoryCommand;
    @Mock
    private UserQuery userQuery;

    private CategoryManagerProtocol categoryManagerProtocol;

    @BeforeEach
    void setup(){
        categoryManagerProtocol = new CategoryManager(categoryQuery, categoryCommand, userQuery);
    }

    // todo create
    @Test
    @DisplayName("Should create a category when request is successful")
    void createCategory(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category(null, "category 1", userMock);
        CategoryDto dto = new CategoryDto(null, categoryMock.getDescription(), categoryMock.getImage());
        String userId = "user-id-valid";


        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(userMock));
        when(categoryCommand.save(any(Category.class))).thenReturn(categoryMock);

        CategoryDto result = categoryManagerProtocol.create(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryCommand, times(1)).save(any(Category.class));

    }

    @Test
    @DisplayName("Should throw bad request exception when user do not exist in Db")
    void notCreateCategory(){
        CategoryDto dto = new CategoryDto(null, "categoryMock", null);
        String userId = "user-id-invalid";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.create(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não informado para categoria");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryCommand, never()).save(any(Category.class));

    }

    // todo list
    @Test
    @DisplayName("Should return a list categories by user id when request is successful")
    void getCategories(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category(null, "category 1", userMock);
        String userId = "user-id-valid";
        String description = "example-description";


        when(categoryQuery.getCategoriesByUser(userId, description)).thenReturn(Collections.singletonList(categoryMock));

        List<CategoryDto> result = categoryManagerProtocol.getCategories(userId,description);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1);

        verify(categoryQuery, times(1)).getCategoriesByUser(userId, description);

    }

    // todo update
    @Test
    @DisplayName("Should update a category when request is successful")
    void updateCategory(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category(null, "category 1", userMock);
        CategoryDto dto = new CategoryDto("id-valid", categoryMock.getDescription(), categoryMock.getImage());
        String userId = "user-id-valid";

        when(categoryQuery.getCategoryByIdAndByUserId(dto.getId(), userId)).thenReturn(Optional.of(categoryMock));
        when(categoryCommand.save(any(Category.class))).thenReturn(categoryMock);

        CategoryDto result = categoryManagerProtocol.update(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(dto.getId(),userId);
        verify(categoryCommand, times(1)).save(any(Category.class));

    }
    @Test
    @DisplayName("Should throw bad request exception when try update a category nad not found resource in DB")
    void notUpdateCategory(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category(null, "category 1", userMock);
        CategoryDto dto = new CategoryDto("id-invalid", categoryMock.getDescription(), categoryMock.getImage());
        String userId = "user-id-invalid";

        when(categoryQuery.getCategoryByIdAndByUserId(categoryMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.update(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não encontrada");

        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(dto.getId(),userId);
        verify(categoryCommand, never()).save(any(Category.class));

    }


}