package com.project.finances.app.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import com.project.finances.mocks.entity.CategoryMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryCommandTest {

    @Mock
    private CategoryRepository repository;

    private CategoryCommand command;

    @BeforeEach
    void setup(){
        command = new CategoryCommand(repository);
    }

    @Test
    @DisplayName("Should save category when category is provider")
    void save(){
        Category categoryMock = CategoryMock.get();

        when(repository.create(any(Category.class))).thenReturn(categoryMock);

        Category result = command.save(categoryMock);

        BDDAssertions.assertThat(result).isEqualTo(categoryMock);

        verify(repository, times(1)).create(categoryMock);
    }
}