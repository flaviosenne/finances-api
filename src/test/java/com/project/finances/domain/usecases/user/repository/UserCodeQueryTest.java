package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserCode;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserCodeQueryTest {

    @Mock
    private UserCodeRepository repository;

    private UserCodeQuery userCodeQuery;

    @BeforeEach
    void setup(){
        userCodeQuery = new UserCodeQuery(repository);
    }

    @Test
    @DisplayName("Should return a optional UserCode when code (id) is provider")
    void findByCode(){
        UserCode userCodeMock = new UserCode(User.builder().build(), true, "");

        String code = userCodeMock.getId();

        when(repository.findByCodeActiveAccount(anyString())).thenReturn(Optional.of(userCodeMock));

        Optional<UserCode> result = userCodeQuery.findByCodeToActiveAccount(code);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get().getId()).isEqualTo(code);

        verify(repository, times(1)).findByCodeActiveAccount(code);
    }

}