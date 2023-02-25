package com.moais.todo.service.member;

import com.moais.todo.domain.Member;
import com.moais.todo.errors.exceptions.NotFoundException;
import com.moais.todo.persistence.MemberRepository;
import com.moais.todo.service.member.dto.MemberJoinCommand;
import com.moais.todo.service.member.dto.MemberJoinResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository repository;

    @Transactional
    public MemberJoinResult join(@NotNull MemberJoinCommand command) {
        verifyDuplicateAccountId(command.getAccountId());
        verifyDuplicateNickname(command.getNickname());

        // 회원 저장
        Member member = new Member(
                command.getAccountId(),
                passwordEncoder.encode(command.getPassword()),
                command.getNickname()
        );

        repository.save(member);
        return new MemberJoinResult(member);
    }

    @Transactional
    public boolean withdrawal(Long memberId, String password) {
        Assert.hasText(password, "Password must not be null or empty.");

        Member member = getById(memberId);

        if (member.isWithdrawal()) {
            log.warn("Members who have already withdrawn");
            return false;
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        member.withdrawal();
        return true;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long memberId) {
        Assert.notNull(memberId, "Member id must not be null.");
        return repository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Member getById(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found. member id: " + memberId));
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

    // private //
    private void verifyDuplicateAccountId(String accountId) {
        if (existsByAccountId(accountId)) {
            throw new IllegalArgumentException("Account id already exists.");
        }
    }

    private void verifyDuplicateNickname(String nickname) {
        if (existsByNickname(nickname)) {
            throw new IllegalArgumentException("Nickname already exists.");
        }
    }
}
