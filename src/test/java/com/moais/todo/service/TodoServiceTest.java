package com.moais.todo.service;

import com.moais.todo.domain.Member;
import com.moais.todo.domain.Todo;
import com.moais.todo.domain.TodoStatus;
import com.moais.todo.persistence.MemberRepository;
import com.moais.todo.persistence.TodoRepository;
import com.moais.todo.service.dto.TodoWriteCommand;
import com.moais.todo.service.dto.TodoWriteResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    MemberRepository memberRepository;

    String accountId = "account1234";
    String password = "password1234";
    String nickname = "nickname1234";
    String title = "title1234";
    String content = "content1234";

    @AfterEach
    void cleanup() {
        System.out.println("----- CLEANUP START -----");
        todoRepository.deleteAll();
        memberRepository.deleteAll();
        System.out.println("----- CLEANUP END -----");
    }

    @Test
    @DisplayName("TODO를 작성이 정상 동작한다.")
    void write_todo_test() {
        // given
        Member writer = new Member(accountId, password, nickname);
        memberRepository.save(writer);

        TodoWriteCommand command = new TodoWriteCommand(writer.getId(), title, content);

        // when
        TodoWriteResult result = todoService.write(command);
        System.out.println("result = " + result);

        // then
        Optional<Todo> optionalTarget = todoRepository.findById(result.getId());
        assertThat(optionalTarget).isPresent();

        Todo target = optionalTarget.get();
        assertThat(target.getTitle()).isEqualTo(title);
        assertThat(target.getContent()).isEqualTo(content);
        assertThat(target.getMemberId()).isEqualTo(writer.getId());
        assertThat(target.getStatus()).isEqualTo(TodoStatus.TO_DO);
        assertThat(target.getCreatedAt()).isNotNull();
    }

}