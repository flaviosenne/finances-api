package com.project.finances.infra.security;

import com.fasterxml.jackson.core.filter.TokenFilter;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import com.project.finances.infra.config.JwtConfig;
import com.project.finances.infra.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenService jwtTokenService;
    private final UserQuery userQuery;


    @Bean
    public BCryptPasswordEncoder  bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,
                        "/v1/auth/login",
                        "/v1/user",
                        "v1/user/active")
                .permitAll()

                .antMatchers(HttpMethod.GET,
                        "/v1/user/retrieve-password")
                .permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenService, userQuery))
        ;
    }
}
