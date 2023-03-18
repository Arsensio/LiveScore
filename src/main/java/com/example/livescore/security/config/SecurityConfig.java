package com.example.livescore.security.config;

import com.example.livescore.security.jwt.JwtConfigurer;
import com.example.livescore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String LOGIN_ENDPOINT = "/auth/login";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/").permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}

//                .antMatchers(LOGIN_ENDPOINT).permitAll()
//                .antMatchers("/swagger-ui/").permitAll()
//                .antMatchers(GET, "/tournament/**").permitAll()
//                .antMatchers(POST).hasAuthority("ADMIN")
//                .antMatchers(PUT).hasAuthority("ADMIN")
//                .antMatchers(DELETE).hasAuthority("ADMIN")
