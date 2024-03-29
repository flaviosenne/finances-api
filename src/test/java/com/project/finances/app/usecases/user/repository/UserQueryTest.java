package com.project.finances.app.usecases.user.repository;

import com.project.finances.domain.entity.User;
import com.project.finances.mocks.entity.UserMock;
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
    void findByIdAndIsActive(){
        User userMock = UserMock.get();
        when(repository.findByIdAndIsActive(anyString())).thenReturn(Optional.of(userMock));

        String idUser = "id-valid";

        Optional<User> result = userQuery.findByIdIsActive(idUser);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(userMock);

        verify(repository, times(1)).findByIdAndIsActive(idUser);
    }

    @Test
    @DisplayName("Should return a optional user when id is provider")
    void findByIdToActive(){
        User userMock = UserMock.get();
        when(repository.findById(anyString())).thenReturn(Optional.of(userMock));

        String idUser = "id-valid";

        Optional<User> result = userQuery.findByIdToActiveAccount(idUser);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(userMock);

        verify(repository, times(1)).findById(idUser);
    }


    @Test
    @DisplayName("Should return a optional user when email is provider")
    void findByUsername(){
        String email = "example@email.com";
        User userMock = UserMock.get().withEmail(email);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(userMock));

        Optional<User> result = userQuery.findByUsername(email);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(userMock);

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should return a optional user when email is provider and account is active")
    void findByUsernameAndIsActive(){
        String email = "example@email.com";
        User userMock = UserMock.get().withEmail(email);
        when(repository.findByEmailAndIsActive(anyString())).thenReturn(Optional.of(userMock));

        Optional<User> result = userQuery.findByEmailActive(email);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get().getEmail()).isEqualTo(userMock.getEmail());

        verify(repository, times(1)).findByEmailAndIsActive(email);
    }

}