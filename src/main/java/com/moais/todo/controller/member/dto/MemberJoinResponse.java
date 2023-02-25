package com.moais.todo.controller.member.dto;

import com.moais.todo.service.member.dto.MemberJoinResult;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberJoinResponse {

    private final Long id;
    private final String accountId;
    private final String nickname;
    private final LocalDateTime createdAt;

    public MemberJoinResponse(MemberJoinResult result) {
        this.id = result.getId();
        this.accountId = result.getAccountId();
        this.nickname = result.getNickname();
        this.createdAt = result.getCreatedAt();
    }
}
