package com.project.finances.infra.service.cryptography;

import com.project.finances.domain.exception.ServerErrorException;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class BcryptServiceTest {

    private final BcryptService bcryptService= new BcryptService();

    @Test
    @DisplayName("Should encode password when is provider")
    void encodePassword(){
        String result = bcryptService.encodePassword("password");

        BDDAssertions.assertThat(result).isNotNull().isNotEqualTo("password");

    }


    @Test
    @DisplayName("Should throw server error exception when is make someone error in time encode password")
    void encodePasswordNul(){
        Throwable exception = BDDAssertions.catchThrowable(()->bcryptService.encodePassword(null));

        BDDAssertions.assertThat(exception)
                .isInstanceOf(ServerErrorException.class)
                .hasMessage("Erro em criptografar a senha");
    }

    @Test
    @DisplayName("Should return true when password is matcher")
    void passwordIsMatcher(){
        String pass = "pass";
        String hash = bcryptService.encodePassword(pass);

        boolean result = bcryptService.passwordMatchers(pass, hash);

        BDDAssertions.assertThat(result).isTrue();
    }


    @Test
    @DisplayName("Should return false when password is not matcher")
    void passwordIsNotMatcher(){
        String pass = "other-pass";
        String hash = bcryptService.encodePassword("pass");

        boolean result = bcryptService.passwordMatchers(pass, hash);

        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should throw server error exception when is make someone error in time matcher password with pass null")
    void passwordIsNullMatcher(){
        String pass = "pass";
        String hash = bcryptService.encodePassword(pass);

        Throwable exception = BDDAssertions.catchThrowable(()->bcryptService.passwordMatchers(null, hash));

        BDDAssertions.assertThat(exception)
                .isInstanceOf(ServerErrorException.class)
                .hasMessage("Erro em validar senha");
    }

    @Test
    @DisplayName("Should return false when hash is null")
    void hashIsNullMatcher(){
        boolean result = bcryptService.passwordMatchers("pass", null);

        BDDAssertions.assertThat(result).isFalse();
    }
}