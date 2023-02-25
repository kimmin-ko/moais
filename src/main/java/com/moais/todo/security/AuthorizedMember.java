package com.moais.todo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class AuthorizedMember {

    public Long getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        if (principal.equals("anonymous")) {
            throw new IllegalStateException("Anonymous users cannot get member id.");
        }

        return Long.parseLong(principal);
    }

}
