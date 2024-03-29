package com.project.finances.app.usecases.user;

import com.project.finances.app.usecases.user.auth.Login;
import com.project.finances.app.usecases.user.ports.CryptographyProtocol;
import com.project.finances.domain.entity.User;
import com.project.finances.app.usecases.user.ports.AuthenticationProtocol;
import com.project.finances.app.usecases.user.ports.TokenProtocol;
import com.project.finances.app.usecases.user.auth.dto.LoginDto;
import com.project.finances.app.usecases.user.auth.dto.ResponseLoginDto;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.mocks.entity.UserMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LoginTest {

    @Mock
    private UserQuery userQuery;
    @Mock
    private CryptographyProtocol cryptographyProtocol;
    @Mock
    private TokenProtocol tokenProtocol;

    private AuthenticationProtocol authenticationProtocol;

    @BeforeEach
    void setup(){
        authenticationProtocol = new Login(userQuery, cryptographyProtocol, tokenProtocol);
    }

    @Test
    @DisplayName("Should throw bad request exception when email incorrect")
    void emailInvalid(){
        LoginDto dto = LoginDto.builder().email("incorrect@emal.com").build();
        when(userQuery.findByEmailActive(anyString())).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->authenticationProtocol.login(dto));

        BDDAssertions.assertThat(exception).isInstanceOf(BadCredentialsException.class).hasMessage("Credenciais inválidas");

        verify(userQuery,times(1)).findByEmailActive("incorrect@emal.com");
        verify(cryptographyProtocol, never()).passwordMatchers(anyString(), anyString());
        verify(tokenProtocol, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("Should throw bad request exception when password incorrect")
    void passwordInvalid(){
        LoginDto dto = LoginDto.builder().email("correct@emal.com").password("password-invalid").build();
        User userMock = UserMock.get();

        when(userQuery.findByEmailActive(anyString())).thenReturn(Optional.of(userMock));
        when(cryptographyProtocol.passwordMatchers(dto.getPassword(), userMock.getPassword())).thenReturn(false);

        Throwable exception = BDDAssertions.catchThrowable(()->authenticationProtocol.login(dto));

        BDDAssertions.assertThat(exception).isInstanceOf(BadCredentialsException.class).hasMessage("Credenciais inválidas");

        verify(userQuery,times(1)).findByEmailActive("correct@emal.com");
        verify(cryptographyProtocol, times(1)).passwordMatchers(dto.getPassword(), "hash");
        verify(tokenProtocol, never()).generateToken(anyString());
    }


    @Test
    @DisplayName("Should return token when user login successful")
    void generateToken(){
        LoginDto dto = LoginDto.builder().email("correct@emal.com").password("password-valid").build();
        User userMock = UserMock.get();
        userMock.withId("id");

        when(userQuery.findByEmailActive(anyString())).thenReturn(Optional.of(userMock));
        when(cryptographyProtocol.passwordMatchers(dto.getPassword(), userMock.getPassword())).thenReturn(true);
        when(tokenProtocol.generateToken(userMock.getId())).thenReturn(ResponseLoginDto.builder().token("token").build());

        ResponseLoginDto result = authenticationProtocol.login(dto);

        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getToken()).isEqualTo("token");

        verify(userQuery,times(1)).findByEmailActive("correct@emal.com");
        verify(cryptographyProtocol, times(1)).passwordMatchers(dto.getPassword(), "hash");
        verify(tokenProtocol, times(1)).generateToken("id");
    }

}