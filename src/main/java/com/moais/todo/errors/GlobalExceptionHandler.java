package com.moais.todo.errors;

import com.moais.todo.errors.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleInvalidFormatException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                         BindingResult bindingResult) {
        log.error(e.getMessage(), e);
        return new ValidationErrorResponse(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(ErrorCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
}