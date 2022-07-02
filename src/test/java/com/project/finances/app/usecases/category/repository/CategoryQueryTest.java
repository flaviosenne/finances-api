package com.project.finances.app.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
import com.project.finances.mocks.entity.CategoryMock;
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
        Category categoryMock = CategoryMock.get();

        String description = "description-example";
        String userId = "user-id-valid";

        when(repository.findCategoryByUserIdAndDescription(userId, description)).thenReturn(Collections.singletonList(categoryMock));

        List<Category> result = query.getCategoriesByUser(userId, description);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1).isEqualTo(Collections.singletonList(categoryMock));

        verify(repository, times(1)).findCategoryByUserIdAndDescription(userId, description);
    }

    @Test
    @DisplayName("Should return a category by id")
    void getCategoryById(){
        Category categoryMock = CategoryMock.get();

        String id = "id-valid";

        when(repository.findById(id)).thenReturn(Optional.of(categoryMock));

        Optional<Category> result = query.getCategoryById(id);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(categoryMock);

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return a category by id and by user id")
    void getCategoryByIdAndByUser(){
        Category categoryMock = CategoryMock.get();

        String id = "id-valid";

        when(repository.findByIdAndByUserId(id, categoryMock.getUser().getId())).thenReturn(Optional.of(categoryMock));

        Optional<Category> result = query.getCategoryByIdAndByUserId(id, categoryMock.getUser().getId());

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(categoryMock);

        verify(repository, times(1)).findByIdAndByUserId(id, categoryMock.getUser().getId());
    }


}