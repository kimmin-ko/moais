package com.moais.todo.service.member.dto;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class MemberJoinCommand {

    private final String accountId;
    private final String password;
    private final String nickname;

    public MemberJoinCommand(String accountId, String password, String nickname) {
        Assert.hasText(accountId, "Account id must not be null or empty.");
        Assert.hasText(password, "Password must not be null or empty.");
        Assert.hasText(nickname, "Nickname must not be null or empty.");

        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
    }
}
