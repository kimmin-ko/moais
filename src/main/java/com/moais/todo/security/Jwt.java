package com.moais.todo.security;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
public final class Jwt {

    private final boolean isValid;
    private String subject;

    Jwt(String token, String secret) {
        this.isValid = isJwtValid(token, secret);
    }


    public boolean isValid() {
        return this.isValid;
    }

    public String getSubject() {
        return this.subject;
    }

    private boolean isJwtValid(String token, String secret) {
        if (ObjectUtils.isEmpty(token)) {
            log.error("Jwt is empty.");
            return false;
        }

        try {
            this.subject = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            log.debug("Request member id: {}", subject);

            if (ObjectUtils.isEmpty(subject)) {
                log.error("The subject contained in the jwt is empty.");
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error("Jwt validation failed.", e);
            return false;
        }
    }
}
