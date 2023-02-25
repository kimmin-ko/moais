package com.moais.todo.security;

import com.moais.todo.properties.JwtProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtProperty jwtProperty;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers("/members/withdrawal").authenticated()
                .antMatchers("/todos/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(getLoginFilter())
                .addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // h2-console 화면을 보기 위한 설정
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // private //
    private LoginFilter getLoginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(jwtProperty);
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }

    private JwtAuthorizationFilter getJwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProperty);
    }

}
