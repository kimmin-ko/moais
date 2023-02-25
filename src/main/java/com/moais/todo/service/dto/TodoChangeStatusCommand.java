package com.moais.todo.service.dto;

import com.moais.todo.domain.TodoStatus;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class TodoChangeStatusCommand {

    private final Long memberId;
    private final Long todoId;
    private final TodoStatus status;

    public TodoChangeStatusCommand(Long memberId, Long todoId, TodoStatus status) {
        Assert.notNull(memberId, "Member id must not be null.");
        Assert.notNull(todoId, "Todo id must not be null.");
        Assert.notNull(status, "Todo status must not be null.");

        this.memberId = memberId;
        this.todoId = todoId;
        this.status = status;
    }
}
