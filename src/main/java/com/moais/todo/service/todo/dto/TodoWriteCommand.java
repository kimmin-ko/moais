package com.moais.todo.service.todo.dto;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class TodoWriteCommand {

    private final Long memberId;
    private final String title;
    private final String content;

    public TodoWriteCommand(Long memberId, String title, String content) {
        Assert.notNull(memberId, "Member id must not be null.");
        Assert.hasText(title, "Title must not be null or empty.");

        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
