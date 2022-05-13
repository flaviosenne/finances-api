package com.project.finances.domain.usecases.release.repository;

import com.project.finances.app.usecases.release.repository.ReleaseCommand;
import com.project.finances.app.usecases.release.repository.ReleaseRepository;
import com.project.finances.domain.entity.*;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReleaseCommandTest {

    @Mock
    private ReleaseRepository repository;

    private ReleaseCommand command;

    @BeforeEach
    void setup(){
        command = new ReleaseCommand(repository);
    }

    @Test
    @DisplayName("Should save release when request is success")
    void save(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        Release releaseMock = new Release(100d, "test", StatusRelease.PENDING, TypeRelease.EXPENSE, new Date(), categoryMock, userMock);

        when(repository.save(releaseMock)).thenReturn(releaseMock);

        Release result = command.create(releaseMock);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(releaseMock);

        verify(repository, times(1)).save(releaseMock);
    }


    @Test
    @DisplayName("Should update release when request is success")
    void update(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        Release releaseMock = new Release(100d, "test", StatusRelease.PENDING, TypeRelease.EXPENSE, new Date(), categoryMock, userMock);

        when(repository.save(releaseMock)).thenReturn(releaseMock);

        Release result = command.update(releaseMock, userMock.getId());

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(releaseMock);
        BDDAssertions.assertThat(result.getId()).isNotNull().isEqualTo(userMock.getId());

        verify(repository, times(1)).save(releaseMock);
    }

}