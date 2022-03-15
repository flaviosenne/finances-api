package com.project.finances.infra.config;

import com.project.finances.domain.usecases.user.repository.UserQuery;
import com.project.finances.infra.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ENDPOINTS_PUBLIC_POST = {
            "/v1/auth/login",
            "/v1/users",
            "/v1/users/active",
            "/v1/users/retrieve-password"
    };

    private static final String[] ENDPOINTS_PUBLIC_PUT = {
            "/v1/users/redefine-password",
            "/v1/users/active-account/*"
    };

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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/health",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable();

        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,ENDPOINTS_PUBLIC_POST).permitAll()
                .antMatchers(HttpMethod.PUT, ENDPOINTS_PUBLIC_PUT).permitAll()
                .anyRequest().authenticated();

        http.apply(new JwtConfig(jwtTokenService, userQuery));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
