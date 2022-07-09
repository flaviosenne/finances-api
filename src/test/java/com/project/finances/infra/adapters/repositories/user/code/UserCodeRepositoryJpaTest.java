package com.project.finances.infra.adapters.repositories.user.code;

import com.project.finances.domain.entity.UserCode;
import com.project.finances.mocks.entity.UserCodeMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class UserCodeRepositoryJpaTest {

    @Mock
    private UserCodeInterfaceJpa jpa;

    UserCodeRepositoryJpa repository;

    @BeforeEach
    void setup(){
        repository = new UserCodeRepositoryJpa(jpa);
    }

    @Test
    @DisplayName("should return a user code when user id is matcher with someone user code in DB")
    void findByUserId(){

        UserCode code = UserCodeMock.get();
        String id = code.getUser().getId();

        when(jpa.findByUserId(id)).thenReturn(Optional.of(code));

        Optional<UserCode> result = repository.findByUserId(id);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(code));

        verify(jpa, times(1)).findByUserId(id);
    }

    @Test
    @DisplayName("should return a user code when code is matcher with someone user code and is valid in DB")
    void findByCodeRetrievePassword(){

        UserCode code = UserCodeMock.get();

        when(jpa.findByCodeRetrievePassword(code.getCode())).thenReturn(Optional.of(code));

        Optional<UserCode> result = repository.findByCodeRetrievePassword(code.getCode());

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(code));

        verify(jpa, times(1)).findByCodeRetrievePassword(code.getCode());
    }

    @Test
    @DisplayName("should return a user code when code is matcher with someone user code and is valid and user is not active in DB")
    void findByCodeActiveAccount(){

        UserCode code = UserCodeMock.get();

        when(jpa.findByCodeActiveAccount(code.getCode())).thenReturn(Optional.of(code));

        Optional<UserCode> result = repository.findByCodeActiveAccount(code.getCode());

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(code));

        verify(jpa, times(1)).findByCodeActiveAccount(code.getCode());
    }

    @Test
    @DisplayName("should save a user code when this provider")
    void save(){

        UserCode code = UserCodeMock.get();

        when(jpa.save(code)).thenReturn(code);

        UserCode result = repository.save(code);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(code);

        verify(jpa, times(1)).save(code);
    }

}