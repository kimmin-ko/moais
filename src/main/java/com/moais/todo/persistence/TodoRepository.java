package com.moais.todo.persistence;

import com.moais.todo.domain.Member;
import com.moais.todo.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select t from Todo t where t.id = :id and t.member.id = :memberId")
    Optional<Todo> findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    Optional<Todo> findFirstByMemberOrderByCreatedAtDesc(Member member);

    Page<Todo> findAllByMember(Member member, Pageable pageable);
}
