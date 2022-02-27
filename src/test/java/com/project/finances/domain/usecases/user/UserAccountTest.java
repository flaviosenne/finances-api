package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.usecases.user.email.MailCreateAccountProtocol;
import com.project.finances.domain.usecases.user.repository.UserCodeCommand;
import com.project.finances.domain.usecases.user.repository.UserCommand;
import com.project.finances.domain.usecases.user.repository.UserQuery;
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
class UserAccountTest {

    @Mock
    private UserQuery userQuery;
    @Mock
    private UserCommand userCommand;
    @Mock
    private CryptographyProtocol cryptographyProtocol;
    @Mock
    private MailCreateAccountProtocol mailCreateAccountProtocol;
    @Mock
    private UserCodeCommand userCodeCommand;

    private UserAccountProtocol userAccountProtocol;

    @BeforeEach
    void setup(){
        userAccountProtocol = new UserAccount(userQuery, userCommand, cryptographyProtocol, mailCreateAccountProtocol, userCodeCommand);
    }

    @Test
    @DisplayName("Should throw bad request exception when email already exist in Data base")
    void notCreateAccount(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        User userToSaveMock = new User("example@email.com", "first-name", "last-name", "password", false);

        when(userQuery.findByUsername(anyString())).thenReturn(Optional.of(userMock));

        Throwable exception = BDDAssertions.catchThrowable(()-> userAccountProtocol.createAccount(userToSaveMock));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Email já cadastrado na base de dados");

        verify(userCommand, never()).save(any(User.class));
        verify(userCodeCommand, never()).save(any(User.class));
        verify(mailCreateAccountProtocol, never()).sendEmail(any(User.class), anyString());
    }

    @Test
    @DisplayName("Should create new user when email do not exist in DB and request is successful")
    void createAccount(){
        User userMock = new User("example@email.com", "first-name", "last-name", "hash", false);
        User userToSaveMock = new User("example@email.com", "first-name", "last-name", "password", false);

        userMock.withId(userToSaveMock.getId());
        when(userQuery.findByUsername(anyString())).thenReturn(Optional.empty());
        when(cryptographyProtocol.encodePassword(anyString())).thenReturn("hash");
        when(userCommand.save(userToSaveMock)).thenReturn(userMock);
        when(userCodeCommand.save(userMock)).thenReturn("code");


        User result = userAccountProtocol.createAccount(userToSaveMock);

        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getPassword()).isEqualTo("hash");
        BDDAssertions.assertThat(result.getId()).isNotNull().isEqualTo(userToSaveMock.getId());

        verify(cryptographyProtocol, times(1)).encodePassword(userToSaveMock.getPassword());
        verify(userCommand, times(1)).save(userToSaveMock);
        verify(userCodeCommand, times(1)).save(userMock);
        verify(mailCreateAccountProtocol, times(1)).sendEmail(userMock, "code");
    }


    @Test
    @DisplayName("Should throw bad request exception when code user do not exist in DB")
    void notActiveAccount(){
        when(userQuery.findById(anyString())).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(() ->userAccountProtocol.activeAccount("invalid-code"));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Código do usuário inválido");

        verify(userCommand, never()).save(any(User.class));
    }
}