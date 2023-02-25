package com.moais.todo.controller.todo.dto;

import com.moais.todo.domain.Todo;
import com.moais.todo.domain.TodoStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final TodoStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.status = todo.getStatus();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }
}
