package com.project.finances.app.usecases.category;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.app.usecases.category.dto.CategoryDto;
import com.project.finances.app.usecases.category.repository.CategoryCommand;
import com.project.finances.app.usecases.category.repository.CategoryQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.mocks.dto.CategoryDtoMock;
import com.project.finances.mocks.entity.CategoryMock;
import com.project.finances.mocks.entity.UserMock;
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
    private CategoryQuery query;
    @Mock
    private CategoryCommand command;
    @Mock
    private UserQuery userQuery;

    private CategoryManagerProtocol categoryManagerProtocol;

    @BeforeEach
    void setup(){
        categoryManagerProtocol = new CategoryManager(query, command, userQuery);
    }

    // todo create
    @Test
    @DisplayName("Should create a category when request is successful")
    void createCategory(){
        User userMock = UserMock.get();
        Category categoryMock = CategoryMock.get();
        CategoryDto dto = CategoryDtoMock.get();
        String userId = "user-id-valid";


        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(userMock));
        when(command.save(any(Category.class))).thenReturn(categoryMock);

        CategoryDto result = categoryManagerProtocol.create(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(command, times(1)).save(any(Category.class));

    }

    @Test
    @DisplayName("Should throw bad request exception when user do not exist in Db")
    void notCreateCategory(){
        CategoryDto dto = CategoryDtoMock.get();
        String userId = "user-id-invalid";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.create(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não informado para categoria");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(command, never()).save(any(Category.class));

    }

    // todo list
    @Test
    @DisplayName("Should return a list categories by user id when request is successful")
    void getCategories(){
        Category categoryMock = CategoryMock.get();
        String userId = "user-id-valid";
        String description = "example-description";


        when(query.getCategoriesByUser(userId, description)).thenReturn(Collections.singletonList(categoryMock));

        List<CategoryDto> result = categoryManagerProtocol.getCategories(userId,description);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1);

        verify(query, times(1)).getCategoriesByUser(userId, description);

    }

    // todo update
    @Test
    @DisplayName("Should update a category when request is successful")
    void updateCategory(){
        Category categoryMock = CategoryMock.get();
        CategoryDto dto = CategoryDtoMock.get();
        String userId = "user-id-valid";

        when(query.getCategoryByIdAndByUserId(dto.getId(), userId)).thenReturn(Optional.of(categoryMock));
        when(command.save(any(Category.class))).thenReturn(categoryMock);

        CategoryDto result = categoryManagerProtocol.update(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(query, times(1)).getCategoryByIdAndByUserId(dto.getId(),userId);
        verify(command, times(1)).save(any(Category.class));

    }
    @Test
    @DisplayName("Should throw bad request exception when try update a category nad not found resource in DB")
    void notUpdateCategory(){
        Category categoryMock = CategoryMock.get();
        CategoryDto dto = CategoryDtoMock.get();
        String userId = "user-id-invalid";

        when(query.getCategoryByIdAndByUserId(categoryMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.update(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não encontrada");

        verify(query, times(1)).getCategoryByIdAndByUserId(dto.getId(),userId);
        verify(command, never()).save(any(Category.class));

    }

    // todo delete
    @Test
    @DisplayName("Should disable a category when request is successful")
    void deleteCategory(){
        Category categoryMock = CategoryMock.get();
        String id = "id-valid";
        String userId = "user-id-valid";

        when(query.getCategoryByIdAndByUserId(id, userId)).thenReturn(Optional.of(categoryMock));
        when(command.save(any(Category.class))).thenReturn(categoryMock);

        categoryManagerProtocol.delete(id, userId);

        verify(query, times(1)).getCategoryByIdAndByUserId(id,userId);
        verify(command, times(1)).save(any(Category.class));

    }
    @Test
    @DisplayName("Should throw bad request exception when try delete a category nad not found resource in DB")
    void notDeleteCategory(){
        Category categoryMock = CategoryMock.get();
        String id = "id-invalid";
        String userId = "user-id-invalid";

        when(query.getCategoryByIdAndByUserId(categoryMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.delete(id, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não encontrada");

        verify(query, times(1)).getCategoryByIdAndByUserId(id,userId);
        verify(command, never()).save(any(Category.class));

    }


}