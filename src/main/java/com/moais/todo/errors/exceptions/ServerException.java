package com.moais.todo.errors.exceptions;

import com.moais.todo.errors.ErrorCode;

public class ServerException extends BusinessException {

    public ServerException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
