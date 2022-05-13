package com.project.finances.domain.usecases.category.repository;

import com.project.finances.app.usecases.category.repository.CategoryCommand;
import com.project.finances.app.usecases.category.repository.CategoryRepository;
import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.User;
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
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);

        when(repository.save(any(Category.class))).thenReturn(categoryMock);

        Category result = command.save(categoryMock);

        BDDAssertions.assertThat(result).isEqualTo(categoryMock);

        verify(repository, times(1)).save(categoryMock);
    }
}