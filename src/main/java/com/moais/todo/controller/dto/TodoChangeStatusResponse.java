package com.moais.todo.controller.dto;

import com.moais.todo.domain.TodoStatus;
import com.moais.todo.service.dto.TodoChangeStatusResult;
import lombok.Getter;

@Getter
public class TodoChangeStatusResponse {

    private final Long todoId;
    private final TodoStatus before;
    private final TodoStatus after;

    public TodoChangeStatusResponse(TodoChangeStatusResult result) {
        this.todoId = result.getTodoId();
        this.before = result.getBefore();
        this.after = result.getAfter();
    }
}
