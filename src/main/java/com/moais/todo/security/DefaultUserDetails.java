package com.moais.todo.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultUserDetails implements UserDetails {

    private final String memberId;
    private final String accountId;
    private final String password;
    private final List<? extends GrantedAuthority> authorities = new ArrayList<>();

    public DefaultUserDetails(Long memberId, String accountId, String password) {
        this.memberId = memberId.toString();
        this.accountId = accountId;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.accountId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
