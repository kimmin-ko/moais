package com.moais.todo.persistence;

import com.moais.todo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAccountId(String accountId);

    boolean existsByAccountId(String accountId);

    boolean existsByNickname(String nickname);
}
