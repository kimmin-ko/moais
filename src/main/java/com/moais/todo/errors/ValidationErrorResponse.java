package com.moais.todo.errors;

import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ValidationErrorResponse {

    private final int code;
    private final LocalDateTime time;
    private List<FieldErrorMessage> fieldErrorMessages;

    public ValidationErrorResponse() {
        this.code = ErrorCode.BAD_REQUEST.getCode();
        this.time = LocalDateTime.now();
    }

    public ValidationErrorResponse(BindingResult bindingResult) {
        this();

        if (Objects.isNull(bindingResult)) {
            return;
        }

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return;
        }

        this.fieldErrorMessages = fieldErrors.stream()
                .map(FieldErrorMessage::new)
                .collect(Collectors.toList());
    }

    @Getter
    static class FieldErrorMessage {

        private String field;
        private String errorMessage;

        public FieldErrorMessage(FieldError fieldError) {
            if (Objects.isNull(fieldError)) {
                return;
            }
            this.field = fieldError.getField();
            this.errorMessage = fieldError.getDefaultMessage();
        }
    }


}
