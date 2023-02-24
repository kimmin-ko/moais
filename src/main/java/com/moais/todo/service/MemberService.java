package com.moais.todo.service;

import com.moais.todo.domain.Member;
import com.moais.todo.persistence.MemberRepository;
import com.moais.todo.service.dto.MemberJoinCommand;
import com.moais.todo.service.dto.MemberJoinResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository repository;

    @Transactional
    public MemberJoinResult join(@NotNull MemberJoinCommand command) {
        // account id 중복 검증
        if (existsByAccountId(command.getAccountId())) {
            throw new IllegalArgumentException("Account id already exists.");
        }

        // nickname 중복 검증
        if (existsByNickname(command.getNickname())) {
            throw new IllegalArgumentException("Nickname already exists.");
        }

        // 회원 저장
        Member member = new Member(
                command.getAccountId(),
                passwordEncoder.encode(command.getPassword()),
                command.getNickname()
        );

        repository.save(member);
        return new MemberJoinResult(member);
    }

    @Transactional(readOnly = true)
    public boolean existsByAccountId(String accountId) {
        Assert.hasText(accountId, "Account id must not be null or empty.");
        return repository.existsByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public boolean existsByNickname(String nickname) {
        Assert.hasText(nickname, "Nickname must not be null or empty.");
        return repository.existsByNickname(nickname);
    }

}
