package com.moais.todo.service.todo;

import com.moais.todo.domain.Member;
import com.moais.todo.domain.Todo;
import com.moais.todo.errors.exceptions.NotFoundException;
import com.moais.todo.persistence.TodoRepository;
import com.moais.todo.service.todo.dto.TodoChangeStatusCommand;
import com.moais.todo.service.todo.dto.TodoChangeStatusResult;
import com.moais.todo.service.todo.dto.TodoWriteCommand;
import com.moais.todo.service.todo.dto.TodoWriteResult;
import com.moais.todo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

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

    @Transactional
    public TodoChangeStatusResult changeStatus(TodoChangeStatusCommand command) {
        Todo todo = getByTodoIdAndMemberId(command.getTodoId(), command.getMemberId());

        TodoChangeStatusResult result = new TodoChangeStatusResult(todo.getId(), todo.getStatus(), command.getStatus());

        todo.changeStatus(command.getStatus());
        return result;
    }

    @Transactional(readOnly = true)
    public Optional<Todo> findByTodoIdAndMemberId(Long todoId, Long memberId) {
        Assert.notNull(memberId, "Member id must not be null.");
        Assert.notNull(todoId, "Todo id must not be null.");
        return todoRepository.findByIdAndMemberId(todoId, memberId);
    }

    @Transactional(readOnly = true)
    public Todo getByTodoIdAndMemberId(Long todoId, Long memberId) {
        return findByTodoIdAndMemberId(todoId, memberId)
                .orElseThrow(() -> new NotFoundException(String.format("Todo not found. id: %d, member id: %d", todoId, memberId)));
    }

    @Transactional(readOnly = true)
    public Optional<Todo> findLatestOneByMemberId(Long memberId) {
        Assert.notNull(memberId, "Member id must not be null.");
        Member member = memberService.getById(memberId);
        return todoRepository.findFirstByMemberOrderByCreatedAtDesc(member);
    }

    @Transactional(readOnly = true)
    public Page<Todo> findAllByMemberId(Long memberId, Pageable pageable) {
        Assert.notNull(memberId, "Member id must not be null.");
        Member member = memberService.getById(memberId);
        return todoRepository.findAllByMember(member, pageable);
    }
}
