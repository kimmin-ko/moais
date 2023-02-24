package com.moais.todo.controller.dto;

import com.moais.todo.service.dto.MemberJoinCommand;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberJoinRequest {

    @NotBlank(message = "회원의 계정 ID를 입력하세요.")
    private String accountId;

    @NotBlank(message = "회원의 비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "회원의 닉네임을 입력하세요.")
    private String nickname;

    public MemberJoinCommand toCommand() {
        return new MemberJoinCommand(
                this.accountId,
                this.password,
                this.nickname
        );
    }
}
