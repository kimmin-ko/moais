package com.moais.todo.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TodoWriteRequest {

    @NotBlank(message = "Todo title must not be null.")
    private String title;

    private String content;
}
