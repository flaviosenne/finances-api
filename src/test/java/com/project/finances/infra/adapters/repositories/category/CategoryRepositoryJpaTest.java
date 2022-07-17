package com.project.finances.infra.adapters.repositories.category;

import com.project.finances.domain.entity.Category;
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
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CategoryRepositoryJpaTest {

    @Mock
    private CategoryInterfaceJpa jpa;

    private CategoryRepositoryJpa repository;


    @BeforeEach
    void setup(){
        repository = new CategoryRepositoryJpa(jpa);
    }

    @Test
    @DisplayName("should return a category when user id and description is provider and is match with someone result in DB")
    void findBankByUserIdAndDescription(){
        Category categoryDb = CategoryMock.get();
        String userId = categoryDb.getUser().getId();
        String description = categoryDb.getDescription();
        when(jpa.findCategoryByUserId(userId, description)).thenReturn(Collections.singletonList(categoryDb));

        List<Category> result = repository.findCategoryByUserIdAndDescription(userId, description);

        BDDAssertions.assertThat(result).isNotNull().isNotEmpty();
        BDDAssertions.assertThat(result).isEqualTo(Collections.singletonList(categoryDb));

        verify(jpa, times(1)).findCategoryByUserId(userId, description);
    }

    @Test
    @DisplayName("should return a category when id is provider and is match with someone result in DB")
    void findById(){
        Category categoryDb = CategoryMock.get();
        String id = categoryDb.getId();
        when(jpa.findById(id)).thenReturn(Optional.of(categoryDb));

        Optional<Category> result = repository.findById(id);

        BDDAssertions.assertThat(result).isNotNull().isPresent();
        BDDAssertions.assertThat(result).isEqualTo(Optional.of(categoryDb));

        verify(jpa, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return a category when id and user id is provider and is match with someone result in DB")
    void findByIdAndByUserId(){
        Category categoryDb = CategoryMock.get();
        String userId = categoryDb.getUser().getId();
        String id = categoryDb.getId();
        when(jpa.findByIdAndByUserId(id, userId)).thenReturn(Optional.of(categoryDb));

        Optional<Category> result = repository.findByIdAndByUserId(id, userId);

        BDDAssertions.assertThat(result).isNotNull().isPresent();
        BDDAssertions.assertThat(result).isEqualTo(Optional.of(categoryDb));

        verify(jpa, times(1)).findByIdAndByUserId(id, userId);
    }

    @Test
    @DisplayName("should save a category when this a provider")
    void create(){
        Category categoryDb = CategoryMock.get();
        when(jpa.save(categoryDb)).thenReturn(categoryDb);

        Category result = repository.create(categoryDb);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(categoryDb);

        verify(jpa, times(1)).save(categoryDb);
    }
}