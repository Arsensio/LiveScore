package com.example.livescore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] pathArr = {"/game/**", "/group/**", "/player/**", "/player_statistics/**", "/protocol/**",
            "/team/**", "/team_statistics/**", "/tournament/**", "/event/**", "/goal_info/**", "/group_info/**"};
    private final String LOGIN_ENDPOINT = "/auth/login";
    private final String[] SWAGGER_ENDPOINTS = {"/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/v2/**",
            "/swagger-resources/**"};
    private final String INFO_UPLOAD_ENDPOINT = "/info/upload/**";
    private final String NOTIFICATION_ENDPOINTS = "/notification/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .antMatchers(GET, pathArr).permitAll()
                .antMatchers(NOTIFICATION_ENDPOINTS).permitAll()
                .antMatchers(POST, pathArr).hasAuthority("ADMIN")
                .antMatchers(PUT, pathArr).hasAuthority("ADMIN")
                .antMatchers(DELETE, pathArr).hasAuthority("ADMIN")
                .antMatchers(INFO_UPLOAD_ENDPOINT).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
