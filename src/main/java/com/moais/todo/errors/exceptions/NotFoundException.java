package com.moais.todo.errors.exceptions;

import com.moais.todo.errors.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}