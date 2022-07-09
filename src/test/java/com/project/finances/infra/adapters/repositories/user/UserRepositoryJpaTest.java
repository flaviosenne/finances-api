package com.project.finances.infra.adapters.repositories.user;

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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserRepositoryJpaTest {

    @Mock
    private UserInterfaceJpa jpa;

    private UserRepositoryJpa repository;

    @BeforeEach
    void setup(){
        repository = new UserRepositoryJpa(jpa);
    }

    @Test
    @DisplayName("should return a user when id is matcher with someone user in DB")
    void findByEmail(){
        User user = UserMock.get();
        String email = user.getEmail();

        when(jpa.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = repository.findByEmail(email);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(user));

        verify(jpa, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("should return a user when email is matcher with someone user and is active in DB")
    void findByEmailAndIsActive(){
        User user = UserMock.get();
        String email = user.getEmail();

        when(jpa.findByEmailAndIsActive(email)).thenReturn(Optional.of(user));

        Optional<User> result = repository.findByEmailAndIsActive(email);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(user));

        verify(jpa, times(1)).findByEmailAndIsActive(email);
    }

    @Test
    @DisplayName("should return a user when id is matcher with someone user and is active in DB")
    void findByIdAndIsActive(){
        User user = UserMock.get();
        String id = user.getId();

        when(jpa.findByIdAndIsActive(id)).thenReturn(Optional.of(user));

        Optional<User> result = repository.findByIdAndIsActive(id);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(user));

        verify(jpa, times(1)).findByIdAndIsActive(id);
    }

    @Test
    @DisplayName("should return a user when id is matcher with someone user in DB")
    void findById(){
        User user = UserMock.get();
        String id = user.getId();

        when(jpa.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = repository.findById(id);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(user));

        verify(jpa, times(1)).findById(id);
    }

    @Test
    @DisplayName("should save in DB a user when this is provider")
    void save(){
        User user = UserMock.get();

        when(jpa.save(user)).thenReturn(user);

        User result = repository.save(user);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(user);

        verify(jpa, times(1)).save(user);
    }


}