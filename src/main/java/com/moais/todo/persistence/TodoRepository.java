package com.moais.todo.persistence;

import com.moais.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select t from Todo t where t.id = :id and t.member.id = :memberId")
    Optional<Todo> findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);
}
