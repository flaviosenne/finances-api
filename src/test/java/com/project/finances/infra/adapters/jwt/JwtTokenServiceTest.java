package com.project.finances.infra.adapters.jwt;

import com.project.finances.app.usecases.user.dto.ResponseLoginDto;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"secretKey = secret", "expireIn : 10000"})
class JwtTokenServiceTest {

    @Mock
    private JwtTokenService jwtTokenService;

    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @BeforeEach
    void setup(){
        jwtTokenService = new JwtTokenService();

        ReflectionTestUtils.setField(jwtTokenService, "secretKey", "it's a security key");
        ReflectionTestUtils.setField(jwtTokenService, "expireIn", "10000");
    }


    @Test
    @DisplayName("Should return a token when payload is provider")
    void  generateToken(){
        String payload = "id";
        ResponseLoginDto result = jwtTokenService.generateToken(payload);

        BDDAssertions.assertThat(result).isNotNull().isNotEqualTo(payload);
    }

    @Test
    @DisplayName("Should return payload when token is provider")
    void  decodeToken(){
        String payload = "id";
        ResponseLoginDto token = jwtTokenService.generateToken(payload);

        String result = jwtTokenService.decodeToken(token.getToken());

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(payload);
    }

    @Test
    @DisplayName("Should return token of request")
    void  resolveToken(){
        request.addHeader("Authorization", "Bearer token");

        String result = jwtTokenService.resolveToken(request);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo("token");
    }

    @Test
    @DisplayName("Should return null when invalid token in request")
    void  resolveInvalidToken(){
        request.addHeader("Authorization", "invalid-token");

        String result = jwtTokenService.resolveToken(request);

        BDDAssertions.assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return true when token valid")
    void  validToken(){
        ResponseLoginDto token = jwtTokenService.generateToken("payload");

        boolean result = jwtTokenService.tokenIsValid(token.getToken());

        BDDAssertions.assertThat(result).isNotNull().isTrue();
    }

    @Test
    @DisplayName("Should return false when token invalid")
    void  invalidToken(){
        ResponseLoginDto token = jwtTokenService.generateToken("payload");

        ReflectionTestUtils.setField(jwtTokenService, "secretKey", "other key");
        boolean result = jwtTokenService.tokenIsValid(token.getToken());

        BDDAssertions.assertThat(result).isNotNull().isFalse();
    }



}