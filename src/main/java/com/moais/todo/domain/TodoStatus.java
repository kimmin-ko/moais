package com.moais.todo.domain;

import lombok.Getter;

public enum TodoStatus {
    TO_DO("할 일"),
    IN_PROGRESS("진행중"),
    DONE("완료");

    @Getter
    private final String desc;

    TodoStatus(String desc) {
        this.desc = desc;
    }
}
