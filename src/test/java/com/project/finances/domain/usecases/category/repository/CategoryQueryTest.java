package com.project.finances.domain.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryQueryTest {

    @Mock
    private CategoryRepository repository;

    private CategoryQuery query;

    @BeforeEach
    void setup(){
        query = new CategoryQuery(repository);
    }

    @Test
    @DisplayName("Should return a list of categories by user id")
    void getCategories(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);

        String userId = "user-id-valid";

        when(repository.findCategoryByUserId(userId)).thenReturn(List.of(categoryMock));

        List<Category> result = query.getCategoriesByUser(userId);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1).isEqualTo(List.of(categoryMock));

        verify(repository, times(1)).findCategoryByUserId(userId);
    }

    @Test
    @DisplayName("Should return a category by id")
    void getCategoryById(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);

        String id = "id-valid";

        when(repository.findById(id)).thenReturn(Optional.of(categoryMock));

        Optional<Category> result = query.getCategoryById(id);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(categoryMock);

        verify(repository, times(1)).findById(id);
    }
}