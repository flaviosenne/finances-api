package com.project.finances.app.usecases.release.repository;

import com.project.finances.domain.entity.*;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.infra.adapters.repositories.release.ReleaseRepositoryJpa;
import com.project.finances.mocks.entity.ReleaseMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReleaseCommandTest {

    @Mock
    private ReleaseRepositoryJpa repository;

    private ReleaseCommand command;

    @BeforeEach
    void setup(){
        command = new ReleaseCommand(repository);
    }

    @Test
    @DisplayName("Should save release when request is success")
    void save(){
        Release releaseMock = ReleaseMock.get();

        when(repository.save(releaseMock)).thenReturn(releaseMock);

        Release result = command.create(releaseMock);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(releaseMock);

        verify(repository, times(1)).save(releaseMock);
    }


    @Test
    @DisplayName("Should update release when request is success")
    void update(){
        Release releaseMock = ReleaseMock.get();
        String id = "id";

        when(repository.save(releaseMock)).thenReturn(releaseMock.withId(id));

        Release result = command.update(releaseMock, id);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(releaseMock);
        BDDAssertions.assertThat(result.getId()).isNotNull().isEqualTo(id);

        verify(repository, times(1)).save(releaseMock);
    }

    @Test
    @DisplayName("Should delete release when request is success")
    void delete(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(repository.findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId)).thenReturn(Optional.of(releaseMock));

        command.delete(releaseMock.getId(), userId);

        verify(repository, times(1)).findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId);
        verify(repository, times(1)).save(releaseMock.disable());
    }


    @Test
    @DisplayName("Should throw bad request exception when do not exists release ein DB")
    void deleteNotFound(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(repository.findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->command.delete(releaseMock.getId(), userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Lançamento não encontrado");

        verify(repository, times(1)).findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId);
        verify(repository, never()).save(any(Release.class));
    }

}