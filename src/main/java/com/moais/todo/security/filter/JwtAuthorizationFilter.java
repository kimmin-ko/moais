package com.moais.todo.security.filter;

import com.moais.todo.properties.JwtProperty;
import com.moais.todo.security.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProperty jwtProperty;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (ObjectUtils.isEmpty(authorization)) {
            log.debug("Requested by a non-member.");
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 검증
        String strJwt = extractJwt(authorization);
        String secret = jwtProperty.getSecret();

        Jwt jwt = new Jwt(strJwt, secret);
        if (!jwt.isValid()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        // security 인가
        String memberId = jwt.getSubject();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberId, null, new ArrayList<>()
        );
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    // private //
    private String extractJwt(String authorization) {
        String bearer = "Bearer ";
        if (authorization.startsWith(bearer)) {
            return authorization.substring(bearer.length());
        }

        return authorization;
    }
}
