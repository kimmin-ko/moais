package com.moais.todo.service;

import com.moais.todo.domain.Member;
import com.moais.todo.domain.Todo;
import com.moais.todo.persistence.TodoRepository;
import com.moais.todo.service.dto.TodoWriteCommand;
import com.moais.todo.service.dto.TodoWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final MemberService memberService;

    @Transactional
    public TodoWriteResult write(TodoWriteCommand command) {
        Member writer = memberService.getById(command.getMemberId());

        Todo todo = new Todo(writer, command.getTitle(), command.getContent());
        todoRepository.save(todo);
        return new TodoWriteResult(todo);
    }

}
