package com.moais.todo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moais.todo.errors.exceptions.ServerException;
import com.moais.todo.properties.JwtProperty;
import com.moais.todo.security.DefaultUserDetails;
import com.moais.todo.security.filter.dto.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProperty jwtProperty;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginRequest.getAccountId(),
                    loginRequest.getPassword(),
                    Collections.emptyList()
            );

            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        DefaultUserDetails user = (DefaultUserDetails) authResult.getPrincipal();

        String jwt = Jwts.builder()
                .setSubject(user.getMemberId())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
                .compact();

        response.setHeader("token", jwt);
    }
}
