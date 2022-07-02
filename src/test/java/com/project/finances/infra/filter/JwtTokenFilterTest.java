package com.project.finances.infra.filter;

import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.User;
import com.project.finances.infra.adapters.jwt.JwtTokenService;
import com.project.finances.mocks.entity.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JwtTokenFilterTest {

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserQuery userQuery;


    private JwtTokenFilter jwtTokenFilter;

    @BeforeEach
    void setup(){
        jwtTokenFilter = new JwtTokenFilter(jwtTokenService, userQuery);
    }

    @Test
    @DisplayName("send valid token")
    void validToken() throws ServletException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        User userMock = UserMock.get();
        String userId = userMock.getId();

        when(jwtTokenService.resolveToken(request)).thenReturn("valid-token");
        when(jwtTokenService.tokenIsValid("valid-token")).thenReturn(true);
        when(jwtTokenService.decodeToken("valid-token")).thenReturn(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(userMock));

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("send invalid token")
    void invalidToken() throws ServletException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        when(jwtTokenService.resolveToken(request)).thenReturn("invalid-token");
        when(jwtTokenService.tokenIsValid("invalid-token")).thenReturn(false);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userQuery, never()).findByIdIsActive(anyString());
    }

    @Test
    @DisplayName("send null token")
    void nullToken() throws ServletException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        when(jwtTokenService.tokenIsValid(anyString())).thenReturn(false);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userQuery, never()).findByIdIsActive(anyString());
    }
}