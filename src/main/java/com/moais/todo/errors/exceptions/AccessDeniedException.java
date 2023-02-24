package com.moais.todo.errors.exceptions;

import com.moais.todo.errors.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(ErrorCode.ACCESS_DENIED, message);
    }
}
