package com.project.finances.infra.filter;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import com.project.finances.infra.service.jwt.JwtTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserQuery userQuery;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {     String token = jwtTokenService.resolveToken((HttpServletRequest) request);

        if(token != null && jwtTokenService.tokenIsValid(token)){
            String userId = jwtTokenService.decodeToken(token);
            setContext(userId);
        }
        filterChain.doFilter(request, response);
    }

    private void setContext(String userId){
        Optional<User> optionalUser = userQuery.findById(userId);

        if(optionalUser.isPresent()){
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(optionalUser.get(), "", null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
