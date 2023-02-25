package com.moais.todo.service.dto;

import com.moais.todo.domain.TodoStatus;
import lombok.Getter;

@Getter
public class TodoChangeStatusResult {

    private final Long todoId;
    private final TodoStatus before;
    private final TodoStatus after;

    public TodoChangeStatusResult(Long todoId, TodoStatus before, TodoStatus after) {
        this.todoId = todoId;
        this.before = before;
        this.after = after;
    }
}
