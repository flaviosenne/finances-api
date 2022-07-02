package com.project.finances.app.usecases.user.repository;

import com.project.finances.app.usecases.user.repository.code.UserCodeCommand;
import com.project.finances.app.usecases.user.repository.code.UserCodeRepository;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserCode;
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
class UserCodeCommandTest {

    @Mock
    private UserCodeRepository repository;

    private UserCodeCommand userCodeCommand;

    @BeforeEach
    void setup(){
        userCodeCommand = new UserCodeCommand(repository);
    }

    @Test
    @DisplayName("Should disable preview code user when user already contains saved in DB")
    void disableAndSaveCode(){
        User userMock = UserMock.get();
        UserCode userCodeMock = new UserCode(userMock, true, "");

        String userId = userMock.getId();

        when(repository.findByUserId(userId)).thenReturn(Optional.of(userCodeMock));
        when(repository.save(any(UserCode.class))).thenReturn(userCodeMock);

        String result = userCodeCommand.save(userMock);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(userCodeMock.getCode());
        BDDAssertions.assertThat(userCodeMock.isValid()).isFalse();

        verify(repository, times(1)).findByUserId(userId);
        verify(repository, times(2)).save(any(UserCode.class));
    }

    @Test
    @DisplayName("Should save code user when user don't has code saved in DB")
    void saveCode(){
        User userMock = UserMock.get();
        UserCode userCodeMock = new UserCode(userMock, true, "");

        String userId = userMock.getId();

        when(repository.findByUserId(userId)).thenReturn(Optional.empty());
        when(repository.save(any(UserCode.class))).thenReturn(userCodeMock);

        String result = userCodeCommand.save(userMock);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(userCodeMock.getCode());
        BDDAssertions.assertThat(userCodeMock.isValid()).isTrue();

        verify(repository, times(1)).findByUserId(userId);
        verify(repository, times(1)).save(any(UserCode.class));
    }

}