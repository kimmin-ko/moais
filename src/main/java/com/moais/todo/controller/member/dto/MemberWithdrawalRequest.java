package com.moais.todo.controller.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberWithdrawalRequest {

    @NotBlank(message = "Password must not be null.")
    private String password;

}
