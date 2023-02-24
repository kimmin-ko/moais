package com.moais.todo.persistence;

import com.moais.todo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByAccountId(String accountId);

    boolean existsByNickname(String nickname);

}
