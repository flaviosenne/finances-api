package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserQueryTest {

    @Mock
    private UserRepository repository;

    private UserQuery userQuery;

    @BeforeEach
    void setup(){
        userQuery = new UserQuery(repository);
    }

    @Test
    @DisplayName("Should return a optional user when id is provider")
    void findById(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        when(repository.findById(anyString())).thenReturn(Optional.of(userMock));

        String idUser = "id-valid";

        Optional<User> result = userQuery.findById(idUser);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(userMock);

        verify(repository, times(1)).findById(idUser);
    }

    @Test
    @DisplayName("Should return a optional user when email is provider")
    void findByUsername(){
        String email = "example@email.com";
        User userMock = new User(email, "first-name", "last-name", "hash", true);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(userMock));

        Optional<User> result = userQuery.findByUsername(email);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(userMock);

        verify(repository, times(1)).findByEmail(email);
    }

}