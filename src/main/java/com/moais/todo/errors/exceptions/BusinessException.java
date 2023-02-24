package com.moais.todo.errors.exceptions;

import com.moais.todo.errors.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    protected final ErrorCode errorType;
    protected final String message;

    protected BusinessException(ErrorCode errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }

    public int getErrorCode() {
        return this.errorType.getCode();
    }
}
