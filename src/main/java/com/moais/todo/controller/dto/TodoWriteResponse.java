package com.moais.todo.controller.dto;

import com.moais.todo.service.dto.TodoWriteResult;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoWriteResponse {

    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;

    public TodoWriteResponse(TodoWriteResult result) {
        this.id = result.getId();
        this.title = result.getTitle();
        this.createdAt = result.getCreatedAt();
    }
}
