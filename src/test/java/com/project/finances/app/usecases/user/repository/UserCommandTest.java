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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserCommandTest {

    @Mock
    private UserRepository repository;

    private UserCommand userCommand;

    @BeforeEach
    void setup(){
        userCommand = new UserCommand(repository);
    }

    @Test
    @DisplayName("Should save user when object is provider")
    void save(){
        User userMock = UserMock.get();

        when(repository.save(userMock)).thenReturn(userMock);

        User result = userCommand.save(userMock);

        BDDAssertions.assertThat(userMock.getId()).isNotNull();
        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getId()).isNotNull().isEqualTo(userMock.getId());

        verify(repository, times(1)).save(userMock);
    }

    @Test
    @DisplayName("Should update user when object and id is provider")
    void update(){
        User userMock = UserMock.get();

        when(repository.save(userMock)).thenReturn(userMock);

        String userid = "other-id";
        String idPreview = userMock.getId();
        User result = userCommand.update(userMock, userid);

        BDDAssertions.assertThat(userMock.getId()).isNotNull();
        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getId()).isNotEqualTo(idPreview).isEqualTo(userid);

        verify(repository, times(1)).save(any(User.class));
    }
}