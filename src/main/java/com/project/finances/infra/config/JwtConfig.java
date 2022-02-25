package com.project.finances.infra.config;

import com.project.finances.domain.usecases.user.repository.UserQuery;
import com.project.finances.infra.filter.JwtTokenFilter;
import com.project.finances.infra.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenService jwtTokenService;
    private final UserQuery userQuery;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenService, userQuery);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
