package com.moais.todo.service.dto;

import com.moais.todo.domain.Todo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TodoWriteResult {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final LocalDateTime createdAt;

    public TodoWriteResult(Todo todo) {
        this.id = todo.getId();
        this.memberId = todo.getMemberId();
        this.title = todo.getTitle();
        this.createdAt = todo.getCreatedAt();
    }
}
