package com.moais.todo.errors;

import lombok.Getter;

public enum ErrorCode {
    BAD_REQUEST(400),
    ACCESS_DENIED(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    @Getter
    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
