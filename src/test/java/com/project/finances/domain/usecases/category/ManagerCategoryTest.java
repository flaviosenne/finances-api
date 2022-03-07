package com.project.finances.domain.usecases.category;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CategoryManagerProtocol;
import com.project.finances.domain.usecases.category.dto.CategoryDto;
import com.project.finances.domain.usecases.category.repository.CategoryCommand;
import com.project.finances.domain.usecases.category.repository.CategoryQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ManagerCategoryTest {
    @Mock
    private CategoryQuery categoryQuery;
    @Mock
    private CategoryCommand categoryCommand;
    @Mock
    private UserQuery userQuery;

    private CategoryManagerProtocol categoryManagerProtocol;

    @BeforeEach
    void setup(){
        categoryManagerProtocol = new ManagerCategory(categoryQuery, categoryCommand, userQuery);
    }

    // todo create
    @Test
    @DisplayName("Should create a category when request is successful")
    void createCategory(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        CategoryDto dto = new CategoryDto(null, categoryMock.getDescription());
        String userId = "user-id-valid";


        when(userQuery.findById(userId)).thenReturn(Optional.of(userMock));
        when(categoryCommand.create(any(Category.class))).thenReturn(categoryMock);

        CategoryDto result = categoryManagerProtocol.create(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(userQuery, times(1)).findById(userId);
        verify(categoryCommand, times(1)).create(any(Category.class));

    }

    @Test
    @DisplayName("Should throw bad request exception when user do not exist in Db")
    void notCreateCategory(){
        CategoryDto dto = new CategoryDto(null, "categoryMock");
        String userId = "user-id-invalid";

        when(userQuery.findById(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->categoryManagerProtocol.create(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não informado para categoria");

        verify(userQuery, times(1)).findById(userId);
        verify(categoryCommand, never()).create(any(Category.class));

    }

    // todo list
    @Test
    @DisplayName("Should return a list categories by user id when request is successful")
    void getCategories(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        String userId = "user-id-valid";


        when(categoryQuery.getCategoriesByUser(userId)).thenReturn(List.of(categoryMock));

        List<CategoryDto> result = categoryManagerProtocol.getCategories(userId);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1);

        verify(categoryQuery, times(1)).getCategoriesByUser(userId);

    }


}