package com.moais.todo.security;

import com.moais.todo.domain.Member;
import com.moais.todo.errors.exceptions.NotFoundException;
import com.moais.todo.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (ObjectUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Username must not be null or empty.");
        }

        Member member = memberRepository.findByAccountId(username)
                .orElseThrow(() -> new NotFoundException("Member not found. account id: " + username));

        return new DefaultUserDetails(member.getId(), member.getAccountId(), member.getPassword());
    }

}
